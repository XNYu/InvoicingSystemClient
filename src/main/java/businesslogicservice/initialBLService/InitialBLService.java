package businesslogicservice.initialBLService;

import VO.InitialVO;
import businesslogic.utilitybl.ResultMessage;

public interface InitialBLService {
	public InitialVO search(String year);
	public ResultMessage createInitial(InitialVO vo);
	public void endInitial();
	public void delAll();
}
