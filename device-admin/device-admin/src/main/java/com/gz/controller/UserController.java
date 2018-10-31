package com.gz.controller;

import com.gz.common.UserService;
import com.gz.common.model.User;
import com.gz.utils.MD5;
import com.gz.utils.Response;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by gongzhen on 2018/6/13.
 */
public class UserController extends Controller{
    public void saveUser(){
        User user = getModel(User.class,"");
        if(user.getId()!=null){
            if(user.update()){
                renderJson(Response.responseJson(0,"更新成功"));
            }else{
                renderJson(Response.responseJson(1,"更新失败"));
            }

        }else {
            String password = user.getPassword();
            String salt = MD5.getRandomSalt(5);
            String pwd = "";
            try {
                pwd = MD5.getEncryptedPwd(password, salt);
            } catch (Exception ex) {
                renderJson(Response.responseJson(1, ex.getMessage()));
            }
            user.setSalt(salt);
            user.setPassword(pwd);
            if (user.save()) {
                renderJson(Response.responseJson(0, "保存成功"));
            } else {
                renderJson(Response.responseJson(1, "保存失败"));
            }
        }

    }
    /**
     * 普通用户列表
     */
    public void getNormalUserList(){
        Page<User> userPage= UserService.getService().getPage(getParaToInt("pageNum"),getParaToInt("pageSize"),"user");
        renderJson(Response.responseJson(0,"请求成功",userPage));
    }
    /**
     * ALl用户列表
     */
    public void getUserList(){
        Page<User> userPage= UserService.getService().getPageList(getParaToInt("pageNum"),getParaToInt("pageSize"));
        renderJson(Response.responseJson(0,"请求成功",userPage));
    }

}
