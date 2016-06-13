package businesslogicservice.documentBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import PO.DocumentPO;
import PO.PresentPO;
import VO.*;

public interface DocumentBLService {
	public ResultMessage createOverflow(String name,String type,String id,int oldAmount,
			int newAmount);
	public ResultMessage createDamage(String name,String type,String id,int oldAmount,
			int newAmount);
	public ResultMessage createReport(String name,String type,String id,int reportAmount);
	public ArrayList<DocumentPO> showDocument(String type);
	public ArrayList<DocumentPO> showAllDocument();
	public ArrayList<PresentPO> showPresent();
	public boolean examineDocument(String id,boolean examineState);
	public void clear();
}
