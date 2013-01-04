package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Concept;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "concept")
public class Conceptx  extends Treex {
	private String protocoltype,protocol;
	Definitionx definition;
	public void setDefinition(Definitionx definition) {
		this.definition = definition;
	}
	public Definitionx getDefinition() {
		return definition;
	}

    ArrayList<Taskx> task;

    public ArrayList<Taskx> getTask() {
	if (null == task)
	    task = new ArrayList<Taskx>();
	return task;
    }

    public void setTask(ArrayList<Taskx> task) {
	this.task = task;
    }

	@XmlAttribute
	public String getProtocoltype() {
		return protocoltype;
	}
	@XmlAttribute
	public String getProtocol() {
		return protocol;
	}

	public void setProtocoltype(String protocoltype) {
		this.protocoltype = protocoltype;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Conceptx() {
	}

	public Conceptx(Tree conceptT) {
		super(conceptT);
		Concept conceptO = conceptT.getConceptO();
		if (null != conceptO) {
			protocol = conceptO.getProtocol();
			protocoltype = conceptO.getProtocoltype();
		}
	}
}
