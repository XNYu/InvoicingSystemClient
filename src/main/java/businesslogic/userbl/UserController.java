package businesslogic.userbl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import PO.UserPO;
import VO.UserVO;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.userBLService.UserBLService;


public class UserController implements UserBLService{
	User user=new User();
	
	public void setUser(UserVO u){
		user.setUser(u);;
	}
	UserVO transform(UserPO po){
		UserVO vo=new UserVO(po.getID(),po.getType(),po.getName(),po.getPassword(),po.getRank());
		return vo;
	}
	public UserVO login(String ID,String password){
	 	return user.login(ID, password);
	}
	
	public ResultMessage register(UserVO vo){
		
		return user.register(vo);
	}

	public ResultMessage addUser(UserVO vo){
		return user.addUser(vo);
	}

	public ResultMessage deleteUser(String ID){
		return user.delUser(ID);
	}

	public ResultMessage modifyUser(UserVO vo){
		return user.modifyUser(vo);
	}
	
	public UserVO searchUser(String ID){
		return user.searchUser(ID);
	}

	public void endUserManage(){
		user.endUserManage();
	}
	public ArrayList<UserVO> showUser() {
		ArrayList<UserVO> voList=user.showUser();
		return voList;
	}

}
