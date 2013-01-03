package org.tc17.jaxb.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.tasclin1.mopet.domain.Notice;
import org.tasclin1.mopet.domain.Tree;

@XmlRootElement(name = "notice")
public class Noticex extends Treex {
	    private String notice, type;
	    @XmlAttribute 
		public String getNotice() { return notice; } 
	    @XmlAttribute
		public String getType() { return type; } 
	    
		public void setType(String type) { this.type = type; } 
		public void setNotice(String notice) { this.notice = notice; }
		public Noticex() { } 
	    public Noticex(Tree noticeT) {
	    	super(noticeT);
	    	Notice noticeO = noticeT.getNoticeO();
	    	if (null != noticeO) {
	    		notice = noticeO.getNotice();
	    		type = noticeO.getType();
	    	}
	    }
}
