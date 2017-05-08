package com.metter.app.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 登录用户实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
@XStreamAlias("user")
public class User extends Entity {

//    public final static int RELATION_ACTION_DELETE = 0x00;// 取消关注
//    public final static int RELATION_ACTION_ADD = 0x01;// 加关注
//
//    public final static int RELATION_TYPE_BOTH = 0x01;// 双方互为粉丝
//    public final static int RELATION_TYPE_FANS_HIM = 0x02;// 你单方面关注他
//    public final static int RELATION_TYPE_NULL = 0x03;// 互不关注
//    public final static int RELATION_TYPE_FANS_ME = 0x04;// 只有他关注我

	@XStreamAlias("id")
	private int id;
	
    @XStreamAlias("supplierId")
    private int supplierId;
    
    @XStreamAlias("villageId")
    private int villageId;
    
    @XStreamAlias("exchangStationId")
    private int exchangStationId;
    
    @XStreamAlias("buildingId")
    private int buildingId;
    
    @XStreamAlias("entranceId")
    private int entranceId;
    

    @XStreamAlias("loginName")
    private String loginName;

    @XStreamAlias("password")
    private String password;

    @XStreamAlias("userName")
    private String userName;

    @XStreamAlias("deptName")
    private String deptName;

    @XStreamAlias("deptNo")
    private String deptNo;

    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public int getVillageId() {
		return villageId;
	}

	public void setVillageId(int villageId) {
		this.villageId = villageId;
	}

	public int getExchangStationId() {
		return exchangStationId;
	}

	public void setExchangStationId(int exchangStationId) {
		this.exchangStationId = exchangStationId;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public int getEntranceId() {
		return entranceId;
	}

	public void setEntranceId(int entranceId) {
		this.entranceId = entranceId;
	}

	public User(){
		
	}
	@Override
	public String toString() {
		return "User [supplierId=" + supplierId + ", villageId=" + villageId
				+ ", exchangStationId=" + exchangStationId + ", buildingId="
				+ buildingId + ", entranceId=" + entranceId + ", loginName="
				+ loginName + ", password=" + password + ", userName="
				+ userName + ", deptName=" + deptName + ", deptNo=" + deptNo
				+ "]";
	}

	
    
    

   
}
