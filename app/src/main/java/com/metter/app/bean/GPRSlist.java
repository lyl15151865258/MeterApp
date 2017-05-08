package com.metter.app.bean;

public class GPRSlist {
	private String list1;
	private String list2;
	private String list3;
	private String list4;
	public  GPRSlist() {
		super();
	}
	public GPRSlist(String list1,String list2,String list3,String list4)
	{
		super();
		this.list1=list1;
		this.list2=list2;
		this.list3=list3;
		this.list4=list4;
	}
	public String getList1() {
		return list1;
	}
	public void setList1(String list1) {
		this.list1 = list1;
	}
	public String getList2() {
		return list2;
	}
	public void setList2(String list2) {
		this.list2 = list2;
	}
	public String getList3() {
		return list3;
	}
	public void setList3(String list3) {
		this.list3 = list3;
	}
	public String getList4() {
		return list4;
	}
	public void setList4(String list4) {
		this.list4 = list4;
	}
}