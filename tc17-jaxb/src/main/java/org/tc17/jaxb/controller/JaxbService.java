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
import org.tasclin1.mopet.domain.Day;
import org.tasclin1.mopet.domain.Dose;
import org.tasclin1.mopet.domain.Drug;
import org.tasclin1.mopet.domain.Expr;
import org.tasclin1.mopet.domain.Task;
import org.tasclin1.mopet.domain.Times;
import org.tasclin1.mopet.domain.Tree;
import org.tc17.jaxb.core.Conceptx;
import org.tc17.jaxb.core.Dayx;
import org.tc17.jaxb.core.Dosex;
import org.tc17.jaxb.core.Drugx;
import org.tc17.jaxb.core.Exprx;
import org.tc17.jaxb.core.TaskRegimex;
import org.tc17.jaxb.core.Taskx;
import org.tc17.jaxb.core.Timesx;
import org.tc17.jaxb.core.Treex;

@Service("jaxbService")
public class JaxbService {
	protected final Log log = LogFactory.getLog(getClass());
	public Tree buildTree(TaskRegimex taskX) {
		Tree taskT = buildTree(taskX, "task");
    	taskT.setMtlO(new Task());
    	taskT.getTaskO().setTask(taskX.getTaskName());
    	taskT.getTaskO().setTaskvar(taskX.getTaskvar());
    	taskT.getTaskO().setType(taskX.getType());
    	if(taskX.getDrug().size()>0)
    		for (Drugx drugX : taskX.getDrug()) {
    			Tree buildTree = buildTree(drugX);
    			buildTree(taskT, buildTree);
    		}
    	if(taskX.getTask().size()>0)
    		for (Taskx taskTaskX : taskX.getTask()) {
    			Tree buildTree = buildTree(taskTaskX);
    			buildTree(taskT, buildTree);
    	}
    	return taskT;
    }
	private Tree buildTree(Taskx taskX) {
    	Tree taskT = buildTree(taskX, "task");
    	taskT.setMtlO(new Task());
    	taskT.getTaskO().setTask(taskX.getTaskName());
    	taskT.getTaskO().setTaskvar(taskX.getTaskvar());
    	taskT.getTaskO().setType(taskX.getType());
    	if(taskX.getDrug().size()>0)
    		for (Drugx drugX : taskX.getDrug()) {
    			Tree buildTree = buildTree(drugX);
    			buildTree(taskT, buildTree);
    		}
		return taskT;
	}
	public Tree buildTree(Drugx drugX) {
		Tree drugT = buildTree(drugX, "drug");
		drugT.setMtlO(new Drug());
		drugT.getDrugO().setDrug(drugX.getDrug());
		drugT.getDrugO().setGeneric(new Drug());
		drugT.getDrugO().getGeneric().setDrug(drugX.getGeneric());
		if(drugX.getDay().size()>0)
			for (Dayx dayX : drugX.getDay()) {
				Tree buildTree = buildTree(dayX);
				buildTree(drugT, buildTree);
			}
		if(null!=drugX.getDose()){
			Tree buildTree = buildTree(drugX.getDose());
			buildTree(drugT, buildTree);
		}
		if(drugX.getDdrug().size()>0)
			for (Drugx ddrugX : drugX.getDdrug()) {
				Tree buildTree = buildTree(ddrugX);
				buildTree(drugT, buildTree);
			}
		return drugT;
	}
	private void buildTree(Tree parentT, Tree childT) {
		if(null==parentT.getChildTs())
			parentT.setChildTs(new ArrayList<Tree>());
		parentT.getChildTs().add(childT);
		childT.setParentT(parentT);
	}
	private Tree buildTree(Dayx dayX) {
		Tree dayT = buildTree(dayX, "day");
		dayT.setMtlO(new Day());
		dayT.getDayO().setAbs(dayX.getAbs());
		dayT.getDayO().setNewtype(dayX.getNewtype());
		if(dayX.getTimes().size()>0)
			for (Timesx timesX : dayX.getTimes()) {
				Tree buildTree = buildTree(timesX);
				buildTree(dayT, buildTree);
			}
		return dayT;
	}
	private Tree buildTree(Timesx timesX) {
		Tree timesT = buildTree(timesX, "times");
		timesT.setMtlO(new Times());
		timesT.getTimesO().setAbs(timesX.getAbs());
		timesT.getTimesO().setApporder(timesX.getApporder());
		timesT.getTimesO().setRelunit(timesX.getRelunit());
		timesT.getTimesO().setRelvalue(timesX.getRelvalue());
		return timesT;
	}
	private Tree buildTree(Exprx exprX) {
		Tree exprT = buildTree(exprX, "expr");
		exprT.setMtlO(new Expr());
		exprT.getExprO().setExpr(exprX.getExpr());
		exprT.getExprO().setType(exprX.getType());
		exprT.getExprO().setUnit(exprX.getUnit());
		exprT.getExprO().setValue(exprX.getValue());
		if(exprX.getEexpr().size()>0)
			for (Exprx expr2X : exprX.getEexpr()) {
				Tree buildTree = buildTree(expr2X);
				buildTree(exprT, buildTree);
			}
		return exprT;
	}
	public Tree buildTree(Dosex doseX) {
		Tree doseT = buildTree(doseX, "dose");
    	doseT.setMtlO(new Dose());
    	doseT.getDoseO().setValue(doseX.getValue());
    	doseT.getDoseO().setUnit(doseX.getUnit());
    	doseT.getDoseO().setApp(doseX.getApp());
    	if(doseX.getExpr().size()>0)
			for (Exprx exprX : doseX.getExpr()) {
				Tree buildTree = buildTree(exprX);
				buildTree(doseT, buildTree);
			}
    	return doseT;
	}
	private Tree buildTree(Treex  treeX, String tabName) {
		Tree tree = new Tree();
    	tree.setId(treeX.getId());
		tree.setTabName(tabName);
		return tree;
	}
//	public TaskRegimex loadTaskx(Integer pasteId) {
	public Treex loadTaskx(Integer pasteId, Class classType) {
		Treex taskX = null;
//    	TaskRegimex taskX = null;
    	JAXBContext newInstance;
    	URL url;
    	try {
			newInstance = JAXBContext.newInstance(classType);
//    		Class<Conceptx> class1 = Conceptx.class;
//			newInstance = JAXBContext.newInstance(class1);
    		Unmarshaller createUnmarshaller = newInstance.createUnmarshaller();
    		String urlStr = "http://localhost:8080/tc17-web/xml=x_" + pasteId;
			url = new URL(urlStr);
			log.debug(url);
    		taskX = (Treex) createUnmarshaller.unmarshal(url);
    		log.debug(taskX);
//    		taskX = (TaskRegimex) createUnmarshaller.unmarshal(url);
    		log.debug(taskX);
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (JAXBException e) {
    		e.printStackTrace();
    	}
    	return taskX;
    }
}
