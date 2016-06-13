package PO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import businesslogic.utilitybl.DateHelper;

public class DocumentPO implements Serializable {
	private static final long serialVersionUID = 1L;
	String docType = "";//OVERFLOW,DAMAGE,REPORT
	String name="";
	String type = "";
	String id="";
	int reportAmount = 0;
	String documentID;
	int systemAmount = 0;
	int realAmount = 0;
	String createdDate="";
	boolean isExamined = false;
	public DocumentPO(String docType,String name,String type,String id,int reportAmount,
			int systemAmount,int realAmount){
		this.docType = docType;
		this.name = name;
		this.type = type;
		this.id = id;
		this.reportAmount=reportAmount;
		this.realAmount=realAmount;
		this.systemAmount = systemAmount;
		DateHelper dh = new DateHelper();
		createdDate = dh.getDate();
		documentID = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
	}
	public boolean isExamined() {
		return isExamined;
	}
	public void setExamined(boolean isExamined) {
		this.isExamined = isExamined;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReportAmount() {
		return reportAmount;
	}
	public void setReportAmount(int reportAmount) {
		this.reportAmount = reportAmount;
	}
	public int getSystemAmount() {
		return systemAmount;
	}
	public void setSystemAmount(int systemAmount) {
		this.systemAmount = systemAmount;
	}
	public int getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(int realAmount) {
		this.realAmount = realAmount;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDocumentID() {
		return documentID;
	}
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	
}

