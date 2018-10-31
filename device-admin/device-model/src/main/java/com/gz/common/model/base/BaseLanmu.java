package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLanmu<M extends BaseLanmu<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setLevel(java.lang.Integer level) {
		set("level", level);
		return (M)this;
	}
	
	public java.lang.Integer getLevel() {
		return getInt("level");
	}

	public M setUpLevelid(java.lang.Integer upLevelid) {
		set("up_levelId", upLevelid);
		return (M)this;
	}
	
	public java.lang.Integer getUpLevelid() {
		return getInt("up_levelId");
	}

	public M setRoute(java.lang.String route) {
		set("route", route);
		return (M)this;
	}
	
	public java.lang.String getRoute() {
		return getStr("route");
	}

	public M setSortNum(java.lang.Integer sortNum) {
		set("sort_num", sortNum);
		return (M)this;
	}
	
	public java.lang.Integer getSortNum() {
		return getInt("sort_num");
	}

	public M setIsSingle(java.lang.Integer isSingle) {
		set("is_single", isSingle);
		return (M)this;
	}
	
	public java.lang.Integer getIsSingle() {
		return getInt("is_single");
	}

	public M setIsSysterm(java.lang.Integer isSysterm) {
		set("is_systerm", isSysterm);
		return (M)this;
	}
	
	public java.lang.Integer getIsSysterm() {
		return getInt("is_systerm");
	}

	public M setDataCount(java.lang.Integer dataCount) {
		set("data_count", dataCount);
		return (M)this;
	}
	
	public java.lang.Integer getDataCount() {
		return getInt("data_count");
	}

	public M setFontIcon(java.lang.String fontIcon) {
		set("font_icon", fontIcon);
		return (M)this;
	}
	
	public java.lang.String getFontIcon() {
		return getStr("font_icon");
	}

	public M setLanmuType(java.lang.Integer lanmuType) {
		set("lanmu_type", lanmuType);
		return (M)this;
	}
	
	public java.lang.Integer getLanmuType() {
		return getInt("lanmu_type");
	}

	public M setModuleId(java.lang.Integer moduleId) {
		set("module_id", moduleId);
		return (M)this;
	}
	
	public java.lang.Integer getModuleId() {
		return getInt("module_id");
	}

	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}
	
	public java.lang.String getDescription() {
		return getStr("description");
	}

}
