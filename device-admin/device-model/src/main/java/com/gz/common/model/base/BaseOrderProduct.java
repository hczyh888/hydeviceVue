package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOrderProduct<M extends BaseOrderProduct<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOrderId(java.lang.Integer orderId) {
		set("order_id", orderId);
		return (M)this;
	}
	
	public java.lang.Integer getOrderId() {
		return getInt("order_id");
	}

	public M setProductId(java.lang.Integer productId) {
		set("product_id", productId);
		return (M)this;
	}
	
	public java.lang.Integer getProductId() {
		return getInt("product_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setPrice(java.lang.Float price) {
		set("price", price);
		return (M)this;
	}
	
	public java.lang.Float getPrice() {
		return getFloat("price");
	}

	public M setSn(java.lang.String sn) {
		set("sn", sn);
		return (M)this;
	}
	
	public java.lang.String getSn() {
		return getStr("sn");
	}

	public M setPromotionName(java.lang.String promotionName) {
		set("promotion_name", promotionName);
		return (M)this;
	}
	
	public java.lang.String getPromotionName() {
		return getStr("promotion_name");
	}

	public M setProductImg(java.lang.String productImg) {
		set("product_img", productImg);
		return (M)this;
	}
	
	public java.lang.String getProductImg() {
		return getStr("product_img");
	}

	public M setQuantity(java.lang.Integer quantity) {
		set("quantity", quantity);
		return (M)this;
	}
	
	public java.lang.Integer getQuantity() {
		return getInt("quantity");
	}

	public M setSpecificationValue(java.lang.String specificationValue) {
		set("specification_value", specificationValue);
		return (M)this;
	}
	
	public java.lang.String getSpecificationValue() {
		return getStr("specification_value");
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
