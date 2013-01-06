package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Folder;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "folder")
public class Folderx extends Treex {
    
    private String folder;
    ArrayList<Folderx> subfolder,parentfolder;
    ArrayList<Conceptx> concept;
  
    public ArrayList<Conceptx> getConcept() {
    	if(null==concept) concept=new ArrayList<Conceptx>();
		return concept;
	}
	public void setConcept(ArrayList<Conceptx> concept) {
		this.concept = concept;
	}
	public void setSubfolder(ArrayList<Folderx> subfolder) {
		this.subfolder = subfolder;
	}
	public void setParentfolder(ArrayList<Folderx> parentfolder) {
		this.parentfolder = parentfolder;
	}
	public ArrayList<Folderx> getParentfolder() {
    	if(null==parentfolder) parentfolder=new ArrayList<Folderx>();
		return parentfolder;
	}
	public ArrayList<Folderx> getSubfolder() {
    	if(null==subfolder) subfolder=new ArrayList<Folderx>();
		return subfolder;
	}
	@XmlAttribute
	public String getFolder() {return folder;}
	public void setFolder(String folder) {this.folder = folder;}
	public Folderx() { } 
    public Folderx(Tree folderT) {
    	super(folderT);
    	Folder folderO = folderT.getFolderO();
    	if (null != folderO) {
    		folder = folderO.getFolder();
    	}
    }

}
