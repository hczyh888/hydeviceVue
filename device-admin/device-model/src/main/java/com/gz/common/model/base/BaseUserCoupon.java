package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUserCoupon<M extends BaseUserCoupon<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUserId(java.lang.Integer userId) {
		set("userId", userId);
		return (M)this;
	}
	
	public java.lang.Integer getUserId() {
		return getInt("userId");
	}

	public M setCouponId(java.lang.Integer couponId) {
		set("couponId", couponId);
		return (M)this;
	}
	
	public java.lang.Integer getCouponId() {
		return getInt("couponId");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public M setExpiryDate(java.util.Date expiryDate) {
		set("expiryDate", expiryDate);
		return (M)this;
	}
	
	public java.util.Date getExpiryDate() {
		return get("expiryDate");
	}

	public M setStatus(java.lang.Boolean status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Boolean getStatus() {
		return get("status");
	}

}
