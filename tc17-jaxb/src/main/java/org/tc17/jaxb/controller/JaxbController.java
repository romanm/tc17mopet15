package org.tc17.jaxb.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
import org.tasclin1.mopet.domain.Drug;
import org.tasclin1.mopet.domain.Task;
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;
import org.tc17.jaxb.core.Appx;
import org.tc17.jaxb.core.Dayx;
import org.tc17.jaxb.core.Dosex;
import org.tc17.jaxb.core.Drugx;
import org.tc17.jaxb.core.FindingPatientx;
import org.tc17.jaxb.core.Laborx;
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
	// System.out.println(drugXml.getDrug());
	// ArrayList<Dayx> day = drugXml.getDay();
	// for (Dayx dayx : day) {
	// System.out.println(dayx);
	// System.out.println(dayx.getAbs());
	// }
	// Dosex dose = drugXml.getDose();
	// System.out.println(dose);
	// System.out.println(dose.getId());
	// System.out.println(dose.getValue());
	// System.out.println(dose.getApp());
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
    	String pasteNodeId = pasteId.split("_")[1];
    	int pasteNodeId2 = Integer.parseInt(pasteNodeId);
    	// String readJaxb = readJaxb(pasteId);
    	log.debug("-------------" + pasteNodeId2);
    	Treex taskx = loadTaskx(pasteNodeId2);
//    	Drugx drugx = loadDrugx(pasteNodeId2);
//    	Tree drugT = buildTree(drugx);
//    	log.debug("-------------" + drugT);
    	return taskx;
    }
    public Tree buildTree(TaskRegimex taskX) {
    	Tree taskT = new Tree();
    	taskT.setTabName("task");
    	taskT.setMtlO(new Task());
    	taskT.getTaskO().setTask(taskX.getTaskName());
    	taskT.getTaskO().setTaskvar(taskX.getTaskvar());
    	taskT.getTaskO().setType(taskX.getType());
    	if(taskX.getDrug().size()>0){
    		taskT.setChildTs(new ArrayList<Tree>());
    		for (Drugx drugX : taskX.getDrug()) {
    			Tree buildTree = buildTree(drugX);
    			taskT.getChildTs().add(buildTree);
    		}
    	}
    	return taskT;
    }
    public Tree buildTree(Drugx drugX) {
    	Tree drugT = new Tree();
    	drugT.setTabName("drug");
    	drugT.setMtlO(new Drug());
    	drugT.getDrugO().setDrug(drugX.getDrug());
    	drugT.getDrugO().setGeneric(new Drug());
    	drugT.getDrugO().getGeneric().setDrug(drugX.getGeneric());
    	ArrayList<Tree> taskTchilds = new ArrayList<Tree>();
    	Dosex dose = drugX.getDose();
    	if(null!=dose)
    	{
    		
    	}
    	return drugT;
    }
    private TaskRegimex loadTaskx(Integer pasteId) {
    	TaskRegimex taskX = null;
    	JAXBContext newInstance;
    	URL url;
    	log.debug(1);
    	try {
    		log.debug(2);
    		newInstance = JAXBContext.newInstance(TaskRegimex.class);
    		log.debug(3);
    		Unmarshaller createUnmarshaller = newInstance.createUnmarshaller();
    		log.debug(4);
    		String urlStr = "http://localhost:8080/tc17-web/xml=x_" + pasteId;
    		log.debug(urlStr);
			url = new URL(urlStr);
			log.debug(url);
    		taskX = (TaskRegimex) createUnmarshaller.unmarshal(url);
    		log.debug(taskX);
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (JAXBException e) {
    		e.printStackTrace();
    	}
    	return taskX;
    }
    private Drugx loadDrugx(Integer pasteId) {
    	Drugx drugx = null;
    	JAXBContext newInstance;
    	URL url;
    	try {
    		newInstance = JAXBContext.newInstance(Drugx.class);
    		Unmarshaller createUnmarshaller = newInstance.createUnmarshaller();
    		url = new URL("http://localhost:8080/tc17-web/xml=x_" + pasteId);
    		drugx = (Drugx) createUnmarshaller.unmarshal(url);
    		log.debug(drugx);
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (JAXBException e) {
    		e.printStackTrace();
    	}
    	return drugx;
    }
    @RequestMapping(value = "/xml={htmlId}", method = RequestMethod.GET,
    		produces = "application/xml")
    public @ResponseBody
    Treex xml(@PathVariable
    		String htmlId, Model model) {
    	log.debug(123);
    	log.debug(1);
    	Integer id = mopetService.getIdFromHtmlId(htmlId);
    	log.debug(id);
    	Tree t0 = mopetService.readNodes4(id, model);
    	Treex mtlX = null;
    	if (t0.isTask()) {
    		mtlX = regimeTaskx(t0);
    	} else if (t0.isDrug()) {
    		mtlX = regimeDrugx(t0);
    	} else if (t0.isPatient()) {
    		mtlX = regimePatientx(t0);
    	} else if (t0.isDose()) {
    		mtlX = new Dosex(t0);
    	} else if (t0.isDay()) {
    		mtlX = regimeDrugDay(t0);
    	} else if (t0.isTimes()) {
    		mtlX = new Timesx(t0);
    	} else {
    		log.info("TODO! \n"+t0);
    	}
    	return mtlX;
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

    private TaskRegimex regimeTaskx(Tree taskT) {
	TaskRegimex taskx = new TaskRegimex(taskT);
	for (Tree t1 : taskT.getChildTs())
	    if (t1.isDrug()) {
		// taskx.getTaskOne().add(regimeDrugx(t1));
		taskx.getDrug().add(regimeDrugx(t1));
	    } else if (t1.isTask()) {
		taskx.getTask().add(regimeTaskx(t1));
	    } else if (t1.isLabor()) {
		taskx.getLabor().add(regimeLaborx(t1));
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

    private Drugx regimeDrugx(Tree drugT) {
	Drugx drugx = new Drugx(drugT);
	for (Tree t1 : drugT.getChildTs())
	    if (t1.isDose()) {
		drugx.setDose(new Dosex(t1));
	    } else if (t1.isApp()) {
		drugx.setApp(new Appx(t1));
	    } else if (t1.isDay()) {
		drugx.getDay().add(regimeDrugDay(t1));
	    }
	return drugx;
    }

    private Dayx regimeDrugDay(Tree t1) {
	Dayx dayx = new Dayx(t1);
	for (Tree t2 : t1.getChildTs()) {
	    if (t2.isTimes()) {
		// dayx.setTimes(new Timesx(t2));
		dayx.getTimes().add(new Timesx(t2));
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
