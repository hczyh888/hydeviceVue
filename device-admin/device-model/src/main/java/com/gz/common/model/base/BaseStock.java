package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseStock<M extends BaseStock<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setDeviceId(java.lang.Integer deviceId) {
		set("deviceId", deviceId);
		return (M)this;
	}
	
	public java.lang.Integer getDeviceId() {
		return getInt("deviceId");
	}

	public M setDeviceSn(java.lang.String deviceSn) {
		set("deviceSn", deviceSn);
		return (M)this;
	}
	
	public java.lang.String getDeviceSn() {
		return getStr("deviceSn");
	}

	public M setDeviceArchives(java.lang.String deviceArchives) {
		set("deviceArchives", deviceArchives);
		return (M)this;
	}
	
	public java.lang.String getDeviceArchives() {
		return getStr("deviceArchives");
	}

	public M setQuantity(java.lang.Double quantity) {
		set("quantity", quantity);
		return (M)this;
	}
	
	public java.lang.Double getQuantity() {
		return getDouble("quantity");
	}

	public M setPrice(java.lang.Double price) {
		set("price", price);
		return (M)this;
	}
	
	public java.lang.Double getPrice() {
		return getDouble("price");
	}

}
