package org.tc17.jaxb.core;

import java.util.ArrayList;

import org.tasclin1.mopet.domain.Tree;

public class TaskOnex extends Treex {

    public TaskOnex() {
    }

    public TaskOnex(Tree tree) {
	super(tree);
    }

    ArrayList<Dayx> day;

    public ArrayList<Dayx> getDay() {
	if (null == day)
	    day = new ArrayList<Dayx>();
	return day;
    }

    public void setDay(ArrayList<Dayx> day) {
	this.day = day;
    }

}
