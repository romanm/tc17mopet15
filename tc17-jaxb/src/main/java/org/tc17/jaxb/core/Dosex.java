package org.tc17.jaxb.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Dose;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "dose")
public class Dosex extends Treex {
	private Float value; 
	private String unit, app, pro, type; 
	ArrayList<Noticex> notice;

    @XmlAttribute
    public Float getValue() { return value; } 
    @XmlAttribute
    public String getUnit() { return unit; } 
    @XmlAttribute
    public String getApp() { return app;}
    @XmlAttribute
    public String getPro() { return pro;} 
    @XmlAttribute
    public String getType() { return type;} 
    
    public Dosex() { } 
    public Dosex(Tree doseT) {
    	super(doseT);
    	Dose doseO = doseT.getDoseO();
    	if (null != doseO) {
    		value = doseO.getValue();
    		unit = doseO.getUnit();
    		app = doseO.getApp();
    		pro = doseO.getPro();
    		type = doseO.getType();
    	}
    }
    
    public void setValue(Float value) { this.value = value; } 
    public void setType(String type) { this.type = type; } 
    public void setPro(String pro) { this.pro = pro; } 
    public void setApp(String app) { this.app = app; } 
    public void setUnit(String unit) { this.unit = unit; } 
    public ArrayList<Noticex> getNotice() {
		if (null == notice) notice = new ArrayList<Noticex>();
		return notice;
	} 
	public void setNotice(ArrayList<Noticex> notice) { this.notice = notice; } 
	/*
	 */
	ArrayList<Exprx> expr; 
	public void setExpr(ArrayList<Exprx> expr) { this.expr = expr; } 
	public ArrayList<Exprx> getExpr() {
		if (null == expr) expr = new ArrayList<Exprx>();
		return expr;
	}
	ArrayList<Rulex> rule;
	public List<Tree> getRule() {
		// TODO Auto-generated method stub
		return null;
	}
}
