package org.tc17.m15.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tasclin1.mopet.service.MopetService;

@Controller
public class ThymeleafController extends BasisController{
	protected final Log log = LogFactory.getLog(getClass());
	private MopetService mopetService;

	@Autowired
	public ThymeleafController(MopetService mopetService) {
		this.mopetService = mopetService;
		// mopetService.init();
	}
	@RequestMapping(value = "/cere-{regimeView}={idRegime}", method = RequestMethod.GET)
    public String readRegime(@PathVariable
    String regimeView, @PathVariable
    Integer idRegime, Model model) {
		addRegimeView(regimeView, model);
		mopetService.readRegimeDocT(idRegime, model);
		return "thymeleaf/my";
	}
	/**
	 * ThymeLeaf my
	 */
	@RequestMapping(value="/my/thymeleaf",method=RequestMethod.GET)
	public String findUsersThymeLeaf(Model model){
		Integer idRegime=906823;
		mopetService.readRegimeDocT(idRegime, model);
		Object object = model.asMap().get(MopetService.REGIMET);
		System.out.println(object);
		return "thymeleaf/my";
	}
	/**
	 * ThymeLeaf layout
	 */
	@RequestMapping(value="/layout/all/thymeleaf",method=RequestMethod.GET)
	public String findLayoutThymeLeaf(Model model){
		model.addAttribute("title", "Layout List - Thymeleaf");
		return "thymeleaf/layout";
	}
	@RequestMapping(value="/layout/all/tc17layout",method=RequestMethod.GET)
	public String findTc17LayoutThymeLeaf(Model model){
		model.addAttribute("title", "tc17Layout List - Thymeleaf");
		return "thymeleaf/tc17layout";
	}
}
