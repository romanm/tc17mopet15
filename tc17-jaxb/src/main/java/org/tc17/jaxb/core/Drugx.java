package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "drug")
public class Drugx extends TaskOnex {
    // public class Drugx extends Treex {
    protected final Log log = LogFactory.getLog(getClass());

    Dosex dose;

    private Appx app;

    public Appx getApp() {
	return app;
    }

    public Dosex getDose() {
	return dose;
    }

    public void setApp(Appx app) {
	this.app = app;
    }

    public void setDose(Dosex dose) {
	this.dose = dose;
    }

    @XmlAttribute
    public String getDrug() {
	return drug;
    }

    String drug, generic;

    @XmlAttribute
    public String getGeneric() {
	return generic;
    }

    public void setGeneric(String generic) {
	this.generic = generic;
    }

    public void setDrug(String drug) {
	this.drug = drug;
    }

    /*
     * public Drugx(int id, int idclass, String drug) { this.id = id; this.idclass = idclass; this.drug = drug; }
     */
    public Drugx() {
    }

    public Drugx(Tree tree) {
	super(tree);
	drug = tree.getDrugO().getDrug();
	generic = tree.getDrugO().getGeneric().getDrug();
    }
}
