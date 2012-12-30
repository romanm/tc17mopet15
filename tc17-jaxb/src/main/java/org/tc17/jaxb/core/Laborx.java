package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "labor")
public class Laborx extends TaskOnex {
    protected final Log log = LogFactory.getLog(getClass());
    private String labor;

    @XmlAttribute
    public String getLabor() {
	return labor;
    }

    public void setLabor(String labor) {
	this.labor = labor;
    }

    public Laborx() {
    }

    public Laborx(Tree laborT) {
	super(laborT);
	labor = laborT.getLaborO().getLabor();
    }

}
