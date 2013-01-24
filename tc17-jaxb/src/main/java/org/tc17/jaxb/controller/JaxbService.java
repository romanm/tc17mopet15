package org.tc17.jaxb.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.tasclin1.mopet.domain.Concept;
import org.tasclin1.mopet.domain.Day;
import org.tasclin1.mopet.domain.Dose;
import org.tasclin1.mopet.domain.Drug;
import org.tasclin1.mopet.domain.Expr;
import org.tasclin1.mopet.domain.Folder;
import org.tasclin1.mopet.domain.Task;
import org.tasclin1.mopet.domain.Times;
import org.tasclin1.mopet.domain.Tree;
import org.tc17.jaxb.core.Conceptx;
import org.tc17.jaxb.core.Dayx;
import org.tc17.jaxb.core.Definitionx;
import org.tc17.jaxb.core.Dosex;
import org.tc17.jaxb.core.Drugx;
import org.tc17.jaxb.core.Exprx;
import org.tc17.jaxb.core.Folderx;
import org.tc17.jaxb.core.TaskRegimex;
import org.tc17.jaxb.core.Taskx;
import org.tc17.jaxb.core.Timesx;
import org.tc17.jaxb.core.Treex;

@Service("jaxbService")
public class JaxbService {
	protected final Log log = LogFactory.getLog(getClass());
	public Tree buildTree(Folderx folderx, Model model) {
		Tree folderT = buildTree(folderx);
		model.addAttribute("folderT", folderT);
		if(folderx.getSubfolder().size()>0)
			for (Folderx subFolderx : folderx.getSubfolder()) {
				Tree buildTree = buildTree(subFolderx);
				buildTree(folderT, buildTree);
			}
		Map<Integer, List<Tree>> conceptRegime = new HashMap<Integer, List<Tree>>();
		model.addAttribute("conceptRegime", conceptRegime);
		if(folderx.getConcept().size()>0)
			for (Conceptx conceptX : folderx.getConcept()) {
				Tree conceptT = buildTreeInFolder(conceptX,conceptRegime);
				buildTree(folderT, conceptT);
			}
		Tree parentT = folderT;
		if(folderx.getParentfolder().size()>0)
		for (Folderx parentFolderX : folderx.getParentfolder()) {
			Tree buildTree = buildTree(parentFolderX);
			parentT.setParentT(buildTree);
			parentT = buildTree;
		}
		return folderT;
	}
	private Tree buildTreeInFolder(Conceptx conceptX, Map<Integer, List<Tree>> conceptRegime ) {
		Tree conceptT = buildTreeEl(conceptX);
		if(!conceptRegime.containsKey(conceptT.getId()))
			conceptRegime.put(conceptT.getId(), new ArrayList<Tree>());
		if(conceptX.getConceptRegime().size()>0)
			for (Taskx taskX : conceptX.getConceptRegime()) {
				Tree buildTaskEl = buildTaskEl(taskX);
				conceptRegime.get(conceptX.getId()).add(buildTaskEl);
			}
		return conceptT;
	}
	private Tree buildTree(Folderx folderx) {
		Tree folderT = buildTree(folderx, "folder");
		folderT.setMtlO(new Folder());
		folderT.getFolderO().setFolder(folderx.getFolder());
		return folderT;
	}
	public Tree buildTree(Conceptx conceptX) {
		Tree conceptT = buildTreeEl(conceptX);
		Definitionx definitionX = conceptX.getDefinition();
		Tree definitionT = buildTree(definitionX, "definition");
		buildTree(conceptT, definitionT);
		if(definitionX.getTask().size()>0)
    		for (Taskx task : definitionX.getTask()) {
    			Tree buildTree = buildTaskEl(task);
    			buildTree(definitionT, buildTree);
    		}
		if(conceptX.getTask().size()>0)
    		for (Taskx task : conceptX.getTask()) {
    			Tree buildTree = buildTaskEl(task);
    			buildTree(conceptT, buildTree);
    		}
		return conceptT;
	}
	
	private Tree buildTreeEl(Conceptx conceptX) {
		Tree conceptT = buildTree(conceptX, "concept");
		conceptT.setMtlO(new Concept());
		conceptT.getConceptO().setProtocol(conceptX.getProtocol());
		conceptT.getConceptO().setProtocoltype(conceptX.getProtocoltype());
		return conceptT;
	}
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
    	Tree taskT = buildTaskEl(taskX);
    	if(taskX.getDrug().size()>0)
    		for (Drugx drugX : taskX.getDrug()) {
    			Tree buildTree = buildTree(drugX);
    			buildTree(taskT, buildTree);
    		}
		return taskT;
	}
	private Tree buildTaskEl(Taskx taskX) {
		Tree taskT = buildTree(taskX, "task");
    	taskT.setMtlO(new Task());
    	taskT.getTaskO().setTask(taskX.getTaskName());
    	taskT.getTaskO().setTaskvar(taskX.getTaskvar());
    	taskT.getTaskO().setType(taskX.getType());
    	taskT.getTaskO().setId(taskX.getIdclass());
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
	public Treex loadTaskx(Integer pasteId, Class<?> classType) {
		Treex taskX = null;
    	JAXBContext newInstance;
    	URL url;
    	try {
			newInstance = JAXBContext.newInstance(classType);
    		Unmarshaller createUnmarshaller = newInstance.createUnmarshaller();
//    		String centralServerUrl = "http://localhost:8080/tc17-web/xml=x_";
			String urlStr = centralServerUrl + pasteId;
			url = new URL(urlStr);
			log.debug(url);
    		taskX = (Treex) createUnmarshaller.unmarshal(url);
    		log.debug(taskX);
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (JAXBException e) {
    		e.printStackTrace();
    	}
    	return taskX;
    }
	@Autowired
    private String centralServerUrl;
}
