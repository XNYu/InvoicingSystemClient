package businesslogicservice.examineBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import VO.ExamineVO;

public interface ExamineBLService {
	public ArrayList<ExamineVO> searchDoc( );
	public ResultMessage chooseDoc(ArrayList<ExamineVO> vos);
	public ResultMessage modifyDoc(ExamineVO vo);
	public ResultMessage confirmDoc(ExamineVO vo );
	public void endExamine();
}
