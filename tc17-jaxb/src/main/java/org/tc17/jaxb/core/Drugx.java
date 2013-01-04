package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "drug")
public class Drugx extends TaskOnex {
    Dosex dose;
    private Appx app;
    String drug, generic;
    ArrayList<Drugx> ddrug; 
    ArrayList<Noticex> notice;
    
    @XmlAttribute
    public String getDrug() { return drug; } 
    @XmlAttribute
    public String getGeneric() { return generic; } 
    
    public Appx getApp() { return app; } 
    public Dosex getDose() { return dose; } 
    public void setApp(Appx app) { this.app = app; } 
    public void setDose(Dosex dose) { this.dose = dose; } 
    public void setGeneric(String generic) { this.generic = generic; } 
    public void setDrug(String drug) { this.drug = drug; }
    public ArrayList<Drugx> getDdrug() {
    	if (null == ddrug) ddrug = new ArrayList<Drugx>();
		return ddrug;
	}

	public void setDdrug(ArrayList<Drugx> ddrug) { this.ddrug = ddrug; } 
    ArrayList<Exprx> expr; 
	public void setExpr(ArrayList<Exprx> expr) { this.expr = expr; } 
	public ArrayList<Exprx> getExpr() {
		if (null == expr) expr = new ArrayList<Exprx>();
		return expr;
	}
    /*
     * public Drugx(int id, int idclass, String drug) { this.id = id; this.idclass = idclass; this.drug = drug; }
     */
    public Drugx(){} 
    public Drugx(Tree tree) {
	super(tree);
	drug = tree.getDrugO().getDrug();
	generic = tree.getDrugO().getGeneric().getDrug();
    }
    public ArrayList<Noticex> getNotice() {
		if (null == notice) notice = new ArrayList<Noticex>();
		return notice;
	} 
	public void setNotice(ArrayList<Noticex> notice) { this.notice = notice; }
}
