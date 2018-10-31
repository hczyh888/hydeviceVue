package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseStockIn<M extends BaseStockIn<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setBillNo(java.lang.String billNo) {
		set("billNo", billNo);
		return (M)this;
	}
	
	public java.lang.String getBillNo() {
		return getStr("billNo");
	}

	public M setBillDate(java.util.Date billDate) {
		set("billDate", billDate);
		return (M)this;
	}
	
	public java.util.Date getBillDate() {
		return get("billDate");
	}

	public M setWarehouseId(java.lang.Integer warehouseId) {
		set("warehouseId", warehouseId);
		return (M)this;
	}
	
	public java.lang.Integer getWarehouseId() {
		return getInt("warehouseId");
	}

	public M setSumQuantity(java.lang.Double sumQuantity) {
		set("sumQuantity", sumQuantity);
		return (M)this;
	}
	
	public java.lang.Double getSumQuantity() {
		return getDouble("sumQuantity");
	}

	public M setSumMoney(java.lang.Double sumMoney) {
		set("sumMoney", sumMoney);
		return (M)this;
	}
	
	public java.lang.Double getSumMoney() {
		return getDouble("sumMoney");
	}

	public M setOperator(java.lang.Integer operator) {
		set("operator", operator);
		return (M)this;
	}
	
	public java.lang.Integer getOperator() {
		return getInt("operator");
	}

	public M setStockman(java.lang.Integer stockman) {
		set("stockman", stockman);
		return (M)this;
	}
	
	public java.lang.Integer getStockman() {
		return getInt("stockman");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
