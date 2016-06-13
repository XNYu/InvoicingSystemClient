package businesslogicservice.userBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import VO.*;

public interface UserBLService {
	public UserVO login(String id, String password);
	public ResultMessage addUser(UserVO vo);
	public ResultMessage deleteUser(String ID);
	public ResultMessage modifyUser(UserVO vo);
	public UserVO searchUser(String ID);
	public ArrayList<UserVO> showUser();
	public ResultMessage register(UserVO vo);
	public void endUserManage();
	public void setUser(UserVO u);
}
