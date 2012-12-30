package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "patient")
public class Patientx extends Treex {
    public Patientx() {

    }

    private String patient, forename;

    @XmlAttribute
    public String getPatient() {
	return patient;
    }

    public void setPatient(String patient) {
	this.patient = patient;
    }

    @XmlAttribute
    public String getForename() {
	return forename;
    }

    public void setForename(String forename) {
	this.forename = forename;
    }

    public Patientx(Tree tree) {
	super(tree);
	patient = tree.getParentO().getPatient();
	forename = tree.getParentO().getForename();
    }

    ArrayList<TaskPatientx> task;

    public void setTask(ArrayList<TaskPatientx> task) {
	this.task = task;
    }

    public ArrayList<TaskPatientx> getTask() {
	if (null == task)
	    task = new ArrayList<TaskPatientx>();
	return task;
    }

    ArrayList<FindingPatientx> finding;

    public ArrayList<FindingPatientx> getFinding() {
	if (null == finding)
	    finding = new ArrayList<FindingPatientx>();
	return finding;
    }

    public void setFinding(ArrayList<FindingPatientx> finding) {
	this.finding = finding;
    }

}
