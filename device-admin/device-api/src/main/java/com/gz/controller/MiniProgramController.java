package com.gz.controller;

import com.alibaba.druid.sql.visitor.functions.Ucase;
import com.alibaba.fastjson.JSONObject;
import com.gz.common.UserService;
import com.gz.common.model.Role;
import com.gz.common.model.User;
import com.gz.utils.Response;
import com.gz.utils.TimeUtil;
import com.jfinal.aop.Duang;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.cache.IAccessTokenCache;
import com.jfinal.wxaapp.WxaConfig;
import com.jfinal.wxaapp.WxaConfigKit;
import com.jfinal.wxaapp.api.WxaUserApi;
import com.jfinal.wxaapp.jfinal.WxaController;

import java.util.Date;


/**
 * Created by gongzhen on 2018/6/19.
 */
public class MiniProgramController extends WxaController {
    // 微信用户接口api
    protected WxaUserApi wxaUserApi = Duang.duang(WxaUserApi.class);
    /**
     * 登陆接口
     */
    public void login() {
        String jsCode = getPara("code");
        if (StrKit.isBlank(jsCode)) {
            Kv data = Kv.by("errcode", 500)
                    .set("errmsg", "code is blank");
            renderJson(data);
            return;
        }
        WxaConfig wxaConfig=new WxaConfig();
        wxaConfig.setAppId("wx194280f406ccd26d");
        wxaConfig.setAppSecret("5606724e29e953075287dc2d766a8f34");
        WxaConfigKit.setWxaConfig(wxaConfig);
        // 获取SessionKey
        ApiResult apiResult = wxaUserApi.getSessionKey(jsCode);
        // 返回{"session_key":"nzoqhc3OnwHzeTxJs+inbQ==","expires_in":2592000,"openid":"oVBkZ0aYgDMDIywRdgPW8-joxXc4"}
        if (apiResult.isSucceed()) {
            User user= UserService.getService().getUserByOpenId(apiResult.getStr("openid"));
            if(user==null){
                user=new User();
               // user.setOpenId(apiResult.get("openid"));
                Role role=UserService.getService().getRole("wechat");
                if(role==null){
                    role.setName("wechat");
                    role.setName("微信用户");
                    role.save();
                }
                user.setRoleId(role.getId());
               // user.setCreated(new Date());
              //  user.setActivated(new Date());
                user.setSalt(UserService.getService().createSalt());
                UserService.getService().createToken(user);
                user.save();
            }
           // user.setLogged(new Date());
           // if(user.getTokenExpiretime().getTime()<new Date().getTime()){
           //     UserService.getService().createToken(user);
          //  }
            user.update();
            // 利用 appId 与 accessToken 建立关联，支持多账户
            IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();
            String sessionId = StrKit.getRandomUUID();
            accessTokenCache.set("wxa:session:" + sessionId, apiResult.getJson());
            renderJson(Response.responseJson(0,"登录成功",user));
        }else{
            renderJson(apiResult.getJson());
        }

    }

    /**
     * 服务端解密用户信息接口
     * 获取unionId
     */
    public void getUserInfo() {
        String signature = getPara("signature");
        String rawData = getPara("rawData");
        String encryptedData = getPara("encryptedData");
        String iv = getPara("iv");

        // 参数空校验 不做演示
        // 利用 appId 与 accessToken 建立关联，支持多账户
        IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();
        String sessionId = getHeader("wxa-sessionid");
        if (StrKit.isBlank(sessionId)) {
            Kv data = Kv.by("errcode", 500)
                    .set("errmsg", "wxa_session Header is blank");
            renderJson(data);
            return;
        }
        String sessionJson = accessTokenCache.get("wxa:session:" + sessionId);
        if (StrKit.isBlank(sessionJson)) {
            Kv data = Kv.by("errcode", 500)
                    .set("errmsg", "wxa_session sessionJson is blank");
            renderJson(data);
            return;
        }
        ApiResult sessionResult = ApiResult.create(sessionJson);
        // 获取sessionKey
        String sessionKey = sessionResult.get("session_key");
        if (StrKit.isBlank(sessionKey)) {
            Kv data = Kv.by("errcode", 500)
                    .set("errmsg", "sessionKey is blank");
            renderJson(data);
            return;
        }
        // 用户信息校验
        boolean check = wxaUserApi.checkUserInfo(sessionKey, rawData, signature);
        if (!check) {
            Kv data = Kv.by("errcode", 500)
                    .set("errmsg", "UserInfo check fail");
            renderJson(data);
            return;
        }
        // 服务端解密用户信息
        ApiResult apiResult = wxaUserApi.getUserInfo(sessionKey, encryptedData, iv);
        if (!apiResult.isSucceed()) {
            renderJson(apiResult.getJson());
            return;
        }
        // 如果开发者拥有多个移动应用、网站应用、和公众帐号（包括小程序），可通过unionid来区分用户的唯一性
        // 同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。
        String unionId = apiResult.get("unionId");
        renderJson(apiResult);
    }
    public void updateUserInfo(){
        JSONObject userInfoJson=JSONObject.parseObject(getPara("userInfo"));
        String token=getPara("token");
        if(userInfoJson==null||token==null){
            renderJson(Response.responseJson(1,"更新失败"));
        }else{
            User user=UserService.getService().getUserByToken(token);
           // user.setNickname(userInfoJson.getString("nickName"));
           // user.setGender(userInfoJson.getString("gender"));
            user.setAvatar(userInfoJson.getString("avatarUrl"));
            user.update();
            renderJson(Response.responseJson(0,"登录成功",user));
        }
    }

}
