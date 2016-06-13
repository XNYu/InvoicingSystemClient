package businesslogic.document;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.DocumentPO;
import PO.PresentPO;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import dataservice.documentDataService.DocumentDataService;

public class Document implements Serializable {
	private static final long serialVersionUID = 1L;
	DocumentDataService dds ;
	public Document() {
		try {
			dds=Datafactory.getDocumentData();
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//clear the data for JUnit test
	public void clear(){
		try {
			dds.clear();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//show present documents
	public ArrayList<PresentPO> showPresent(){
		try {
			return dds.showPresent();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	//show all kinds of documents
	public ArrayList<DocumentPO> show(){
		try {
			return dds.showAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	//show a specific type's documents
	public ArrayList<DocumentPO> showDocument(String type){
		try {
			return dds.showDoc(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	//create a damage document
	public ResultMessage createDamage(String name,String type,String id,int oldAmount ,int newAmount){
		boolean flag = false;
		try {
			flag =  dds.createDamage(name, type, id, oldAmount, newAmount);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//create a report document
	public ResultMessage createReport(String name,String type,String id,int reportAmount){
		boolean flag = false;
		try {
			flag =  dds.createReport(name, type, id, reportAmount);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//create a overflow document
	public ResultMessage createOverflow(String name,String type,String id,int oldAmount ,int newAmount){
		boolean flag = false;
		try {
			flag =  dds.createOverflow(name, type, id, oldAmount, newAmount);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//examine a doucment
	public boolean examineDocument(String id,boolean examinState){
		try {
			return dds.examineDocument(id,examinState);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	//income result from overflow document
	public double overflowIncome(){
		try {
			return dds.overflowIncome();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//outcome result from damage document
	public double damageOutcome(){
		try {
			return dds.damageOutcome();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

