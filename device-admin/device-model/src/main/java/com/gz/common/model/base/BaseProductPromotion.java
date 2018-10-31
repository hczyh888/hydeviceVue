package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseProductPromotion<M extends BaseProductPromotion<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setProductId(java.lang.Integer productId) {
		set("productId", productId);
		return (M)this;
	}
	
	public java.lang.Integer getProductId() {
		return getInt("productId");
	}

	public M setPromotionId(java.lang.Integer promotionId) {
		set("promotionId", promotionId);
		return (M)this;
	}
	
	public java.lang.Integer getPromotionId() {
		return getInt("promotionId");
	}

	public M setDiscount(java.lang.Float discount) {
		set("discount", discount);
		return (M)this;
	}
	
	public java.lang.Float getDiscount() {
		return getFloat("discount");
	}

	public M setReduceMoney(java.lang.Float reduceMoney) {
		set("reduceMoney", reduceMoney);
		return (M)this;
	}
	
	public java.lang.Float getReduceMoney() {
		return getFloat("reduceMoney");
	}

	public M setLastPrice(java.lang.Float lastPrice) {
		set("lastPrice", lastPrice);
		return (M)this;
	}
	
	public java.lang.Float getLastPrice() {
		return getFloat("lastPrice");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
