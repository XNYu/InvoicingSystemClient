package businesslogic.logbl;

import java.util.ArrayList;

import VO.LogVO;
import businesslogicservice.logBLService.LogBLService;

public class LogController implements LogBLService {
	Log log = new Log();

	@Override
	public ArrayList<LogVO> searchlogs(String time1,String time2) {
		// TODO 自动生成的方法存根
		return log.searchlogs(time1, time2);
	}

	@Override
	public void endLog() {
		// TODO 自动生成的方法存根
		log.endLog();
	}

	@Override
	public void delAll() {
		// TODO 自动生成的方法存根
		log.delAll();
	}

}
