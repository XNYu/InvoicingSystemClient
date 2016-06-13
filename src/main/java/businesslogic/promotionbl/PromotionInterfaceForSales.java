package businesslogic.promotionbl;

import java.util.ArrayList;

import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;

public interface PromotionInterfaceForSales {
	public ArrayList<PromotionVO> availablePromotion(SalesVO svo);
	public void setUser(UserVO user);
}
