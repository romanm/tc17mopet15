package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "definition")
public class Definitionx  extends Treex {
    public Definitionx() { } 
    public Definitionx(Tree definitionT) {
    	super(definitionT);
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

}
