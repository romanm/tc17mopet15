package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "finding")
public class FindingPatientx extends Findingx implements OfDate {

    public FindingPatientx() {

    }

    public FindingPatientx(Tree tree) {
	super(tree);
    }

    private Pvariablex pvOfDate;

    public Pvariablex getPvOfDate() {
	return pvOfDate;
    }

    public void setPvOfDate(Pvariablex pvOfDate) {
	this.pvOfDate = pvOfDate;
    }

}
