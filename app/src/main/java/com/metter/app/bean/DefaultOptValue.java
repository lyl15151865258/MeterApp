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
@XStreamAlias("lagtoptvalue")
public class DefaultOptValue extends Entity {

    @XStreamAlias("defaultCollector")
    private String defaultCollector ;

    @XStreamAlias("defaultMeter")
    private String defaultMeter;

	public String getDefaultCollector() {
		return defaultCollector;
	}

	public void setDefaultCollector(String defaultCollector) {
		this.defaultCollector = defaultCollector;
	}

	public String getDefaultMeter() {
		return defaultMeter;
	}

	public void setDefaultMeter(String defaultMeter) {
		this.defaultMeter = defaultMeter;
	}

	@Override
	public String toString() {
		return "LastOptValue [defaultCollector=" + defaultCollector
				+ ", defaultMeter=" + defaultMeter + "]";
	}

 

  

    

   
}
