package org.tasclin1.mopet.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The persistent class for the day database table.
 * 
 */
@Entity
public class Day implements MObject, Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    protected final Log log = LogFactory.getLog(getClass());

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    // @GeneratedValue(strategy=GenerationType.TABLE)
    @Id
    @Column(name = "idday")
    private Integer id;

    private String abs;
    private String newtype;

    private Integer apporder;
    private Integer numd;
    private Integer type;

    // bi-directional one-to-one association to TreeL
    @OneToOne
    @JoinColumn(name = "idday")
    private Tree tree;

    public Day() {
    }

    public Day(String abs, String newType) {
	super();
	setAbs(abs);
	setNewtype(newType);
    }

    public String getAbs() {
	if (abs == null)
	    abs = "";
	return this.abs;
    }

    public void setAbs(String abs) {
	this.abs = abs;
    }

    public Integer getApporder() {
	return this.apporder;
    }

    public void setApporder(Integer apporder) {
	this.apporder = apporder;
    }

    public String getNewtype() {
	if (newtype == null)
	    newtype = "";
	return this.newtype;
    }

    public void setNewtype(String newtype) {
	this.newtype = newtype;
    }

    public Integer getNumd() {
	return this.numd;
    }

    public void setNumd(Integer numd) {
	this.numd = numd;
    }

    public Integer getType() {
	return this.type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public Tree getTree() {
	return this.tree;
    }

    public void setTree(Tree tree) {
	this.tree = tree;
    }

    public String toString() {
	return "day:" + getId() + ":abs:" + abs + ":newtype:" + newtype;
    }

    public int compareTo(MObject d) {
	return compareTo((Day) d);
    }

    public int compareTo(Day d) {
	if (d == null)
	    return -1;
	else
	    return copmareAbs(d);
    }

    private int copmareAbs(Day d) {
	if (!hasAbs())
	    return d.hasAbs() ? 1 : compareNewtype(d);
	else if (!d.hasAbs())
	    return -1;
	else {
	    int c = abs.compareTo(d.getAbs());
	    return c != 0 ? c : compareNewtype(d);
	}
    }

    private int compareNewtype(Day d) {
	if (!hasNewtype())
	    return d.hasNewtype() ? 1 : 0;
	else if (!d.hasNewtype())
	    return -1;
	else
	    return newtype.compareTo(d.getNewtype());
    }

    public boolean hasAbs() {
	return abs != null && abs.length() > 0;
    }

    public boolean hasNewtype() {
	return newtype != null && newtype.length() > 0;
    }

    public Day addAtt(Map<String, Object> map) {
	setAbs((String) map.get("abs"));
	setNewtype((String) map.get("newtype"));
	return this;
    }

    @Transient
    String edDay;

    @Transient
    Set<Integer> hashSet, absSet, periodSet;

    public Set<Integer> getPeriodSet() {
	if (null == periodSet) {
	    periodSet = new HashSet<Integer>();
	    if ("p".equals(getNewtype())) {
		String[] splitAbsperiod = getAbs().split("-");
		String beginDay = splitAbsperiod[0];
		int beginDay2 = Integer.parseInt(beginDay);
		String endDay = splitAbsperiod[1];
		int enddDay2 = Integer.parseInt(endDay);
		for (int i = beginDay2; i < enddDay2; i++)
		    hashSet.add(i);
	    }
	}
	return periodSet;
    }

    public Set<Integer> getHashSet() {
	// log.debug(hashSet);
	if (null == hashSet) {
	    hashSet = new HashSet<Integer>();
	    // log.debug(getNewtype());
	    // log.debug(getNewtype().length());
	    if ("a".equals(getNewtype())) {
		hashSet.addAll(getAbsSet());
	    } else if ("p".equals(getNewtype())) {
		hashSet.addAll(getPeriodSet());
	    } else if ("l".equals(getNewtype())) {
		// log.debug(abs);
		if (abs.length() == 0) {
		} else if (abs.contains("-"))
		    hashSet.addAll(getPeriodSet());
		else
		    hashSet.add(Integer.parseInt(abs));
	    }
	}
	return hashSet;
    }

    public Set<Integer> getAbsSet() {
	if (null == absSet) {
	    absSet = new HashSet<Integer>();
	    if ("a".equals(getNewtype()))
		for (String dayNr : getAbs().split(","))
		    if (dayNr.length() > 0)
			absSet.add(Integer.parseInt(dayNr));
	}
	return absSet;
    }

    public void setAbsSet(Set<Integer> absSet) {
	this.absSet = absSet;
    }

    public void initAbs() {
	edDay = "edDayAbs";
	log.debug("---------------" + absSet);
    }

    public String actionStateDay() {
	if (null == edDay) {
	    edDay = "edDayAbs";
	    String newtype = getNewtype();
	    if ("a".equals(newtype))
		edDay = "edDayAbs";
	    else if ("p".equals(newtype))
		edDay = "edDayPeriod";
	}
	return edDay;
    }

    public void initPeriod() {
	edDay = "edDayPeriod";
	if (null == getFromday()) {
	    if ("p".equals(getNewtype())) {
		String abs = getAbs();
		String[] split = abs.split("-");
		int parseInt = Integer.parseInt(split[0]);
		int parseInt2 = Integer.parseInt(split[1]);
		this.fromday = parseInt;
		this.totheday = parseInt2;
	    }
	}

    }

    @Transient
    private Integer fromday, totheday;

    public Integer getTotheday() {
	return totheday;
    }

    public void setTotheday(Integer totheday) {
	this.totheday = totheday;
    }

    public Integer getFromday() {
	return fromday;
    }

    public void setFromday(Integer fromday) {
	this.fromday = fromday;
    }

}