package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOrder<M extends BaseOrder<M>> extends Model<M> implements IBean {

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

	public M setOrderSN(java.lang.String orderSN) {
		set("orderSN", orderSN);
		return (M)this;
	}
	
	public java.lang.String getOrderSN() {
		return getStr("orderSN");
	}

	public M setOrderStatus(java.lang.Integer orderStatus) {
		set("order_status", orderStatus);
		return (M)this;
	}
	
	public java.lang.Integer getOrderStatus() {
		return getInt("order_status");
	}

	public M setPaymentStatus(java.lang.Integer paymentStatus) {
		set("payment_status", paymentStatus);
		return (M)this;
	}
	
	public java.lang.Integer getPaymentStatus() {
		return getInt("payment_status");
	}

	public M setShippingStatus(java.lang.Integer shippingStatus) {
		set("shipping_status", shippingStatus);
		return (M)this;
	}
	
	public java.lang.Integer getShippingStatus() {
		return getInt("shipping_status");
	}

	public M setAddress(java.lang.String address) {
		set("address", address);
		return (M)this;
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

	public M setTotalPrice(java.lang.Float totalPrice) {
		set("totalPrice", totalPrice);
		return (M)this;
	}
	
	public java.lang.Float getTotalPrice() {
		return getFloat("totalPrice");
	}

	public M setPostFee(java.lang.Float postFee) {
		set("postFee", postFee);
		return (M)this;
	}
	
	public java.lang.Float getPostFee() {
		return getFloat("postFee");
	}

	public M setPayFee(java.lang.Float payFee) {
		set("payFee", payFee);
		return (M)this;
	}
	
	public java.lang.Float getPayFee() {
		return getFloat("payFee");
	}

	public M setYouhuis(java.lang.String youhuis) {
		set("youhuis", youhuis);
		return (M)this;
	}
	
	public java.lang.String getYouhuis() {
		return getStr("youhuis");
	}

	public M setCouponName(java.lang.String couponName) {
		set("coupon_name", couponName);
		return (M)this;
	}
	
	public java.lang.String getCouponName() {
		return getStr("coupon_name");
	}

	public M setCouponMoney(java.lang.Integer couponMoney) {
		set("coupon_money", couponMoney);
		return (M)this;
	}
	
	public java.lang.Integer getCouponMoney() {
		return getInt("coupon_money");
	}

	public M setUserCouponId(java.lang.Integer userCouponId) {
		set("user_coupon_id", userCouponId);
		return (M)this;
	}
	
	public java.lang.Integer getUserCouponId() {
		return getInt("user_coupon_id");
	}

	public M setTradeNo(java.lang.String tradeNo) {
		set("trade_no", tradeNo);
		return (M)this;
	}
	
	public java.lang.String getTradeNo() {
		return getStr("trade_no");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public M setActive(java.lang.Boolean active) {
		set("active", active);
		return (M)this;
	}
	
	public java.lang.Boolean getActive() {
		return get("active");
	}

}