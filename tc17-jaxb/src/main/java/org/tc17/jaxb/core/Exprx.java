package org.tc17.jaxb.core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Expr;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "expr")
public class Exprx  extends Treex {

	private String expr, type, unit, value;
	@XmlAttribute
	public String getValue() { return value; } 
	public void setValue(String value) { this.value = value; } 
	@XmlAttribute
	public String getUnit() { return unit; } 
	public void setUnit(String unit) { this.unit = unit; } 
	@XmlAttribute
	public String getType() { return type; } 
	public void setType(String type) { this.type = type; } 
	@XmlAttribute
	public String getExpr() { return expr; }
	public void setExpr(String expr) { this.expr = expr; }
    ArrayList<Exprx> eexpr; 
	public ArrayList<Exprx> getEexpr() {
		if (null == eexpr) eexpr = new ArrayList<Exprx>();
		return eexpr;
	} 
	public void setEexpr(ArrayList<Exprx> eexpr) { this.eexpr = eexpr; } 
	public Exprx() {}
	public Exprx(Tree exprT) {
		super(exprT);
		Expr exprO = exprT.getExprO();
		if (null != exprO) {
			expr = exprO.getExpr();
			type = exprO.getType();
			unit = exprO.getUnit();
			value = exprO.getValue();
		}
	}

}
