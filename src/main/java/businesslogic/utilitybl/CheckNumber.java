package businesslogic.utilitybl;

public class CheckNumber {
	public static boolean isInteger(String value) {
		  try {
		   Integer.parseInt(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }

		 /**
		  * 判断字符串是否是浮点数
		  */
   public static boolean isDouble(String value) {
		  try {
		   Double.parseDouble(value);
		   if (value.contains("."))
		    return true;
		   return false;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }
   public static boolean isNumber(String value) {
  	  return isInteger(value) || isDouble(value);
  	 }
   public static boolean isNum(String value){
	   boolean flag=true;
	   char [] v=value.toCharArray();
	   for(int i=0;i!=value.length();++i){
		   if(!(v[i]<='9'&&v[i]>='0')){
			   flag=false;
		   }
	   }
	   return flag;
   }
}
