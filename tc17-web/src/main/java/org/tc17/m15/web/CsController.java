package org.tc17.m15.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;
import org.tc17.jaxb.controller.JaxbService;
import org.tc17.jaxb.core.Conceptx;
import org.tc17.jaxb.core.Folderx;
import org.tc17.jaxb.core.TaskRegimex;

@Controller
public class CsController extends BasisController{
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	private JaxbService jaxbService;
	@Autowired
	private MopetService mopetService;

	// ChemoRegime
	@RequestMapping(value = "/cs/cere-{regimeView}={idRegime}", method = RequestMethod.GET)
	public String readRegime(@PathVariable
			String regimeView, @PathVariable
			Integer idRegime, Model model) {
		addRegimeView(regimeView, model);
		//    	TaskRegimex taskx = jaxbService.loadTaskx(idRegime);
		TaskRegimex taskx 
		= (TaskRegimex) jaxbService.loadTaskx(idRegime,TaskRegimex.class);
		Tree regimeT = jaxbService.buildTree(taskx);
		log.debug(regimeT);
		model.addAttribute("regimeT", regimeT);
		mopetService.initRegimeDocT(model);
		return "thymeleaf/regime";
	}
	// ChemoRegime END

	// Concept
	@RequestMapping(value = "/cs/study-{studyPart}={idStudy}", method = RequestMethod.GET)
	public String folderConcept(@PathVariable
			String studyPart, @PathVariable
			Integer idStudy, Model model) {
		Conceptx conceptx 
		= (Conceptx) jaxbService.loadTaskx(idStudy,Conceptx.class);
		Tree conceptT = jaxbService.buildTree(conceptx);
		model.addAttribute("conceptT", conceptT);
		for (Tree tree : conceptT.getChildTs()) 
			if("definition".equals(tree.getTabName()))
			{
				model.addAttribute("conceptDefinitionT", tree);
			}
		return "thymeleaf/study";
	}
	// Concept END

	// Folder
	@RequestMapping(value = "/cs/tfolder={idFolder}", method = RequestMethod.GET)
	public String folder(@PathVariable
			Integer idFolder, Model model) {
		log.debug(1);
		Folderx folderx 
		= (Folderx) jaxbService.loadTaskx(idFolder,Folderx.class);
		Tree conceptT = jaxbService.buildTree(folderx,model);
		return "thymeleaf/folder";
	}
	// Folder END
}
