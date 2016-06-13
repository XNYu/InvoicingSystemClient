package businesslogic.utilitybl;

public class CheckDouble {
	public  static boolean isDouble(char a) {
		
		return (a >= '0' && a <= '9');
	}

	public  static boolean isAllDouble(String s) {
		boolean flag = true;
		if (null == s || "".equals(s.trim())) return false;
		for (int i = 0; i < s.length(); i++) {
			if (!isDouble(s.charAt(i))) return false;
		}
		
		return flag;
	}
}
