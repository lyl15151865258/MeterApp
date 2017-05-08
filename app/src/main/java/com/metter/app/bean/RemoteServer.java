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
@XStreamAlias("remote")
public class RemoteServer extends Entity {

//    public final static int RELATION_ACTION_DELETE = 0x00;// 取消关注
//    public final static int RELATION_ACTION_ADD = 0x01;// 加关注
//
//    public final static int RELATION_TYPE_BOTH = 0x01;// 双方互为粉丝
//    public final static int RELATION_TYPE_FANS_HIM = 0x02;// 你单方面关注他
//    public final static int RELATION_TYPE_NULL = 0x03;// 互不关注
//    public final static int RELATION_TYPE_FANS_ME = 0x04;// 只有他关注我




    @XStreamAlias("serviceIp")
    private String serviceIp;

    @XStreamAlias("servicePort")
    private String servicePort;

    @XStreamAlias("serviceName")
    private String serviceName;

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	public String getServicePort() {
		return servicePort;
	}

	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String toString() {
		return "RemoteServer [serviceIp=" + serviceIp + ", servicePort="
				+ servicePort + ", serviceName=" + serviceName + "]";
	}

  

    

   
}
