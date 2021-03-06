package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCompany<M extends BaseCompany<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCnName(java.lang.String cnName) {
		set("cnName", cnName);
		return (M)this;
	}
	
	public java.lang.String getCnName() {
		return getStr("cnName");
	}

	public M setEnName(java.lang.String enName) {
		set("enName", enName);
		return (M)this;
	}
	
	public java.lang.String getEnName() {
		return getStr("enName");
	}

	public M setAddr(java.lang.String addr) {
		set("addr", addr);
		return (M)this;
	}
	
	public java.lang.String getAddr() {
		return getStr("addr");
	}

	public M setTel(java.lang.String tel) {
		set("tel", tel);
		return (M)this;
	}
	
	public java.lang.String getTel() {
		return getStr("tel");
	}

	public M setLogo(java.lang.String logo) {
		set("logo", logo);
		return (M)this;
	}
	
	public java.lang.String getLogo() {
		return getStr("logo");
	}

}
