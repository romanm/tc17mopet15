package org.tasclin1.mopet.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The persistent class for the tree database table.
 */
@Entity
// @SequenceGenerator(name="DBID",sequenceName="dbid")
public class Tree implements Serializable {

    @Transient
    Integer durationValueSecond;

    public Integer getAppDurationSecond() {
    	if (null == durationValueSecond) {
    		durationValueSecond = 0;
    		Tree drugAppT = getParentT().getParentT().getDrugAppT();
    		if (null != drugAppT) {
    			App appO = drugAppT.getAppO();
    			durationValueSecond = appO.getDurationValue();
    			if ("min".equals(appO.getUnit()))
    				durationValueSecond *= 60;
    			if ("h".equals(appO.getUnit()))
    				durationValueSecond *= 60 * 60;
    			if ("d".equals(appO.getUnit()))
    				durationValueSecond *= 60 * 60 * 24;
    		}
    	}
    	return durationValueSecond;
    }

    /**
     * Get child tree with value of this object as integer.
     * @return Tree object with parent object value as integer
     */
    public Tree getIvalueT() {
	if (isFinding() || isLabor())
	    for (Tree iValueT : this.getChildTs())
		if (iValueT.isIvalue())
		    return iValueT;
	return null;
    }

    /**
     * Get child tree with value of this object as string.
     * @return Tree object with parent object value as string
     */
    public Tree getPvalueT() {
	if (isFinding() || isLabor())
	    for (Tree pValueT : this.getChildTs())
		if (pValueT.isPvalue())
		    return pValueT;
	return null;
    }

    /**
     * Get child tree with value of this object as string.
     * @return Tree object with parent object value as string
     */
    public Tree getOfDateT() {
	if (getParentT().isPatient())
	    for (Tree pValueT : this.getChildTs())
		if (pValueT.isOfDate())
		    return pValueT;
	return null;
    }

    public boolean isTaskOne() {
    	return isDrug() || isLabor() || isFinding() || isNotice();
    }

    public Tree getDrugDayT(int n) {
	int i = 0;
	// if (isDrug())
	if (isTaskOne())
	    for (Tree dayT : this.getChildTs())
		if (dayT.isDay())
		    if (n == i++)
			return dayT;
	return null;
    }

    public Tree getDrugTimesT(String n) {
    	if(null==n)
    		return null;
    	log.debug(n);
    	int nn = Integer.parseInt(n);
    	log.debug(nn);
    	return getDrugTimesT(nn);
    }
    public Tree getDrugTimesT(int n) {
    	int i = 0;
    	if (isTaskOne())
    		for (Tree dayT : this.getChildTs())
    			if (dayT.isDay())
    				for (Tree timesT : dayT.getChildTs())
    					if (timesT.isTimes())
    						if (n == i++)
    							return timesT;
    	return null;
    }
    public Tree getDrugDayTimesT(int n) {
	int i = 0;
	if (isDay())
	    for (Tree timesT : this.getChildTs())
		if (timesT.isTimes())
		    if (n == i++)
			return timesT;
	return null;
    }

    public Tree getDrugDoseAppT() {
	if (isDose())
	    for (Tree doseAppT : this.getChildTs())
		if (doseAppT.isApp())
		    return doseAppT;
	return null;
    }

    public Tree getDrugAppT() {
	if (isDrug())
	    for (Tree appT : this.getChildTs())
		if (appT.isApp())
		    return appT;
	return null;
    }

    public Tree getDrugDoseT() {
	if (isDrug())
	    for (Tree doseT : this.getChildTs())
		if (doseT.isDose())
		    return doseT;
	return null;
    }

    public boolean isIvalue() {
	return isIvariable() && ((Ivariable) getMtlO()).getIvariable().equals(getParentT().getTabName());
    }

    public boolean isOfDate() {
	return isPvariable() && "ofDate".equals(((Pvariable) getMtlO()).getPvariable());
    }

    public boolean isPvalue() {
	return isPvariable() && ((Pvariable) getMtlO()).getPvariable().equals(getParentT().getTabName());
    }

    public boolean isIvariable() {
	return getMtlO() instanceof Ivariable;
    }

    public boolean isPvariable() {
	return getMtlO() instanceof Pvariable;
    }

    public boolean isLabor() {
	return getMtlO() instanceof Labor || "labor".equals(getTabName());
    }

    public boolean isFolder() {
	return getMtlO() instanceof Folder || "folder".equals(getTabName());
    }

    public boolean isDiagnose() {
	return getMtlO() instanceof Diagnose || "diagnose".equals(getTabName());
    }

    public boolean isPatient() {
	return getMtlO() instanceof Patient;
    }

    public boolean isFinding() {
	return getMtlO() instanceof Finding || "finding".equals(getTabName());
    }

    public boolean isConcept() {
	return getMtlO() instanceof Concept || "protocol".equals(getTabName());
    }

    public boolean isTask() {
	return getMtlO() instanceof Task || "task".equals(getTabName());
    }

    public boolean isDrug() {
	return getMtlO() instanceof Drug || "drug".equals(getTabName());
    }

