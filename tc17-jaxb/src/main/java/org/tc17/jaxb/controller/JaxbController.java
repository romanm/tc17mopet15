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
import org.tasclin1.mopet.domain.Tree;
import org.tasclin1.mopet.service.MopetService;
import org.tc17.jaxb.core.Appx;
import org.tc17.jaxb.core.Dayx;
import org.tc17.jaxb.core.Dosex;
import org.tc17.jaxb.core.Drugx;
import org.tc17.jaxb.core.Exprx;
import org.tc17.jaxb.core.FindingPatientx;
import org.tc17.jaxb.core.Laborx;
import org.tc17.jaxb.core.Noticex;
import org.tc17.jaxb.core.OfDate;
import org.tc17.jaxb.core.Patientx;
import org.tc17.jaxb.core.Pvariablex;
import org.tc17.jaxb.core.Rulex;
import org.tc17.jaxb.core.TaskPatientx;
import org.tc17.jaxb.core.TaskRegimex;
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
    	TaskRegimex taskx = jaxbService.loadTaskx(pasteNodeId2);
//    	Drugx drugx = loadDrugx(pasteNodeId2);
//    	Tree drugT = buildTree(drugx);
//    	log.debug("-------------" + drugT);
    	Tree buildTree = jaxbService.buildTree(taskx);
    	log.debug(buildTree);
    	return taskx;
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
    	} else if (t0.isExpr()) {
    		mtlX = regimeExpr(t0);
    	} else if (t0.isDose()) {
    		mtlX = regimeDrugDose(t0);
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
    		}else if(dayT.isExpr()){
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
