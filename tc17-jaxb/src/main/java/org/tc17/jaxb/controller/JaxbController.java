package org.tc17.jaxb.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;
import org.tc17.jaxb.core.Appx;
import org.tc17.jaxb.core.Conceptx;
import org.tc17.jaxb.core.Dayx;
import org.tc17.jaxb.core.Definitionx;
import org.tc17.jaxb.core.Dosex;
import org.tc17.jaxb.core.Drugx;
import org.tc17.jaxb.core.Exprx;
import org.tc17.jaxb.core.FindingPatientx;
import org.tc17.jaxb.core.Folderx;
import org.tc17.jaxb.core.Laborx;
import org.tc17.jaxb.core.Noticex;
import org.tc17.jaxb.core.OfDate;
import org.tc17.jaxb.core.Patientx;
import org.tc17.jaxb.core.Pvariablex;
import org.tc17.jaxb.core.TaskPatientx;
import org.tc17.jaxb.core.TaskRegimex;
import org.tc17.jaxb.core.Taskx;
import org.tc17.jaxb.core.Timesx;
import org.tc17.jaxb.core.Treex;

@Controller
public class JaxbController {
    protected final Log log = LogFactory.getLog(getClass());
    @Autowired
    private MopetService mopetService;
    @Autowired
    private JaxbService jaxbService;

    @RequestMapping(value = "/jaxb_drug2", method = RequestMethod.POST)
    public @ResponseBody
    String readXml2(@RequestBody
    String drugXml) {
	log.debug(drugXml);
	return "Read from XML: " + drugXml;
    }

    @RequestMapping(value = "/jaxb_drug", method = RequestMethod.POST)
    public @ResponseBody
    String readXml(@RequestBody
    		Drugx drugXml) {
    	log.debug(1);
    	System.out.println("-------------------");
    	System.out.println(drugXml);
    	System.out.println(drugXml.getId());
    	return "Read from XML: " + drugXml;
    }

    @RequestMapping(value = "/xxml={htmlId}", method = RequestMethod.GET,
    		produces = "application/xml")
    public @ResponseBody
    Treex xxml(@PathVariable
    String htmlId, Model model) {
    	log.debug(1);
    	log.debug(htmlId);
    	String pasteId = htmlId;
    	log.debug("-------------" + pasteId);
    	String[] split = pasteId.split("_");
    	String pasteNodeType = split[0];
		String pasteNodeId = split[1];
    	int pasteNodeId2 = Integer.parseInt(pasteNodeId);
    	// String readJaxb = readJaxb(pasteId);
    	log.debug("-------------" + pasteNodeId2);
    	Class<?> classType = TaskRegimex.class;
    	if("c".equals(pasteNodeType))
    		classType = Conceptx.class;
    	else if("f".equals(pasteNodeType))
    		classType = Folderx.class;
    	Treex treex = jaxbService.loadTaskx(pasteNodeId2,classType);
    	log.debug("-------------" + treex);
    	return treex;
    }
    
    @RequestMapping(value = "/xml={htmlId}", method = RequestMethod.GET,
    		produces = "application/xml")
    public @ResponseBody
    Treex xml(@PathVariable
    		String htmlId, Model model) {
    	Integer id = mopetService.getIdFromHtmlId(htmlId);
    	log.debug(id);
    	Tree tree = mopetService.checkId(id);
    	log.debug(tree);
    	Treex mtlX = null;
    	if(tree.isFolder()){
    		mopetService.readFolderO2folder(id, model);
    		mtlX=folderX(model);
    	}else{
    		Tree t0 = mopetService.readNodes4(id, model);
    		log.debug(t0);
    		if (t0.isTask()) {
    			mtlX = regimeTaskx(t0);
    		} else if (t0.isDrug()) {
    			mtlX = regimeDrugx(t0);
    		} else if (t0.isPatient()) {
    			mtlX = regimePatientx(t0);
    		} else if (t0.isExpr()) {
    			mtlX = regimeExpr(t0);
    		} else if (t0.isDose()) {
    			mtlX = regimeDrugDose(t0);
    		} else if (t0.isDay()) {
    			mtlX = regimeDrugDay(t0);
    		} else if (t0.isTimes()) {
    			mtlX = new Timesx(t0);
    		} else if (t0.isConcept()) {
    			mtlX = conceptStudyx(t0);
    		} else {
    			log.info("TODO! \n"+t0);
    		}
    	}
    	log.debug(mtlX);
    	return mtlX;
    }

