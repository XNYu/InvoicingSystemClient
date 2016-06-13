package businesslogic.accountbl;

import java.util.ArrayList;

import PO.AccountPO;
import VO.AccountVO;

public interface AccountInterfaceForBill {
	public AccountPO getAccountPO(String name);
	public ArrayList<AccountVO> showAccount();//下拉列表中显示
}
