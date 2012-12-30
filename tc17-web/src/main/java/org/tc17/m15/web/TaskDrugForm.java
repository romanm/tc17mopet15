package org.tc17.m15.web;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tasclin1.mopet.domain.Day;
import org.tasclin1.mopet.domain.Tree;

public class TaskDrugForm implements Serializable {
    private static final long serialVersionUID = 1L;
    protected final Log log = LogFactory.getLog(getClass());
    private Tree drugT;
    private Integer idt;
    private Tree targetT;

    public void initTaskDrugDose() {
	log.debug("---------------");
	setIdt(drugT.getDrugDoseT().getId());
	log.debug("---------------");
    }

    public void initTaskDrug() {
	setIdt(drugT.getId());
    }

    public Tree getTargetT() {
	return targetT;
    }

    public Integer getIdt() {
	return idt;
    }

    public void setIdt(Integer idt) {
	this.idt = idt;
	log.debug(idt + " " + targetT);
	setTargetT();
	log.debug(idt + " " + targetT);
    }

    /**
     * Find target tree equals idt value.
     */
    private void setTargetT() {
	if (idt.equals(drugT.getId()))
	    targetT = drugT;
	else
	    for (Tree t1 : drugT.getChildTs())
		if (idt.equals(t1.getId())) {
		    targetT = t1;
		    break;
		} else
		    for (Tree t2 : t1.getChildTs())
			if (idt.equals(t2.getId())) {
			    targetT = t2;
			    break;
			} else
			    for (Tree t3 : t2.getChildTs())
				if (idt.equals(t3.getId())) {
				    targetT = t3;
				    break;
				}
    }

    public TaskDrugForm(Tree drugT, Integer idt) {
	this.drugT = drugT;
	setIdt(idt);
    }

    public String toString() {
	String string = "day::type=" + type + ":fromday=" + getFromday() + ":totheday=" + getTotheday() + ":absset="
		+ getAbsset() + ":-:idt=" + idt;
	// log.debug(string);
	return string;
    };

    public Set<Integer> getAbsset() {
	return targetT.getDayO().getAbsSet();
    }

    public void setAbsset(Set<Integer> absset) {
	targetT.getDayO().setAbsSet(absset);
    }

    public Set<Integer> getAbsHourSet() {
	return targetT.getTimesO().getAbsHourSet();
    }

    public void setAbsHourSet(Set<Integer> absHourSet) {
	targetT.getTimesO().setAbsHourSet(absHourSet);
    }

    String type;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
	log.debug(type + " " + targetT);
    }

    public Integer getFromday() {
	return targetT.getDayO().getFromday();
    }

    public void setFromday(Integer fromday) {
	targetT.getDayO().setFromday(fromday);
    }

    public Integer getTotheday() {
	return targetT.getDayO().getTotheday();
    }

    public void setTotheday(Integer totheday) {
	targetT.getDayO().setTotheday(totheday);
    }

    public String actionStateDay() {
	initTargetDayT();
	return targetT.getDayO().actionStateDay();
    }

    public String actionStateTimes() {
	initTargetTimesT();
	return targetT.getTimesO().actionStateTimes(targetT);
    }

    // String edDay, edTimes;

    public void initHourAbs() {
	targetT.getTimesO().initHourAbs();
    }

    public void initAbs() {
	targetT.getDayO().initAbs();
    }

    public void initPeriod() {
	targetT.getDayO().initPeriod();
    }

    private void initTargetTimesT() {
	if (!targetT.isTimes()) {
	    if (targetT.isDay())
		targetT = targetT.getDrugDayTimesT(0);
	    else {
		Tree dayT = drugT.getDrugDayT(0);
		targetT = dayT.getDrugDayTimesT(0);
	    }
	    idt = targetT.getId();
	}

    }

    private void initTargetDayT() {
	if (!targetT.isDay()) {
	    targetT = drugT.getDrugDayT(0);
	}
	if (null == targetT.getDayO())
	    targetT.setMtlO(new Day());
	idt = targetT.getId();
    }

}
