package businesslogic.initialbl;

import VO.InitialVO;
import VO.UserVO;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.initialBLService.InitialBLService;

public class InitialController implements InitialBLService {
	private Initial ini = null;
	private UserVO user = null;

	public InitialController(UserVO user1){
		this.user = user1;
		ini = new Initial(user);
	}

	public InitialVO search(String year) {
		// TODO 自动生成的方法存根
		return ini.search(year);
	}

	public ResultMessage createInitial(InitialVO vo) {
		// TODO 自动生成的方法存根
		return ini.createInitial(vo);
	}

	public void endInitial() {
		// TODO 自动生成的方法存根

	}

	public void delAll() {
		// TODO 自动生成的方法存根
		ini.delAll();
	}

}
