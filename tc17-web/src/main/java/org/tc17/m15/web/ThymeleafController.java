package org.tc17.m15.web;


import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;

@Controller
public class ThymeleafController extends BasisController{
	protected final Log log = LogFactory.getLog(getClass());
	private MopetService mopetService;
	@Autowired RepositoryImpl repository;
	@Autowired
	public ThymeleafController(MopetService mopetService) {
		this.mopetService = mopetService;
		// mopetService.init();
	}
	@RequestMapping(value = "/thome", method = RequestMethod.GET)
	public String home(Model model) {
		mopetService.home(model);
		
		return "thymeleaf/home";
	}
	@RequestMapping(value = "/jrbegin", method = RequestMethod.GET)
	public String jrbegin(Model model) throws RepositoryException {
		log.debug(repository);

		Session session=null;
		try {
			session = repository.login(
					new SimpleCredentials("admin", "admin".toCharArray()));
			
			Node root = session.getRootNode(); 
			model.addAttribute("root", root);
			
			// Store content
			Node hello = root.addNode("hello"); 
			Node world = hello.addNode("world"); 
			world.setProperty("message", "Hello, World!"); 
			session.save(); 

			// Retrieve content 
			Node node = root.getNode("hello/world"); 
			model.addAttribute("node", node);
			System.out.println(node.getPath());
			System.out.println(node.getProperty("message").getString()); 

			// Remove content 
			root.getNode("hello").remove(); 
			session.save(); 
		} catch (RepositoryException e) {
			e.printStackTrace();
		} finally { 
//			if(null!=session) session.logout(); 
		}
		Node node = (Node) model.asMap().get("node");
		log.debug(node.getPath());
		log.debug(node.getProperty("message").getString());
		return "thymeleaf/jrbegin";
	}
	// Folder
	@RequestMapping(value = "/tfolder={idFolder}", method = RequestMethod.GET)
	public String folder(@PathVariable
			Integer idFolder, Model model) {
		mopetService.readFolderO2folder(idFolder, model);
		return "thymeleaf/folder";
	}
	// Folder END
	@RequestMapping(value = "/study-{studyView}={idStudy}", method = RequestMethod.GET)
    public String readConcept(@PathVariable
    String studyView, @PathVariable
    Integer idStudy, Model model) {
		addStudyView(studyView, model);
		Tree conceptT = mopetService.readConceptDocT(idStudy, model);
		Integer idFolder = conceptT.getParentT().getId();
		mopetService.readFolderO2doc(idFolder, model);
		lastUsedDocuments(model, conceptT);
		getRequest().getSession().setAttribute("studyView", studyView);
		return "thymeleaf/study";
	}

	@RequestMapping(value = "/cere-{regimeView}={idRegime}", method = RequestMethod.GET)
    public String readRegime(@PathVariable
    String regimeView, @PathVariable
    Integer idRegime, Model model) {
		addRegimeView(regimeView, model);
		Tree regimeT = mopetService.readRegimeDocT(idRegime, model);
		Integer idStudy = regimeT.getDocT().getId();
		mopetService.readConceptT(idStudy, model);
		return "thymeleaf/regime";
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
		return "thymeleaf/regime";
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
