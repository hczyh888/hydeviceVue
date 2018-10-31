package com.gz.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUserBak<M extends BaseUserBak<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUsername(java.lang.String username) {
		set("username", username);
		return (M)this;
	}
	
	public java.lang.String getUsername() {
		return getStr("username");
	}

	public M setNickname(java.lang.String nickname) {
		set("nickname", nickname);
		return (M)this;
	}
	
	public java.lang.String getNickname() {
		return getStr("nickname");
	}

	public M setRealname(java.lang.String realname) {
		set("realname", realname);
		return (M)this;
	}
	
	public java.lang.String getRealname() {
		return getStr("realname");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}
	
	public java.lang.String getPassword() {
		return getStr("password");
	}

	public M setSalt(java.lang.String salt) {
		set("salt", salt);
		return (M)this;
	}
	
	public java.lang.String getSalt() {
		return getStr("salt");
	}

	public M setEmail(java.lang.String email) {
		set("email", email);
		return (M)this;
	}
	
	public java.lang.String getEmail() {
		return getStr("email");
	}

	public M setEmailStatus(java.lang.String emailStatus) {
		set("email_status", emailStatus);
		return (M)this;
	}
	
	public java.lang.String getEmailStatus() {
		return getStr("email_status");
	}

	public M setMobile(java.lang.String mobile) {
		set("mobile", mobile);
		return (M)this;
	}
	
	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public M setMobileStatus(java.lang.String mobileStatus) {
		set("mobile_status", mobileStatus);
		return (M)this;
	}
	
	public java.lang.String getMobileStatus() {
		return getStr("mobile_status");
	}

	public M setTelephone(java.lang.String telephone) {
		set("telephone", telephone);
		return (M)this;
	}
	
	public java.lang.String getTelephone() {
		return getStr("telephone");
	}

	public M setAmount(java.math.BigDecimal amount) {
		set("amount", amount);
		return (M)this;
	}
	
	public java.math.BigDecimal getAmount() {
		return get("amount");
	}

	public M setGender(java.lang.String gender) {
		set("gender", gender);
		return (M)this;
	}
	
	public java.lang.String getGender() {
		return getStr("gender");
	}

	public M setRoleId(java.lang.Integer roleId) {
		set("role_id", roleId);
		return (M)this;
	}
	
	public java.lang.Integer getRoleId() {
		return getInt("role_id");
	}

	public M setSignature(java.lang.String signature) {
		set("signature", signature);
		return (M)this;
	}
	
	public java.lang.String getSignature() {
		return getStr("signature");
	}

	public M setContentCount(java.lang.Long contentCount) {
		set("content_count", contentCount);
		return (M)this;
	}
	
	public java.lang.Long getContentCount() {
		return getLong("content_count");
	}

	public M setCommentCount(java.lang.Long commentCount) {
		set("comment_count", commentCount);
		return (M)this;
	}
	
	public java.lang.Long getCommentCount() {
		return getLong("comment_count");
	}

	public M setQq(java.lang.String qq) {
		set("qq", qq);
		return (M)this;
	}
	
	public java.lang.String getQq() {
		return getStr("qq");
	}

	public M setWechat(java.lang.String wechat) {
		set("wechat", wechat);
		return (M)this;
	}
	
	public java.lang.String getWechat() {
		return getStr("wechat");
	}

	public M setWeibo(java.lang.String weibo) {
		set("weibo", weibo);
		return (M)this;
	}
	
	public java.lang.String getWeibo() {
		return getStr("weibo");
	}

	public M setFacebook(java.lang.String facebook) {
		set("facebook", facebook);
		return (M)this;
	}
	
	public java.lang.String getFacebook() {
		return getStr("facebook");
	}

	public M setLinkedin(java.lang.String linkedin) {
		set("linkedin", linkedin);
		return (M)this;
	}
	
	public java.lang.String getLinkedin() {
		return getStr("linkedin");
	}

	public M setBirthday(java.util.Date birthday) {
		set("birthday", birthday);
		return (M)this;
	}
	
	public java.util.Date getBirthday() {
		return get("birthday");
	}

	public M setCompany(java.lang.String company) {
		set("company", company);
		return (M)this;
	}
	
	public java.lang.String getCompany() {
		return getStr("company");
	}

	public M setOccupation(java.lang.String occupation) {
		set("occupation", occupation);
		return (M)this;
	}
	
	public java.lang.String getOccupation() {
		return getStr("occupation");
	}

	public M setAddress(java.lang.String address) {
		set("address", address);
		return (M)this;
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

	public M setZipcode(java.lang.String zipcode) {
		set("zipcode", zipcode);
		return (M)this;
	}
	
	public java.lang.String getZipcode() {
		return getStr("zipcode");
	}

	public M setSite(java.lang.String site) {
		set("site", site);
		return (M)this;
	}
	
	public java.lang.String getSite() {
		return getStr("site");
	}

	public M setGraduateschool(java.lang.String graduateschool) {
		set("graduateschool", graduateschool);
		return (M)this;
	}
	
	public java.lang.String getGraduateschool() {
		return getStr("graduateschool");
	}

	public M setEducation(java.lang.String education) {
		set("education", education);
		return (M)this;
	}
	
	public java.lang.String getEducation() {
		return getStr("education");
	}

	public M setAvatar(java.lang.String avatar) {
		set("avatar", avatar);
		return (M)this;
	}
	
	public java.lang.String getAvatar() {
		return getStr("avatar");
	}

	public M setIdcardtype(java.lang.String idcardtype) {
		set("idcardtype", idcardtype);
		return (M)this;
	}
	
	public java.lang.String getIdcardtype() {
		return getStr("idcardtype");
	}

	public M setIdcard(java.lang.String idcard) {
		set("idcard", idcard);
		return (M)this;
	}
	
	public java.lang.String getIdcard() {
		return getStr("idcard");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setCreated(java.util.Date created) {
		set("created", created);
		return (M)this;
	}
	
	public java.util.Date getCreated() {
		return get("created");
	}

	public M setCreateSource(java.lang.String createSource) {
		set("create_source", createSource);
		return (M)this;
	}
	
	public java.lang.String getCreateSource() {
		return getStr("create_source");
	}

	public M setLogged(java.util.Date logged) {
		set("logged", logged);
		return (M)this;
	}
	
	public java.util.Date getLogged() {
		return get("logged");
	}

	public M setActivated(java.util.Date activated) {
		set("activated", activated);
		return (M)this;
	}
	
	public java.util.Date getActivated() {
		return get("activated");
	}

	public M setToken(java.lang.String token) {
		set("token", token);
		return (M)this;
	}
	
	public java.lang.String getToken() {
		return getStr("token");
	}

	public M setOpenId(java.lang.String openId) {
		set("openId", openId);
		return (M)this;
	}
	
	public java.lang.String getOpenId() {
		return getStr("openId");
	}

	public M setTokenExpiretime(java.util.Date tokenExpiretime) {
		set("token_expiretime", tokenExpiretime);
		return (M)this;
	}
	
	public java.util.Date getTokenExpiretime() {
		return get("token_expiretime");
	}

}
