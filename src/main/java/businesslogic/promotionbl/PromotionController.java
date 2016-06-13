package businesslogic.promotionbl;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;


import VO.CommodityVO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.promotionbl.Promotion;
import businesslogic.utilitybl.*;
import businesslogicservice.promotionBLService.PromotionBLService;

public class PromotionController implements PromotionBLService{
	Promotion p=new Promotion();
	
	public PromotionController(UserVO user){
		p.setUser(user);
	}
	
	public ResultMessage modifyPromotion(PromotionVO pvo){
		return p.modifyPromotion(pvo);
	}
	
	public ResultMessage cancelPromotion(PromotionVO pvo){
		return p.cancelPromotion(pvo);
		}
	
	public String getID(){
		return p.getID();
	}
	
	public ResultMessage addPromotion(PromotionVO pvo){
		return p.addPromotion(pvo);
	}
	
	
	
	public ArrayList<PromotionVO> getAllPromotion(){
		ArrayList<PromotionVO> pvos=p.getAllPromotion();
		return pvos;
	}
	
	public PromotionVO searchPromotion(String id){
		PromotionVO pvo = p.searchPromotion(id);
		return pvo;
	}
	
	public ArrayList<PromotionVO> availablePromotion(SalesVO svo){
		ArrayList<PromotionVO> aVos=p.availablePromotion(svo);
		return aVos;
	}

	@Override
	public void endPromotion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delAll() {
		// TODO Auto-generated method stub
		return p.delAll();
	}
	

	
	
}