    private Folderx folderX(Model model) {
    	Tree folderT = (Tree) model.asMap().get("folderT");
    	Folderx folderX = new Folderx(folderT);
    	for (Tree subFolderT : folderT.getChildTs()) 
    		if(subFolderT.isFolder())
    			folderX.getSubfolder().add(new Folderx(subFolderT));
    	setParentfolder(folderT.getParentT(),folderX);
    	for (Tree conceptT : folderT.getChildTs()) 
    		if(conceptT.isConcept())
    			folderX.getConcept().add(folderX(conceptT,model));
    	return folderX;
    }

	private Conceptx folderX(Tree conceptT, Model model) {
		Conceptx conceptx = new Conceptx(conceptT);
		Map<Integer, List<Tree>> conceptRegime = 
		(Map<Integer, List<Tree>>) model.asMap().get("conceptRegime");
		if(conceptRegime.containsKey(conceptx.getId()))
		for (Tree cRegimeT : conceptRegime.get(conceptx.getId())) {
			conceptx.getConceptRegime().add(new TaskRegimex(cRegimeT));
		}
		return conceptx;
	}

	private void setParentfolder(Tree parentT, Folderx folderX) {
		if(null!=parentT.getFolderO()
		&&!"folder".equals(parentT.getFolderO().getFolder())
		){
			folderX.getParentfolder().add(new Folderx(parentT));
			setParentfolder(parentT.getParentT(),folderX);
		}
	}

	private Patientx regimePatientx(Tree taskT) {
		Patientx patientx = new Patientx(taskT);
		for (Tree t1 : taskT.getChildTs())
			if (t1.isTask()) {
				patientx.getTask().add(taskPatientx(t1));
			} else if (t1.isFinding()) {
				patientx.getFinding().add(findingPatientx(t1));
			}
		return patientx;
	}

	private FindingPatientx findingPatientx(Tree t0) {
		FindingPatientx findingPatientx = new FindingPatientx(t0);
		for (Tree t1 : t0.getChildTs()) {
			addOfDate(findingPatientx, t1);
		}
		return findingPatientx;
	}

    private TaskPatientx taskPatientx(Tree taskT) {
    	TaskPatientx taskPatientx = new TaskPatientx(taskT);
    	for (Tree t1 : taskT.getChildTs()) {
    		addOfDate(taskPatientx, t1);
    		if (t1.isPvariable()) {
    			Pvariablex pvariablex = new Pvariablex(t1);
    			if ("cycle".equals(t1.getPvalueO().getPvariable())) {
    				taskPatientx.setPvCycle(pvariablex);
    			}
    		}
    	}
    	log.debug(taskPatientx);
    	return taskPatientx;
    }
    private Conceptx conceptStudyx(Tree conceptT) {
    	Conceptx conceptx = new Conceptx(conceptT);
    	for (Tree t1 : conceptT.getChildTs())
    		if ("definition".equals(t1.getTabName()) ){
    			Definitionx definitionx = new Definitionx(t1);
    			conceptx.setDefinition(definitionx);
    			for (Tree t2 : t1.getChildTs()) {
    				if (t2.isTask()) {
    					definitionx.getTask().add(new Taskx(t2));
    				}
    			}
    		} else if (t1.isTask()) {
    			conceptx.getTask().add(new Taskx(t1));
    		}
    	return conceptx;
    }
    private TaskRegimex regimeTaskx(Tree taskT) {
    	TaskRegimex taskx = new TaskRegimex(taskT);
    	for (Tree t1 : taskT.getChildTs())
    		if (t1.isDrug()) {
    			taskx.getDrug().add(regimeDrugx(t1));
    		} else if (t1.isTask()) {
    			taskx.getTask().add(regimeTaskx(t1));
    		} else if (t1.isLabor()) {
    			taskx.getLabor().add(regimeLaborx(t1));
    		}else if(t1.isNotice()){
    			taskx.getNotice().add(regimeNotice(t1));
    		}
    	return taskx;
    }

