package org.tc17.m15.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tasclin1.mopet.service.MopetService;

public class BasisController {
	protected void addRegimeView(String regimeView, Model model) {
		model.addAttribute(MopetService.regimeView, regimeView);
		getRequest().getSession().setAttribute(MopetService.regimeView, regimeView);
	}
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request;
	}
	protected String getRegimeView() {
		String regimeView = (String) getRequest().getSession().getAttribute("regimeView");
		if (null == regimeView)
			regimeView = "ed";
		return regimeView;
	}
	protected void addStudyView(String studyView, Model model) {
		model.addAttribute(MopetService.studyView, studyView);
		getRequest().getSession().setAttribute(MopetService.studyView, studyView);
	}
	protected String getStudyView() {
		String studyView = (String) getRequest().getSession().getAttribute("studyView");
		if (null == studyView)
			studyView = "sg";
		return studyView;
	}
}
