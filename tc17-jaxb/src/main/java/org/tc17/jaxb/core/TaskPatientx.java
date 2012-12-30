package org.tc17.jaxb.core;

import org.tasclin1.mopet.domain.Tree;

public class TaskPatientx extends Taskx implements OfDate {

    private Pvariablex pvCycle;

    public Pvariablex getPvCycle() {
	return pvCycle;
    }

    private Pvariablex pvOfDate;

    public Pvariablex getPvOfDate() {
	return pvOfDate;
    }

    public void setPvOfDate(Pvariablex pvOfDate) {
	this.pvOfDate = pvOfDate;
    }

    public TaskPatientx() {

    }

    public TaskPatientx(Tree taskT) {
	super(taskT);
    }

    public void setPvCycle(Pvariablex pvCycle) {
	this.pvCycle = pvCycle;
    }

}
