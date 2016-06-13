package businesslogic.billbl;



import java.util.ArrayList;
import java.util.Iterator;

import PO.TransferPO;
import VO.TransferVO;
//付款单转账
public class TransferList {
	ArrayList<TransferVO> rtlist = new ArrayList<TransferVO>();



	//po vo转换
	public TransferPO transform(TransferVO vo){
		return new TransferPO(vo.acc,vo.getMoney(),vo.getNotes());
	}

	public TransferVO transform(TransferPO po){
		return new TransferVO(po.getAcc(),po.getMoney(),po.getNotes());
	}

	//返回两种对象的列表
	public ArrayList<TransferVO> getlist() {
		return rtlist;
	}

	public ArrayList<TransferPO> getRtlist() {
		ArrayList<TransferPO> list = new ArrayList<TransferPO>();
		for(TransferVO vo:rtlist){
			list.add(transform(vo));
		}
		return list;
	}

	public void addTransfer(TransferVO tf){
		rtlist.add(tf);
	}

	public void removeTransfer(int n){
		rtlist.remove(n);
	}

	//计算转账单总价
	public double total(){
		double total = 0.0;
		Iterator<TransferVO> iter = rtlist.iterator();
		while (iter.hasNext()){
			Object o = iter.next();
			total += ((TransferVO)o).getMoney();
		}

		return total;

	}
}
