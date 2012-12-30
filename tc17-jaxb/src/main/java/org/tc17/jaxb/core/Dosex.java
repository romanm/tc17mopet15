package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Dose;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "dose")
public class Dosex extends Treex {

    public Dosex() {

    }

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

    @XmlAttribute
    public Float getValue() {
	return value;
    }

    @XmlAttribute
    public String getUnit() {
	return unit;
    }

    @XmlAttribute
    public String getApp() {
	return app;
    }

    @XmlAttribute
    public String getPro() {
	return pro;
    }

    @XmlAttribute
    public String getType() {
	return type;
    }

    private Float value;

    public void setValue(Float value) {
	this.value = value;
    }

    private String unit, app, pro, type;

    public void setType(String type) {
	this.type = type;
    }

    public void setPro(String pro) {
	this.pro = pro;
    }

    public void setApp(String app) {
	this.app = app;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

}