    public boolean isDose() {
	return isMtlDoseO() || "dose".equals(getTabName());
    }
    public boolean isMtlDoseO() {
    	return getMtlO() instanceof Dose;
        }
    public boolean isExpr() {
	return getMtlO() instanceof Expr || "expr".equals(getTabName());
    }

    public boolean isNotice() {
	return getMtlO() instanceof Notice || "notice".equals(getTabName());
    }

    public boolean isArm() {
	return getMtlO() instanceof Arm || "arm".equals(getTabName());
    }

    public boolean isApp() {
	return getMtlO() instanceof App || "app".equals(getTabName());
    }
    
    public boolean isTimes() {
	return isMtlTimesO() || "times".equals(getTabName());
    }

    public boolean isMtlTimesO() {
	return getMtlO() instanceof Times;
    }

    public boolean isDay() {
	return isMtlDayO() || "day".equals(getTabName());
    }

    public boolean isMtlDayO() {
	return getMtlO() instanceof Day;
    }

    @Transient
    private boolean importChild;

    public boolean isImportChild() {
	return importChild;
    }

    public void setImportChild(boolean importChild) {
	this.importChild = importChild;
    }

    @Transient
    private MObject mtlO;

    /**
     * Get entity object with medical date (MeTaL object) for this mtl-node.
     * @return MeTaL object for this node.
     */
    public MObject getMtlO() {
	return mtlO;
    }

