package org.tasclin1.mopet.service;

import java.util.ArrayList;
import java.util.List;


public class PlanChronoFormat {
	private List<Plancolumns> plancolumns;

	public List<Plancolumns> getPlancolumns() {
		return plancolumns;
	}

	public PlanChronoFormat() {
		plancolumns = new ArrayList<Plancolumns>();
		plancolumns.add(Plancolumns.times);
		plancolumns.add(Plancolumns.drug);
		plancolumns.add(Plancolumns.dose);
		// plancolumns.add(Plancolumns.appDuration);
		plancolumns.add(Plancolumns.dayHour);
		plancolumns.add(Plancolumns.timesNumer);
	}
}