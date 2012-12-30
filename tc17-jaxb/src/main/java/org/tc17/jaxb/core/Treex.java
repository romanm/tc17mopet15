package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;

import org.tasclin1.mopet.domain.Tree;

public class Treex {
    Integer did, id, idclass, ref;

    @XmlAttribute
    public Integer getRef() {
	return ref;
    }

    public void setRef(Integer ref) {
	this.ref = ref;
    }

    public void setIdclass(Integer idclass) {
	this.idclass = idclass;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    private Long sort;

    @XmlAttribute
    public Long getSort() {
	return sort;
    }

    /*
     * private Tree tree;
     * 
     * public Tree getTree() { return tree; }
     * 
     * public void setTree(Tree tree) { this.tree = tree; }
     */

    public Treex(Tree tree) {
	// this.tree = tree;
	id = tree.getId();
	idclass = tree.getIdClass();
	did = tree.getParentT().getId();
	sort = tree.getSort();
	if (null != tree.getRef())
	    ref = tree.getRef();
    }

    public Treex() {
    }

    @XmlAttribute
    public Integer getDid() {
	return did;
    }

    public void setDid(Integer id) {
	did = id;
    }

    @XmlAttribute
    public Integer getIdclass() {
	return idclass;
    }

    @XmlAttribute
    public Integer getId() {
	return id;
    }

}
