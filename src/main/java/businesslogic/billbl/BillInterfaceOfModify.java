package businesslogic.billbl;

import VO.BillVO;
import businesslogic.utilitybl.ResultMessage;

public interface BillInterfaceOfModify {
	public ResultMessage modifyBill(BillVO bill);//提供给总经理修改的接口
}
