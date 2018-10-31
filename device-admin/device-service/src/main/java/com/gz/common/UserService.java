package com.gz.common;

import com.gz.common.model.Role;
import com.gz.common.model.User;
import com.gz.utils.SecurityUtil;
import com.gz.utils.StringUtil;
import com.jfinal.plugin.activerecord.Page;

import java.util.*;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * Created by gongzhen on 2018/6/2.
 */
public class UserService {
    private static UserService userService;

    private UserService() {
    }

    public static UserService getService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
    public void createToken(User user){
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.DATE, 60);//2月
       /* user.setTokenExpiretime(cd.getTime());
        user.setToken(SecurityUtil.getMD5(user.getSalt()+user.getTokenExpiretime()));*/
    };
    public String createSalt(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return SecurityUtil.getMD5(sb.toString());
    }
    public List<User> getList(){
        return  User.dao.find("select * from tb_user where status!='administrator'");
    }
    public Page<User> getPage(int pageNum, int pageSize,String role){
        return User.dao.paginate(pageNum, pageSize, "select *", "from tb_user where role_id = (SELECT id from tb_role where role=?)",role);
    }
    public Page<User> getPageList(int pageNum,int pageSize){
        Page<User> pageList = User.dao.paginate(pageNum,pageSize,"select *","from tb_user" );
        for(User user:pageList.getList()){
            user.put("avatar_tmp",Constant.FILE_PATH+user.getAvatar());
        }
        return pageList;
    }
    public User getUserByAccount(String account){
        return User.dao.findFirst("select * from tb_user where userAccount=? or mobile=? ",account,account);
    }
    public User getUserByUserName(String account){
        return User.dao.findFirst("select * from tb_user where username=? ",account);
    }
    public User getUserByUserEmail(String account){
        return User.dao.findFirst("select * from tb_user where email=? ",account);
    }
    public User getUserByUserMobile(String account){
        return User.dao.findFirst("select * from tb_user where mobile=? ",account);
    }
    public Map<String,Object> updateUser(User user){
        Map<String,Object> data=new HashMap<>();
        data.put("code",1);
        if(user==null){
            data.put("msg","user为空");
        }else if(user.getId()!=null&&user.getId()!=0&&user.update()){
            data.put("code",0);
            data.put("msg","用户信息更新成功");
        }else{

            /*if(!StringUtil.isEmpty(user.getEmail())&&getUserByUserEmail(user.getEmail())!=null){
                data.put("msg","邮箱已注册");
            }else if(!StringUtil.isEmpty(user.getMobile())&&getUserByUserMobile(user.getMobile())!=null){
                data.put("msg","手机号已注册");
            }else if(StringUtil.isEmpty(user.getUsername())){
                data.put("msg","用户名不能为空");
            }else if(getUserByUserName(user.getUsername())!=null){
                data.put("msg","用户名已存在");*/
          //  }else
                if(StringUtil.isEmpty(user.getPassword())){
                data.put("msg","用户名密码为空");
            }else if(user.save()){
                data.put("code",0);
                data.put("msg","用户信息创建成功");
            }else{
                data.put("msg","用户信息创建失败");
            }

        }

        return data;
    }
    public User getUserByToken(String token){
        return User.dao.findFirst("SELECT * FROM tb_user where `token` =?",token);
    };
    public User getUserByOpenId(String openId){
        return User.dao.findFirst("SELECT * FROM tb_user where `openId` =?",openId);
    };
    public Role getRole(String role){
        return Role.dao.findFirst("SELECT * FROM tb_role where role =?",role);
    };
    public Role getRole(int userId){
        return Role.dao.findFirst("SELECT tb_role.* from tb_role right JOIN tb_user on tb_user.role_id=tb_role.id where tb_user.id=?",userId);

    }
    public List<User> getListByRole(String role){
        return User.dao.find("SELECT * from tb_role where role=?",role);
    }
}
