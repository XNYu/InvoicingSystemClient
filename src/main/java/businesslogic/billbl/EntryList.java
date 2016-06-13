package businesslogic.billbl;

import java.util.ArrayList;
import java.util.Iterator;

import PO.EntryPO;
import VO.EntryVO;

public class EntryList {
	//现金费用单条目单
	ArrayList<EntryVO> elist = new ArrayList<EntryVO>();


	//po vo转换
	public EntryPO transform(EntryVO vo){
		return new EntryPO(vo.getEntry(),vo.getMoney(),vo.getNotes());
	}

	public EntryVO transform(EntryPO po){
		return new EntryVO(po.getEntry(),po.getMoney(),po.getNotes());
	}

	//返回两种对象的list
	public ArrayList<EntryVO> getlist() {
		// TODO 自动生成的方法存根
		return elist;
	}

	public ArrayList<EntryPO> getRtlist() {
		ArrayList<EntryPO> list = new ArrayList<EntryPO>();
		for(EntryVO vo:elist){
			list.add(transform(vo));
		}
		return list;
	}

	public void addEntry(EntryVO et){
		elist.add(et);
	}

	public void removeEntry(int n){
		elist.remove(n);
	}

	//计算条目单总价
	public double total(){
		double total = 0.0;
		Iterator<EntryVO> iter = elist.iterator();
		while (iter.hasNext()){
			Object o = iter.next();
			total += ((EntryVO)o).money;
		}

		return total;

	}


}
