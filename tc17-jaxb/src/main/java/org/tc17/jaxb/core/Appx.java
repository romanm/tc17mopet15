package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.App;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement
public class Appx extends Treex {
    public Appx(Tree appT) {
	super(appT);
	App appO = appT.getAppO();
	if (null != appO) {
	    appapp = appO.getAppapp();
	    type = appO.getType();
	    unit = appO.getUnit();
	}
    }

    private Integer appapp;
    private String type, unit;

    @XmlAttribute
    public Integer getAppapp() {
	return appapp;
    }

    public void setAppapp(Integer appapp) {
	this.appapp = appapp;
    }

    @XmlAttribute
    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @XmlAttribute
    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public Appx() {
    }

}
