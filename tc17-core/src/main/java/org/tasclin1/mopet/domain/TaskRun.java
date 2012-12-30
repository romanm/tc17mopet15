package org.tasclin1.mopet.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.MutableDateTime;
import org.springframework.ui.Model;
import org.tasclin1.mopet.domain.Times;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;

public class TaskRun implements Serializable, Comparable<TaskRun> {
    private static final long serialVersionUID = 1L;
    protected final Log log = LogFactory.getLog(getClass());

    private Integer defDay, calcDay;

    public Integer getDefDay() {
	return defDay;
    }

    private MutableDateTime begin, end;

    public MutableDateTime getEnd() {
	return end;
    }

    private Tree timesT;

    public Tree getTimesT() {
	return timesT;
    }

    public TaskRun(Tree timesT, Integer dayNr, MutableDateTime mutableDateTime, Model model) {
	init(timesT, dayNr, mutableDateTime, model);
    }

    public TaskRun(Tree timesT, Integer dayNr, Model model) {
	log.debug(1);
	MutableDateTime mutableDateTime = instanceMutableDateTime(dayNr, 10);
	init(timesT, dayNr, mutableDateTime, model);
    }

    private void init(Tree timesT, Integer dayNr, MutableDateTime mutableDateTime, Model model) {
	this.timesT = timesT;
	defDay = dayNr;
	// correctur begin
	Map<Long, TaskRun> regimeTaskRuns 
	= (Map<Long, TaskRun>) model.asMap().get(MopetService.regimeTaskRuns);
	while (regimeTaskRuns.containsKey(mutableDateTime.getMillis())) {
	    mutableDateTime.addMillis(-1);
	}
	begin = mutableDateTime;
	regimeTaskRuns.put(begin.getMillis(), this);
	// correctur begin END
	Integer durationValueSecond = timesT.getAppDurationSecond();
	end = endFromBegin().secondOfDay().add(durationValueSecond);

	modelThisDay(model);
    }

    public static MutableDateTime instanceMutableDateTime(Integer dayNr, int hour) {
	MutableDateTime mutableDateTime = new MutableDateTime();
	mutableDateTime.addDays(dayNr - 1);
	mutableDateTime.setHourOfDay(hour);
	mutableDateTime.setMinuteOfHour(0);
	// mutableDateTime.setSecondOfMinute(0);
	mutableDateTime.setSecondOfMinute(5);
	mutableDateTime.setMillisOfSecond(100);
	return mutableDateTime;
    }

    private void modelThisDay(Model model) {
	timesT.getTaskRuns().add(this);
	Map<Integer, Set<TaskRun>> dayNrTaskRuns = (Map<Integer, Set<TaskRun>>) model.asMap().get(
		MopetService.dayNrTaskRuns);
	log.debug(defDay);
	if (!dayNrTaskRuns.containsKey(defDay))
	    dayNrTaskRuns.put(defDay, new ConcurrentSkipListSet<TaskRun>());
	log.debug(dayNrTaskRuns);
	dayNrTaskRuns.get(defDay).add(this);
	Map<Integer, Map<Integer, Set<TaskRun>>> daysHoursTaskRuns = (Map<Integer, Map<Integer, Set<TaskRun>>>) model
		.asMap().get(MopetService.daysHoursTaskRuns);
	if (!daysHoursTaskRuns.containsKey(defDay))
	    daysHoursTaskRuns.put(defDay, new TreeMap<Integer, Set<TaskRun>>());
	Integer hourOfDay = begin.getHourOfDay();
	Map<Integer, Set<TaskRun>> dayHoursTaskRuns = daysHoursTaskRuns.get(defDay);
	if (!dayHoursTaskRuns.containsKey(hourOfDay))
	    dayHoursTaskRuns.put(hourOfDay, new ConcurrentSkipListSet<TaskRun>());
	dayHoursTaskRuns.get(hourOfDay).add(this);

    }

    public TaskRun(Tree timesT, TaskRun refTaskRun, Model model) {
	this.timesT = timesT;
	defDay = refTaskRun.getDefDay();

	Integer durationValueSecond = timesT.getAppDurationSecond();
	Integer relValueSecond = timesT.getTimesO().getConnectionIntervalSecond();

	// apporder
	// ↘↖ - leading
	// ↑↓ - driven
	// 0 -↘↑- beginAfterEnd
	// 1 -↑↖- beginBeforeBegin
	// 2 -↘↓- endAfterEnd
	// 3 -↓↖- endBeforeBegin
	String apporder = timesT.getTimesO().getApporder();
	if ("0".equals(apporder)) {
	    begin = refTaskRun.getEnd().copy().secondOfDay().add(relValueSecond);
	    end = endFromBegin().secondOfDay().add(durationValueSecond);
	} else if ("1".equals(apporder)) {
	    begin = refTaskRun.getBegin().copy().secondOfDay().add(0 - relValueSecond);
	    end = endFromBegin().secondOfDay().add(durationValueSecond);
	} else if ("2".equals(apporder)) {
	    end = refTaskRun.getEnd().copy().secondOfDay().add(relValueSecond);
	    begin = beginFromEnd().secondOfDay().add(0 - durationValueSecond);
	} else if ("3".equals(apporder)) {
	    end = refTaskRun.getBegin().copy().secondOfDay().add(0 - relValueSecond);
	    begin = beginFromEnd().secondOfDay().add(0 - durationValueSecond);
	}

	modelThisDay(model);

    }

    public String symbolToHour(int h) {
	if (0 == timesT.getAppDurationSecond() && begin.getHourOfDay() == h)
	    return addMinuteDottDuration(begin.getMinuteOfHour(), "&#160;") + "*";
	String symbol = ".";

	if (begin.getHourOfDay() == h) {
	    symbol = addMinuteDottDuration(begin.getMinuteOfHour(), "&#160;") + "|";
	    int minuteOfHour = 0;
	    if (end.getHourOfDay() > begin.getHourOfDay()) {
		minuteOfHour = 60 - begin.getMinuteOfDay();
	    } else if (timesT.getAppDurationSecond() > 0) {
		minuteOfHour = timesT.getAppDurationSecond() / 60;
	    }
	    symbol += addMinuteDottDuration(minuteOfHour, "·");
	}
	if (end.getHourOfDay() == h) {
	    if (begin.getHourOfDay() == h) {
		symbol += "|";
	    } else {
		symbol = addMinuteDottDuration(end.getMinuteOfHour(), "·") + "|";
	    }
	}
	if (begin.getHourOfDay() < h && end.getHourOfDay() > h) {
	    symbol = "-";
	}
	return symbol;
    }

    private String addMinuteDottDuration(int minuteOfHour, String c) {
	String symbol = "";
	if (0 == minuteOfHour) {
	} else if (15 >= minuteOfHour)
	    symbol += c;
	else if (30 >= minuteOfHour)
	    symbol += c + c;
	else if (45 >= minuteOfHour)
	    symbol += c + c + c;
	return symbol;
    }

    // Integer durationValueSecond;

    private MutableDateTime endFromBegin() {
	return begin.copy().millisOfSecond().add(10);
    }

    private MutableDateTime beginFromEnd() {
	return end.copy().millisOfSecond().add(0 - 10);
    }

    public MutableDateTime getBegin() {
	return begin;
    }

    public int compareTo(TaskRun o) {
	return (int) (getBegin().getMillis() - o.getBegin().getMillis());
    }

}
