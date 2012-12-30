package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "regime")
public class Regimex extends Treex {

    ArrayList<Drugx> drug;

    public Regimex() {
	super();
    }

    public Regimex(int id, int idclass, String name) {
	this.id = id;
	this.idclass = idclass;
	this.name = name;
    }

    public ArrayList<Drugx> getDrug() {
	return drug;
    }

    public void setDrug(ArrayList<Drugx> drug) {
	this.drug = drug;
	// for (Treex tree : drug)
	// tree.setDid(this.getId());
    }

    String name;

    @XmlAttribute
    public String getName() {
	return name;
    }
}