    private Laborx regimeLaborx(Tree laborT) {
	Laborx laborx = new Laborx(laborT);
	for (Tree t1 : laborT.getChildTs())
	    if (t1.isDay()) {
		laborx.getDay().add(regimeDrugDay(t1));
	    }
	return laborx;
    }
    private Exprx regimeExpr(Tree exprT) {
    	Exprx exprx = new Exprx(exprT);
    	for (Tree t1 : exprT.getChildTs())
    		if (t1.isExpr()) {
    			exprx.getEexpr().add(regimeExpr(t1));
    		}
    	return exprx;
    }
    private Noticex regimeNotice(Tree exprT) {
    	Noticex noticex = new Noticex(exprT);
		return noticex;
    }
    private Dosex regimeDrugDose(Tree doseT) {
		Dosex dosex = new Dosex(doseT);
		for (Tree t1 : doseT.getChildTs())
			if(t1.isExpr()){
				dosex.getExpr().add(regimeExpr(t1));
			}else if(t1.isNotice()){
				dosex.getNotice().add(regimeNotice(t1));
			}
		return dosex;
	}

    private Drugx regimeDrugx(Tree drugT) {
    	Drugx drugx = new Drugx(drugT);
    	for (Tree t1 : drugT.getChildTs())
    		if (t1.isDose()) {
    			drugx.setDose(regimeDrugDose(t1));
    		}else if(t1.isExpr()){
    			drugx.getExpr().add(regimeExpr(t1));
			}else if(t1.isNotice()){
				drugx.getNotice().add(regimeNotice(t1));
    		} else if (t1.isApp()) {
    			drugx.setApp(new Appx(t1));
    		} else if (t1.isDrug()){ 
    			drugx.getDdrug().add(regimeDrugDrug(t1));
    		} else if (t1.isDay()) {
    			drugx.getDay().add(regimeDrugDay(t1));
    		}
    	return drugx;
    }
	
    private Drugx regimeDrugDrug(Tree drugDrugT) {
    	Drugx drugx = new Drugx(drugDrugT);
    	for (Tree t1 : drugDrugT.getChildTs())
    	    if (t1.isDose()) {
    		drugx.setDose(regimeDrugDose(t1));
    	    }
    	return drugx;
    }
    private Dayx regimeDrugDay(Tree dayT) {
    	Dayx dayx = new Dayx(dayT);
    	for (Tree t1 : dayT.getChildTs()) {
    		if (t1.isTimes()) {
    			// dayx.setTimes(new Timesx(t2));
    			dayx.getTimes().add(new Timesx(t1));
    		}else if(t1.isExpr()){
    			dayx.getExpr().add(regimeExpr(t1));
    		}else if(t1.isNotice()){
				dayx.getNotice().add(regimeNotice(t1));
    		}
    	}
    	return dayx;
    }
    private void addOfDate(OfDate patientHistoryx, Tree t1) {
    	if (t1.isPvariable()) {
    	    Pvariablex pvariablex = new Pvariablex(t1);
    	    if ("ofDate".equals(t1.getPvalueO().getPvariable())) {
    		patientHistoryx.setPvOfDate(pvariablex);
    	    }
    	}
        }
}
