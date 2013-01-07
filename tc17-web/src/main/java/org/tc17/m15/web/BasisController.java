package org.tc17.m15.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;

public class BasisController {
	protected void lastUsedDocuments(Model model, Tree docT) {
		Map<Integer, Tree> lastUsedDocuments = (Map<Integer, Tree>) getRequest().getSession().getAttribute(
				"lastUsedDocuments");
		List<Integer> lastUsedDocumentsList = (List<Integer>) getRequest().getSession().getAttribute(
				"lastUsedDocumentsList");
		if (null == lastUsedDocuments) {
			lastUsedDocuments = new HashMap<Integer, Tree>();
			lastUsedDocumentsList = new ArrayList<Integer>();
			getRequest().getSession().setAttribute("lastUsedDocuments", lastUsedDocuments);
			getRequest().getSession().setAttribute("lastUsedDocumentsList", lastUsedDocumentsList);
			model.addAttribute("lastUsedDocuments", lastUsedDocuments);
			model.addAttribute("lastUsedDocumentsList", lastUsedDocumentsList);
		}
		if (!lastUsedDocuments.containsKey(docT.getId())) {
			lastUsedDocuments.put(docT.getId(), docT);
			lastUsedDocumentsList.add(0, docT.getId());
		}
		if (lastUsedDocuments.size() > 10) {
			Integer lastId = lastUsedDocumentsList.get(lastUsedDocumentsList.size() - 1);
			Tree lastDocT = lastUsedDocuments.get(lastId);
			lastUsedDocuments.remove(lastDocT);
		}
	}
	protected void addRegimeView(String regimeView, Model model) {
		model.addAttribute(MopetService.regimeView, regimeView);
		getRequest().getSession().setAttribute(MopetService.regimeView, regimeView);
	}
	public static HttpServletRequest getRequest() {
		HttpServletRequest request 
		= ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
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
