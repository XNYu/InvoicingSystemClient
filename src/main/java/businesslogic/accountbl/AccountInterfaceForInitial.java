package businesslogic.accountbl;

import java.util.ArrayList;

import VO.AccountVO;
import businesslogic.utilitybl.ResultMessage;

public interface AccountInterfaceForInitial {
	public ResultMessage addAccount(String name);
	public ArrayList<AccountVO> showAccount();//panel上的显示
}
