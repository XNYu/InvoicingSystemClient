package businesslogic.document;

import java.io.Serializable;
import java.util.ArrayList;

import PO.*;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.documentBLService.DocumentBLService;

public class DocumentController implements DocumentBLService,Serializable{

	Document doc = new Document();
	@Override
	public ResultMessage createOverflow(String name, String type, String id,
			int oldAmount, int newAmount) {
		return doc.createOverflow(name,type,id,oldAmount,newAmount);
	}
	@Override
	public ResultMessage createDamage(String name, String type, String id,
			int oldAmount, int newAmount) {
		return doc.createDamage(name, type, id, oldAmount, newAmount);
	}
	@Override
	public ResultMessage createReport(String name, String type, String id,
			int reportAmount) {
		// TODO 自动生成的方法存根
		return doc.createReport(name, type, id, reportAmount);
	}
	@Override
	public ArrayList<DocumentPO> showDocument(String type) {
		// TODO 自动生成的方法存根
		return doc.showDocument(type);
	}
	@Override
	public ArrayList<DocumentPO> showAllDocument() {
		// TODO 自动生成的方法存根
		return doc.show();
	}
	@Override
	public ArrayList<PresentPO> showPresent() {
		// TODO Auto-generated method stub
		return doc.showPresent();
	}
	@Override
	public boolean examineDocument(String id,boolean examineState) {
		// TODO Auto-generated method stub
		return doc.examineDocument(id,examineState);
	}
	public void clear(){
		doc.clear();
	}

}
