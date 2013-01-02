package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "taskRegime")
public class TaskRegimex extends Taskx {
    public TaskRegimex() {
    }

    public TaskRegimex(Tree tree) {
	super(tree);
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

    ArrayList<Laborx> labor;

    public ArrayList<Laborx> getLabor() {
	if (null == labor)
	    labor = new ArrayList<Laborx>();
	return labor;
    }

    public void setLabor(ArrayList<Laborx> labor) {
	this.labor = labor;
    }

    /*
     * ArrayList<TaskOnex> taskOne; public ArrayList<TaskOnex> getTaskOne() { if (null == taskOne) taskOne = new
     * ArrayList<TaskOnex>(); return taskOne; }
     * 
     * public void setTaskOne(ArrayList<TaskOnex> taskOne) { this.taskOne = taskOne; }
     */

}
