package org.tc17.m15.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;

@Controller
public class MopetController extends BasisController{
	protected final Log log = LogFactory.getLog(getClass());
	private MopetService mopetService;

	@Autowired
	public MopetController(MopetService mopetService) {
		this.mopetService = mopetService;
		// mopetService.init();
	}

    // Patient ChemoRegime
	@RequestMapping(value = "/f={idFolder}/p={idPatient}/s={idStudy}/cere-{regimeView}={idRegime}", method = RequestMethod.GET)
    public void folderPatientConceptRegime(@PathVariable
    Integer idFolder, @PathVariable
    Integer idPatient, @PathVariable
    Integer idStudy, @PathVariable
    String regimeView, @PathVariable
    Integer idRegime, Model model) {
	Tree patientT = mopetService.readPatientDocShort(idPatient, model);
	lastUsedDocuments(model, patientT);
	readRegime(idFolder, idStudy, regimeView, idRegime, model);
    }

    // Patient ChemoRegime END
    // ChemoRegime
	@RequestMapping(value = "/f={idFolder}/s={idStudy}/cere-{regimeView}={idRegime}", method = RequestMethod.GET)
	public void folderConceptRegime(@PathVariable
			Integer idFolder, @PathVariable
			Integer idStudy, @PathVariable
			String regimeView, @PathVariable
			Integer idRegime, Model model) {
		// Tree tree = mopetService.checkId(idRegime);
		// if (null == tree)
		// return "redirect:/";
		readRegime(idFolder, idStudy, regimeView, idRegime, model);
		// return "/f=" + idFolder + "/s=" + idStudy + "/cere-" + regimeView + "=" + idRegime;
	}

	private void readRegime(Integer idFolder, Integer idStudy, String regimeView, Integer idRegime, Model model) {
		addRegimeView(regimeView, model);
		mopetService.readFolderO2doc(idFolder, model);
		Tree conceptT = mopetService.readConceptT(idStudy, model);
		lastUsedDocuments(model, conceptT);
		mopetService.readRegimeDocT(idRegime, model);
		// mopetService.initRegimeDocT(model);
		model.addAttribute("docId", idRegime);
		Tree sessionPatientT = (Tree) getRequest().getSession().getAttribute("sessionPatientT");
		if (null != sessionPatientT)
			model.addAttribute("sessionPatientT", sessionPatientT);
		Tree docT = (Tree) model.asMap().get(MopetService.REGIMET);
		lastUsedDocuments(model, docT);
	}
	
	// ChemoRegime END

	// Concept
	@RequestMapping(value = "f={idFolder}/study-{studyPart}={idStudy}", method = RequestMethod.GET)
	public void folderConcept(@PathVariable
			Integer idFolder, @PathVariable
			String studyPart, @PathVariable
			Integer idStudy, Model model) {
		readConcept(idFolder, studyPart, idStudy, model);
	}
	private void readConcept(Integer idFolder, String studyPart, Integer idStudy, Model model) {
		log.debug(1);
		addStudyView(studyPart, model);
		mopetService.readFolderO2doc(idFolder, model);
		Tree conceptT = mopetService.readConceptDocT(idStudy, model);
		lastUsedDocuments(model, conceptT);
		getRequest().getSession().setAttribute("studyPart", studyPart);
		model.addAttribute("docId", idStudy);
		log.debug(getRequest().getSession().getAttribute("studyView"));
	}
	private void lastUsedDocuments(Model model, Tree docT) {
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
	// Concept END
	// Folder
	@RequestMapping(value = "/folder={idFolder}", method = RequestMethod.GET)
	public void folder(@PathVariable
			Integer idFolder, Model model) {
		//		model.addAttribute("idFolder", idFolder);
		log.debug(1);
		log.debug(mopetService);
		log.debug(2);
		mopetService.readFolderO2folder(idFolder, model);
		//		model.addAttribute("docId", idFolder);
	}
	// Folder END

	// home
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homeBegin(Model model) {
		mopetService.home(model);
		return "home";
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		mopetService.home(model);
		return "home";
	}
	// home END
	@RequestMapping(value = "id={id}", method = RequestMethod.GET)
	public String fromId(@PathVariable
			Integer id) {
		// Integer id, Model model) {
		log.debug(id);
		Tree tree = mopetService.checkId(id);
		log.debug(tree);

		if (null == tree)
			return "redirect:/";
		int idFolder;
		boolean isFolder = "folder".equals(tree.getTabName());
		if (isFolder) {
			idFolder = tree.getId();
			return "redirect:/folder=" + idFolder;
		}
		int idPatient;
		boolean isPatient = "patient".equals(tree.getTabName());
		if (isPatient) {
			idPatient = tree.getId();
			idFolder = tree.getParentT().getId();
			return "redirect:/f=" + idFolder + "/patient=" + idPatient;
		}
		int idConcept;
		boolean isConcept = "protocol".equals(tree.getTabName());
		if (isConcept) {
			idConcept = tree.getId();
			String study_Part_Id_Url = "/study-" + getStudyView() + "=" + idConcept;
			isPatient = "patient".equals(tree.getParentT().getTabName());
			if (isPatient) {
				idPatient = tree.getParentT().getId();
				idFolder = tree.getParentT().getParentT().getId();
				return "redirect:/f=" + idFolder + "/p=" + idPatient + study_Part_Id_Url;
			}
			if (!isPatient) {
				idFolder = tree.getParentT().getId();
				return "redirect:/f=" + idFolder + study_Part_Id_Url;
			}
		}
		int idRegime;
		boolean isRegime = "task".equals(tree.getTabName());
		if (isRegime) {
			idRegime = tree.getId();
			Tree conceptT = tree.getDocT();
			idConcept = conceptT.getId();
			String cere_Part_Id_Url = "/cere-" + getRegimeView() + "=" + idRegime;
			isPatient = "patient".equals(conceptT.getParentT().getTabName());
			if (isPatient) {
				idPatient = conceptT.getParentT().getId();
				idFolder = conceptT.getParentT().getParentT().getId();
				return "redirect:/f=" + idFolder + "/p=" + idPatient + "/s=" + idConcept + cere_Part_Id_Url;
			}
			if (!isPatient) {
				idFolder = conceptT.getParentT().getId();
				return "redirect:/f=" + idFolder + "/s=" + idConcept + cere_Part_Id_Url;
			}
		}
		return "redirect:/";
	}
	
}
