package PO;

import java.io.Serializable;
import java.util.ArrayList;


public class CommodityTypePO implements Serializable{
	private static final long serialVersionUID = 1L;
	public int  numOfChildren = 0,numOfCommodity = 0;
	public int realNumOfCommodity = 0;
	private String typeID = "000000";
    private String type = null;
	private boolean hasCommodity = false;
	private boolean isLeaf=true;
	public ArrayList<String> child = new ArrayList<String>();
	private String father = "";
	
	public String getFather() {
		return father;
	}
	public void setFather(String father) {
		this.father = father;
	}
	public int getNumOfChildren() {
		return numOfChildren;
	}
	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
	}
	public int getNumOfCommodity() {
		return numOfCommodity;
	}
	public void setNumOfCommodity(int numOfCommodity) {
		this.numOfCommodity = numOfCommodity;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public CommodityTypePO(CommodityTypePO p){
		String s = p.getType();
		this.type = s;
	}
	public CommodityTypePO(String s){
		type = s;
	}
	
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public boolean isHasCommodity() {
		return hasCommodity;
	}
	public void setHasCommodity(boolean hasCommodity) {
		this.hasCommodity = hasCommodity;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
