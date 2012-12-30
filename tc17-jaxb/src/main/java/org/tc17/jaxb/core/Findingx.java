package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "finding")
public class Findingx extends Treex {

    public Findingx() {

    }

    public Findingx(Tree tree) {
	super(tree);
	finding = tree.getFindingO().getFinding();
	unit = tree.getFindingO().getUnit();
    }

    private String finding, unit;

    @XmlAttribute
    public String getFinding() {
	return finding;
    }

    public void setFinding(String finding) {
	this.finding = finding;
    }

    @XmlAttribute
    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

}
