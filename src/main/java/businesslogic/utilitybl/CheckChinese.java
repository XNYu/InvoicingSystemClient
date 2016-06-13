package businesslogic.utilitybl;

public class CheckChinese {
	public  static boolean isChinese(char a) {
		int v = (int)a;
		return (v >= 19968 && v <= 171941);
	}

	public  static boolean isAllChinese(String s) {
		
		boolean flag = true;
		if (null == s || "".equals(s.trim())) return false;
		for (int i = 0; i < s.length(); i++) {
			if (!isChinese(s.charAt(i))) return false;
		}
		return flag;
	}

}
