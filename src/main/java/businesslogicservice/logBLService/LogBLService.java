package businesslogicservice.logBLService;

import java.util.ArrayList;

import VO.LogVO;

public interface LogBLService {
	public ArrayList<LogVO> searchlogs(String time1,String time2);
	public void delAll();
	public void endLog();
}
