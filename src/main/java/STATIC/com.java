package STATIC;

public class com{
	//包含数量名称时间的商品
	public String name,time;
	public int num;
	public int year,month,day;
	
	public com(String n,int nu,String time){
		name=n;
		num=nu;
		year=Integer.parseInt(time.substring(4, 8).toString());
		month=Integer.parseInt(time.substring(8, 10).toString());
		day=Integer.parseInt(time.substring(10, 12).toString());
		this.time=time.substring(4, 12).toString();
	}
	
	public void addNum(int n){
		num+=n;
	}
	public String getName(){
		return name;
	}
	public int getYear(){
		return year;
	}
	public int getMonth(){
		return month;
	}
	public int getDay(){
		return day;
	}
	
}
