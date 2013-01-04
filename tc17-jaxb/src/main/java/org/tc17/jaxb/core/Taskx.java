package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;

import org.tasclin1.mopet.domain.Tree;

public class Taskx extends Treex {
	public Taskx() {
	}

	public Taskx(Tree taskT) {
		super(taskT);
		taskName = taskT.getTaskO().getTask();
	}

	private String taskName, taskvar, type;

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	public String getTaskvar() {
		return taskvar;
	}

	public void setTaskvar(String taskvar) {
		this.taskvar = taskvar;
	}

	@XmlAttribute
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	ArrayList<Drugx> drug;

	public ArrayList<Drugx> getDrug() {
		if (null == drug)
			drug = new ArrayList<Drugx>();
		return drug;
	}

	public void setDrug(ArrayList<Drugx> drug) {
		this.drug = drug;
	}

}
