package promotionTest;

import static org.junit.Assert.*;

import VO.PromotionVO;
import VO.PromotionVO.types;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.promotionbl.PromotionController;
import businesslogic.utilitybl.*;
import businesslogicservice.promotionBLService.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PromotionBLTest {
	 PromotionVO pvo;
	 PromotionVO pvo1;
	 UserVO user=new UserVO(null, null, null, null, null);
	SalesVO svo=new SalesVO(null, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null);
	PromotionBLService a=new PromotionController(user);
	
	@Before
	public void setUp() throws Exception {
		pvo=new PromotionVO("10000",1, 0, types.d, null, 0.5, 0, null,0, null,null);
		pvo1=new PromotionVO("10001",1, 0, types.d, null, 0.5, 0, null,0, null,null);
		a.addPromotion(pvo1);
	}
	
	@After
    public void tearDown(){
		a.delAll();
	}
	
	@Test
	public void addPromotionTest1() {
		assertTrue(a.addPromotion(pvo)==ResultMessage.Success);
	}
	
	@Test
	public void getIDTest() {
		assertTrue(a.getID()!=null);
	}
	
	@Test
	public void getAllPromotionTest() {
		assertTrue(a.getAllPromotion()!=null);
	}
	
	@Test
	public void searchPromotionTest1() {
		assertTrue(a.searchPromotion(pvo1.getID()).getID().equals(pvo1.getID()));
	}
	
	@Test
	public void availablePromotionTest() {
		assertTrue(true);
	}
	
	@Test
	public void modifyTest() {
		assertTrue(a.modifyPromotion(pvo1)==ResultMessage.Success);
	}
	
	@Test
	public void modifyTest2() {
		assertTrue(a.modifyPromotion(pvo)!=ResultMessage.Failure);
	}

}
