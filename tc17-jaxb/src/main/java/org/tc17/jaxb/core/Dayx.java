package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Day;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "day")
public class Dayx extends Treex {

    public Dayx() {
    }

    public Dayx(Tree dayT) {
	super(dayT);
	Day dayO = dayT.getDayO();
	if (null != dayO) {
	    abs = dayO.getAbs();
	    newtype = dayO.getNewtype();
	}
    }

    ArrayList<Timesx> times;

    public ArrayList<Timesx> getTimes() {
	if (null == times)
	    times = new ArrayList<Timesx>();
	return times;
    }

    public void setTimes(ArrayList<Timesx> times) {
	this.times = times;
    }

    private String abs, newtype;

    public void setNewtype(String newtype) {
	this.newtype = newtype;
    }

    public void setAbs(String abs) {
	this.abs = abs;
    }

    @XmlAttribute
    public String getAbs() {
	return abs;
    }

    @XmlAttribute
    public String getNewtype() {
	return newtype;
    }
}
