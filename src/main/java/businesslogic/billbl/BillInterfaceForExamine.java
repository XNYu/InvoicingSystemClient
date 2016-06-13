package businesslogic.billbl;

import java.util.ArrayList;

import VO.BillVO;
import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import businesslogic.utilitybl.ResultMessage;

public interface BillInterfaceForExamine {
	public ArrayList<CostBillVO> getXJFYDListForExamined();
	public ArrayList<PaymentBillVO> getFKDListForExamined();
	public ArrayList<ReceivingBillVO> getSKDListForExamined();
	public ResultMessage examine(BillVO vo,boolean isPassed);
}
