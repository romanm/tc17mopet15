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

    private String abs, newtype;
    ArrayList<Timesx> times;
    ArrayList<Exprx> expr;

    public ArrayList<Timesx> getTimes() {
	if (null == times)
	    times = new ArrayList<Timesx>();
	return times;
    }

    public void setTimes(ArrayList<Timesx> times) {
	this.times = times;
    }


	public void setExpr(ArrayList<Exprx> expr) {
		this.expr = expr;
	}

	public ArrayList<Exprx> getExpr() {
		if (null == expr)
			expr = new ArrayList<Exprx>();
		return expr;
	}


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

    ArrayList<Noticex> notice;
    public ArrayList<Noticex> getNotice() {
		if (null == notice)
			notice = new ArrayList<Noticex>();
		return notice;
	}

	public void setNotice(ArrayList<Noticex> notice) {
		this.notice = notice;
	}

}
