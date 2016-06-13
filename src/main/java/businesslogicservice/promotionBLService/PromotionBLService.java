package businesslogicservice.promotionBLService;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import VO.PromotionVO;
import VO.SalesVO;

public interface PromotionBLService {
	public ResultMessage cancelPromotion(PromotionVO pro);
	public ResultMessage modifyPromotion(PromotionVO pro);
	public ResultMessage addPromotion(PromotionVO pvo);
	public ArrayList<PromotionVO> getAllPromotion();
	public PromotionVO searchPromotion(String id);
	public ArrayList<PromotionVO> availablePromotion(SalesVO svo);
	public String getID();
	public void endPromotion();
	public boolean delAll();
}
