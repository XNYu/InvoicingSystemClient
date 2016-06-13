package dataservice.examineDataService;
import java.rmi.RemoteException;
import PO.ExaminePO;

public interface ExamineDataService {
	public boolean add(ExaminePO po) throws RemoteException; 
	public boolean modify(ExaminePO po) throws RemoteException;
	public boolean delete(ExaminePO po) throws RemoteException;
	public boolean confirm(ExaminePO po) throws RemoteException;
	public void finish();
}
