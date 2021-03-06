package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDeviceClass<M extends BaseDeviceClass<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setPid(java.lang.Long pid) {
		set("pid", pid);
		return (M)this;
	}
	
	public java.lang.Long getPid() {
		return getLong("pid");
	}

	public M setClassBm(java.lang.String classBm) {
		set("classBm", classBm);
		return (M)this;
	}
	
	public java.lang.String getClassBm() {
		return getStr("classBm");
	}

	public M setClassName(java.lang.String className) {
		set("className", className);
		return (M)this;
	}
	
	public java.lang.String getClassName() {
		return getStr("className");
	}

	public M setClassSimple(java.lang.String classSimple) {
		set("classSimple", classSimple);
		return (M)this;
	}
	
	public java.lang.String getClassSimple() {
		return getStr("classSimple");
	}

	public M setLevel(java.lang.Integer level) {
		set("level", level);
		return (M)this;
	}
	
	public java.lang.Integer getLevel() {
		return getInt("level");
	}

	public M setSortNum(java.lang.Integer sortNum) {
		set("sort_num", sortNum);
		return (M)this;
	}
	
	public java.lang.Integer getSortNum() {
		return getInt("sort_num");
	}

}