    public void setMtlO(MObject mtlO) {
	this.mtlO = mtlO;
	if (null == mtlO)
	    setIdClass(null);
	else
	    setIdClass(mtlO.getId());
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // @SequenceGenerator(name="TREE_ID_GENERATOR", sequenceName="dbid")
    // @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TREE_ID_GENERATOR")
    // @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DBID")
    @Id
    private Integer id;

    @Column(name = "idclass")
    private Integer idClass;

    public Integer getIdClass() {
	return idClass;
    }

    public void setIdClass(Integer idclass) {
	this.idClass = idclass;
    }

    @Column(name = "sort")
    private Long sort;

    @Column(name = "tab_name")
    private String tabName;

    private String ttype;

    // bi-directional many-to-one association to Tree
    @ManyToOne(cascade = CascadeType.ALL)
    // @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "did")
    private Tree parentT;

    public Tree getParentT() {
	return this.parentT;
    }

    public void setParentT(Tree tree1) {
	this.parentT = tree1;
	if (!parentT.hasChild())
	    parentT.setChildTs(new ArrayList<Tree>());
	if (!parentT.getChildTs().contains(this))
	    parentT.getChildTs().add(this);
    }

    // @OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "id")
    private History history;

    public History getHistory() {
	return history;
    }

    public void setHistory(History history) {
	this.history = history;
    }

    // bi-directional many-to-one association to Tree
    @OneToMany(mappedBy = "parentT", cascade = CascadeType.ALL)
    @OrderBy("sort")
    private List<Tree> childTs;

    public List<Tree> getChildTs() {
	return this.childTs;
    }

    public void setChildTs(List<Tree> trees1) {
	this.childTs = trees1;
    }

    public boolean hasChild() {
	return getChildTs() != null && getChildTs().size() > 0;
    }

    // bi-directional many-to-one association to Tree
    @ManyToOne
    @JoinColumn(name = "iddoc")
    private Tree docT;

    public Tree getDocT() {
	return this.docT;
    }

    public void setDocT(Tree docT) {
	this.docT = docT;
    }

    // bi-directional many-to-one association to Tree
    @OneToMany(mappedBy = "docT")
    private List<Tree> docTs;

    public List<Tree> getDocNodes() {
    	return this.docTs;
    }

    public void setDocNodes(List<Tree> trees) {
	this.docTs = trees;
    }

    /*
     * //bi-directional many-to-one association to Tree
     * 
     * @ManyToOne
     * 
     * @JoinColumn(name="ref") private Tree refT; //bi-directional many-to-one association to Tree
     * 
     * @OneToMany(mappedBy="refT") // @LazyCollection(LazyCollectionOption.FALSE) private Set<Tree> refTs;
     * 
     * public Tree getRefT() { return this.refT; } public void setRefT(Tree tree4) { this.refT = tree4; }
     * 
     * public Set<Tree> getRefTs() { return this.refTs; } public void setRefTs(Set<Tree> trees4) { this.refTs = trees4;
     * }
     */

    @Transient
    private Tree refT;

    public Tree getRefT() {
	return refT;
    }

    public void setRefT(Tree refT) {
	this.refT = refT;
    }

    private Integer ref;

    public Integer getRef() {
	return ref;
    }

    public void setRef(Integer ref) {
	this.ref = ref;
    }

    public boolean hasRef() {
	return ref != null && ref > 0;
    }

    public Tree() {
    }

    public Integer getId() {
	return this.id;
    }

    public void setId(Integer id) {
	this.id = id;
	if (getHistory() != null)
	    getHistory().setTree(this);
    }

    public Long getSort() {
	return this.sort;
    }

    public void setSort(int sort) {
	setSort((long) sort);
    }

    public void setSort(Long sort) {
	this.sort = sort;
    }

    public String getTabName() {
	return this.tabName;
    }

    public void setTabName(String tabName) {
	this.tabName = tabName;
    }

    public String getTtype() {
	return this.ttype;
    }

    public void setTtype(String ttype) {
	this.ttype = ttype;
    }

    public String toString() {
	return "tree:id:" + id + " :tabName:" + tabName + " :idclass:" + idClass + " :did:"
		+ (parentT == null ? 0 : parentT.getId()) + " :ref:" + ref + " :sort:" + sort + " :iddoc:"
		+ (docT == null ? 0 : docT.getId()) + "\n mtlO=" + getMtlO()
		+ (null == getHistory() ? "" : "\n " + getHistory())
	// +":hashCode:"+this.hashCode()
	;
    }

    // public void copyDoc(CopyDocMtl copyDocM, MtlDbService dbService) {
    // copy(copyDocM.getDocT(),copyDocM,this,dbService);
    // }
    // private void copy(Tree originalT, CopyDocMtl copyDocM, Tree newDocT, MtlDbService dbService) {
    // if(copyDocM.getNotCopyS().contains(originalT))return;
    // setId(dbService.nextDbid());
    // setDocT(newDocT);
    // // newDocT.getDocNodes().add(this);
    // copyAtt(originalT);
    // copyDocM.getTree().put(originalT.getId(), originalT);
    // copyDocM.getNewTree().put(originalT.getId(), this);
    // System.out.println(originalT+"\n to "+this);
    // copyDocM.addTreePaar(this);
    // dbService.getEntityManager().persist(this);
    //
    // if(originalT.getRef()!=null)
    // copyDocM.addRef(originalT);
    // if(originalT.getChildTs()!=null){
    // childTs=new ArrayList<Tree>();
    // for (Tree ocT : originalT.getChildTs()) {
    // Tree newT = new Tree();
    // newT.setParentT(this);
    // childTs.add(newT);
    // newT.copy(ocT, copyDocM, newDocT, dbService);
    // }
    // }
    // }
    public void copyAtt(Tree originalT) {
	setTabName(originalT.getTabName());
	setIdClass(originalT.getIdClass());
	setSort(originalT.getSort());
    }

    // @Transient private Set<TsTask> tses;
    transient private Set<TsTask> tses;

    public Set<TsTask> getTses() {
	return tses;
    }

    public boolean hasTses() {
	return tses != null;
    }

    public void setTses() {
	tses = new ConcurrentSkipListSet<TsTask>();
    }

    public void addTs(TsTask ts) {
	if (!hasTses())
	    setTses();
	getTses().add(ts);
    }

    public void addTs(TsTask ts, Map<Long, TsTask> plan2TsS) {
	while (plan2TsS.containsKey(ts.getTime()))
	    ts.setTime(ts.getTime() + 1);
	// log.debug("timesId="+ts.getTimesT().getId()+" "+ts.getTimesT().getParentT().getParentT().getMtlO()+"\n"+ts);
	plan2TsS.put(ts.getTime(), ts);
	addTs(ts);
    }

    @Transient
    protected final Log log = LogFactory.getLog(getClass());

    // private Dose doseO;
    // public void putDose(MObject mtlO) {doseO=(Dose) mtlO;}
    //
    // private Drug drugO;
    // public void putDrug(MObject mtlO) {drugO=(Drug) mtlO;}
    /*
     * protected int childNr(Tree tree) { int childNr = tree.getParentT().getChildTs().indexOf(tree); return childNr; }
     */
    public int childNr() {
	int childNr = getParentT().getChildTs().indexOf(this);
	return childNr;
    }

    public Finding getFinding() {
	return (Finding) getMtlO();
    }

    public Drug getDrugO() {
	return (Drug) getMtlO();
    }

    public Notice getNoticeO() {
	return (Notice) getMtlO();
    }

    public Folder getFolderO() {
	return (Folder) getMtlO();
    }

    public Finding getFindingO() {
	return (Finding) getMtlO();
    }

    public Labor getLaborO() {
	return (Labor) getMtlO();
    }

    public App getAppO() {
	return (App) getMtlO();
    }
    public Expr getExprO() {
    	return (Expr) getMtlO();
    }
    public Dose getDoseO() {
	return (Dose) getMtlO();
    }
    public Concept getConceptO() {
    	return (Concept) getMtlO();
    }
    public Day getDayO() {
	return (Day) getMtlO();
    }

    public Times getTimesO() {
	return (Times) getMtlO();
    }

    public Task getTaskO() {
	return (Task) getMtlO();
    }

    public Patient getParentO() {
	return (Patient) getMtlO();
    }

    public Pvariable getPvalueO() {
	return (Pvariable) getMtlO();
    }

    @Transient
    private Set<TaskRun> taskRuns;

    public Set<TaskRun> getTaskRuns() {
	if (null == taskRuns) {
	    taskRuns = new ConcurrentSkipListSet<TaskRun>();
	}
	return taskRuns;
    }

    public char getCharNr(int i) {
	return (char) (87 + i);
    }

}
