package com.gz.common;

import com.gz.common.model.Role;

import java.util.List;

public class RoleService {
    private static RoleService service;
    private RoleService(){
    }
     public static RoleService getService(){
         if(service == null){
             service = new RoleService();
         }
         return service;
     }
     public List<Role> getRoleList(){
         return Role.dao.find("select * from tb_role");
     }
}
