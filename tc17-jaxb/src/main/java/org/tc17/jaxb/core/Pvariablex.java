package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "pvariable")
public class Pvariablex extends Treex {

    public Pvariablex() {

    }

    public Pvariablex(Tree tree) {
	super(tree);
	pvariable = tree.getPvalueO().getPvariable();
	pvalue = tree.getPvalueO().getPvalue();
    }

    private String pvariable, pvalue;

    @XmlAttribute
    public String getPvariable() {
	return pvariable;
    }

    public void setPvariable(String pvariable) {
	this.pvariable = pvariable;
    }

    @XmlAttribute
    public String getPvalue() {
	return pvalue;
    }

    public void setPvalue(String pvalue) {
	this.pvalue = pvalue;
    }

}
