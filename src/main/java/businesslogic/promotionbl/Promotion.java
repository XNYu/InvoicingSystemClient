package businesslogic.promotionbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import java.util.Date;

import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.DateHelper;
import businesslogic.utilitybl.ResultMessage;
import PO.CommodityPO;
import PO.PromotionPO;


import VO.PromotionVO.types;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;
import dataservice.promotionDataService.PromotionDataService;
import businesslogic.customerbl.Customer;

public class Promotion implements PromotionInterfaceForSales {
	PromotionDataService pds;
	PromotionPO po=new PromotionPO();
	ArrayList<PromotionVO> vos; 
	Customer cus=new Customer();
	Date dt=new Date();
	LogInterface log=new Log();
	UserVO user=new UserVO(null, "总经理", null, null, "最高权限");

	/*
	 * 构造函数，通过datafactory获得data层的引用
	 */
	public Promotion(){
		try {
			pds=Datafactory.getPromotionData();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vos=getAllPromotion();
	}
	//处理当前使用用户的信息
	public void setUser(UserVO user){
		this.user=user;
	}
	/*
	 * 修改销售策略
	 */
	public ResultMessage modifyPromotion(PromotionVO pvo1){
		//PromotionVO pvo2=searchPromotion(pvo1.getID());
		//cancelPromotion(pvo2);
		//addPromotion(pvo1);
		po.transform(pvo1);
		boolean jug=false;
		try {
			jug=pds.modify(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jug==true){
		buildLog("修改策略:"+po.getID());
		return ResultMessage.Success;
		}
		else
			return ResultMessage.Failure;
	}
	/*
	 * 
	 * 终止销售策略
	 */
	public ResultMessage cancelPromotion(PromotionVO pvo){
		boolean r=false;
		try {
			po.transform(pvo);
			r=pds.delete(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildLog("终止策略:"+po.getID());
		if(r==true)
		return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	
	/*
	 * 
	 * 添加销售策略
	 */
	
	public ResultMessage addPromotion(PromotionVO pvo){
		PromotionPO po =new PromotionPO();
		po.transform(pvo);
		try {
			pds.add(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildLog("添加策略:"+po.getID());
		return ResultMessage.Success;
		
	}
	/*
	 * 
	 * 得到当前策略ID
	 */
	public String getID(){
		String s=null;
		try {
			s = pds.getID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	/*
	 * 
	 * 得到所有的销售策略
	 */
	public ArrayList<PromotionVO> getAllPromotion(){
		ArrayList<PromotionPO> pos = null;
		try {
			pos=pds.show();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<PromotionVO> vos = new ArrayList<PromotionVO>(); 
		if(pos!=null){for(PromotionPO p:pos){
			PromotionVO vo=new PromotionVO(null, 0, 0, null, null, 0, 0, null,0, dt, dt);
			vo.transform(p);
			vos.add(vo);
		}}
		return vos;
	
	}
	/*
	 * 按照ID搜索策略
	 */

	public PromotionVO searchPromotion(String id){
		PromotionVO vo =new PromotionVO(id, 0, 0, null, null, 0, 0, null,0,dt, dt);
		try {
			vo.transform(pds.search(id));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	/*
	 * 通过分析传入的销售单，
	 * 返回对应的可用的销售策略
	 */
	public ArrayList<PromotionVO> availablePromotion(SalesVO svo){
		ArrayList<PromotionVO> vos=this.getAllPromotion();
		ArrayList<PromotionVO> aVos=new ArrayList<PromotionVO>();
		for(PromotionVO vo:vos){
			types t=vo.getType();
			Date startTime=vo.getStartTime();
			Date endTime=vo.getEndTime();
			Date tday=new Date();
	    	boolean j=DateHelper.timeCompare(startTime,tday,endTime);
			if(j){
			switch(t){
			case g:;if(cus.getRank(svo.getCustomer())==vo.getRank()&&svo.getSum()>=vo.getTotalPrice())//判断CustomerRank
				aVos.add(vo);break;
			case v:if(cus.getRank(svo.getCustomer())==vo.getRank()&&svo.getSum()>=vo.getTotalPrice())//判断CustomerRank
				aVos.add(vo);break;
			case d:if(cus.getRank(svo.getCustomer())==vo.getRank())
				aVos.add(vo);break;
			case p:if(judgePackPromotion(svo,vo))
				aVos.add(vo);break;
				}
			}
		}
		return aVos;
	}
	
	/*
	 * 判断能否使用特价包
	 */
	public boolean judgePackPromotion(SalesVO svo,PromotionVO vo){
		
		boolean result=false;
		commodityNum s=new commodityNum();
		ArrayList<CommodityPO> pPos=vo.getpackList();//特价包内商品
		ArrayList<CommodityPO> cPos=svo.getCommodityList();//销售列表的商品
		
		for(CommodityPO po:pPos)
			{
			if((!po.getID().equals(s.ID))&&!s.ID.equals(null)){
				/*
				 * 当遍历到新的特价包商品且不是第一个商品时，判断之前的特价包内商品是否符合情况
				 */
				for(CommodityPO cpo:cPos)
					if(cpo.getID().equals(s.ID)&&s.num<=cpo.getAmount())
						result=true;
				s=new commodityNum(po);
			}
			else if(s.ID.equals(null))
				s=new commodityNum(po);//当遍历到新的特价包商品且是第一个商品时，添加为特价包商品
			else
				s.num++;
		}
		return result;
	}
	/*
	 * 特价包商品计数类
	 */
	private class commodityNum{
		String ID;
		int num;
		commodityNum(){
			ID="";
			num=0;
		}
		commodityNum(CommodityPO po){
			ID=po.getID();
			num=1;
		}
	}
	/*
	 * 清空销售策略数据
	 */
	public boolean delAll(){
		boolean r=false;
		try {
			r=pds.delAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	/*
	 * 记录日志
	 */
	void buildLog(String s){
		log.buildlog(user.getName(), s);
	}
}

