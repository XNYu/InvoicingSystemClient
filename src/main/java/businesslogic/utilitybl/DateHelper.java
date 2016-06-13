package businesslogic.utilitybl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class DateHelper implements Serializable{
	private static final long serialVersionUID = 1L;
	public String getDate(){
		Calendar ca = Calendar.getInstance();
        int monthCount=ca.get(Calendar.MONTH);//获取月份 
        int dayCount=ca.get(Calendar.DATE);   //获取日
        String year = String.valueOf(ca.get(Calendar.YEAR));
        String month = String.valueOf(ca.get(Calendar.MONTH)+1);
        String day = String.valueOf(ca.get(Calendar.DATE));
        if(monthCount<9)
        	month = "0"+month;
        if(dayCount<=9)
        	day = "0"+day;
        String date = year+month+day;
		return date;
	}
	public static void main(String[] args){
		DateHelper dh = new DateHelper();
		System.out.println(dh.getDate());
	}
	public static boolean timeCompare(Date startTime,Date tday,Date endTime){
		boolean result=false;
		Calendar start = Calendar.getInstance();
    	Calendar now = Calendar.getInstance();
    	Calendar end = Calendar.getInstance();
    	start.setTime(startTime);
		now.setTime(tday);
		end.setTime(endTime);
		boolean a=before(start,now);
		boolean b=after(end,now);
		if(a&&b)
			result=true;
		System.out.println(result);
		return result;
	}//比较是否符合当前时间
	
	public static boolean after(Calendar c1,Calendar c2){
		boolean result=false;
		if((c1.get(Calendar.YEAR)>c2.get(Calendar.YEAR))||(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)&&c1.get(Calendar.DAY_OF_YEAR)>=c2.get(Calendar.DAY_OF_YEAR)))
			result=true;	
		return result;
	}

	public static boolean before(Calendar c1,Calendar c2){
		boolean result=false;
		if((c1.get(Calendar.YEAR)<c2.get(Calendar.YEAR))||(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)&&c1.get(Calendar.DAY_OF_YEAR)<=c2.get(Calendar.DAY_OF_YEAR)))
			result=true;	
		return result;
	}
	
}
