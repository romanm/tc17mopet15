package org.tc17.jaxb.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.tasclin1.mopet.domain.Drug;
import org.tasclin1.mopet.domain.Task;
import org.tasclin1.mopet.domain.Tree;
import org.tc17.jaxb.core.Dosex;
import org.tc17.jaxb.core.Drugx;
import org.tc17.jaxb.core.TaskRegimex;

@Service("jaxbService")
public class JaxbService {
	protected final Log log = LogFactory.getLog(getClass());
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
	
	public TaskRegimex loadTaskx(Integer pasteId) {
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
}
