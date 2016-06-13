package VO;

public class CommodityTypeVO {
	private String type = "AAAAAA";

	public CommodityTypeVO(CommodityTypeVO t){
		String s = t.getType();
		this.type = s;
	}
	
	public CommodityTypeVO(String s){
		this.type = s;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
