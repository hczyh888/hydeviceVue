package com.gz.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gz.common.CompanyService;
import com.gz.common.Constant;
import com.gz.common.DeptService;
import com.gz.common.DeviceClassService;
import com.gz.common.ModuleService;
import com.gz.common.RoleService;
import com.gz.common.model.Company;
import com.gz.common.model.Dept;
import com.gz.common.model.DeviceClass;
import com.gz.common.model.Lanmu;
import com.gz.common.model.Module;
import com.gz.common.model.Role;
import com.gz.utils.Response;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

public class SystemController extends Controller {
    //private JSONArray jsonArray;

    public void getModules(){
        renderJson(Response.responseJson(0,"成功", ModuleService.getService().getModuleList()));
    }
    public void getModuleCmb(){
        renderJson(Response.responseJson(0,"成功", ModuleService.getService().getModuleCmb()));
    }
    public void saveModule(){
        Module module = getModel(Module.class,"");
        if(module.getId()==null){
            module.save();
        }else{
            module.update();
        }
        renderJson(Response.responseJson(0,"成功",module));
    }
    public void delModule(){
        int moduleId = getParaToInt("id",0);
        if (Module.dao.deleteById(moduleId)) {
            renderJson(Response.responseJson(0,"成功"));
        }else {
            renderJson(Response.responseJson(1, "失败"));
        }
    }
    public void getRole(){
        renderJson(Response.responseJson(0,"成功", RoleService.getService().getRoleList()));
    }
    public void saveRole(){
        Role role = getModel(Role.class,"");
        if(role.getId() == null){
            role.save();
        }else{
            role.update();
        }
        renderJson(Response.responseJson(0,"成功",role));
    }
    public void delRole(){
        int roleId = getParaToInt("id",0);
        if(Role.dao.deleteById(roleId)){
            renderJson(Response.responseJson(0,"成功"));
        }else{
            renderJson(Response.responseJson(1,"失败"));
        }
    }
    public void getPermission(){
        int roleId = getParaToInt("roleId",1);
        //step 1.获取module，step 2.获取module下的主菜单，step 3.获取各主菜单下的功能菜单
        List<Module> modules = ModuleService.getService().getModuleList();
        for(int i=0;i<modules.size();i++){

            List<Lanmu> lanmus = Lanmu.dao.find(" select id,name from tb_lanmu where lanmu_type=0 and  module_id=? order by sort_num ",modules.get(i).getId());

            for(int j=0;j<lanmus.size();j++){

                List<Lanmu> subLanmus = Lanmu.dao.find(" select id,name,"+
                        " (case when id in(select lanmu_id from tb_role_lanmu where role_id="+roleId+") then true else false end) checked " +
                        " from tb_lanmu where up_levelId=? order by sort_num ",lanmus.get(j).getId());
                for(int k=0;k<subLanmus.size();k++){
                    Boolean b = subLanmus.get(k).getInt("checked")==0?false:true;
                    subLanmus.get(k).put("checked",b);
                }
                lanmus.get(j).put("subFunc",subLanmus);
            }

            modules.get(i).put("subMenu",lanmus);
        }
        renderJson(Response.responseJson(0,"成功",modules));
    }
    @Before(Tx.class)
    public void savePermission(){
        int roleId = getParaToInt("roleId");
        int count_checked = 0;
        //先删后插入
        Db.update("delete from tb_role_lanmu where role_id=?",roleId);
        String data = getPara("permissions");

        StringBuffer sb=new StringBuffer();
        sb.append(" insert into tb_role_lanmu(role_id,lanmu_id) select * from (");

        if(data == null){
            renderJson(Response.responseJson(1,"数据为空"));
        }else {
            JSONArray jsonArray = JSON.parseArray(data);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject permissionItem = (JSONObject) jsonArray.get(i);
                JSONArray subMenu = JSON.parseArray(permissionItem.getString ("subMenu"));
                for(int j=0;j<subMenu.size();j++){
                    JSONObject subMenuItem = (JSONObject) subMenu.get(j);
                    JSONArray subFunc = JSON.parseArray(subMenuItem.getString("subFunc"));

                    for(int k=0;k<subFunc.size();k++){
                        JSONObject subFuncItem = (JSONObject) subFunc.get(k);
                        if(subFuncItem.getBoolean("checked")){
                                count_checked ++;
                                sb.append(" select "+roleId+","+subFuncItem.getString("id") +" union ");
                            }
                    }
                    if(count_checked>0){ //若该菜单下有子菜单被选中，则加入
                        sb.append(" select "+roleId+","+subMenuItem.getString("id")+" union ");
                        count_checked =0;
                    }
                }
            }
            sb.delete(sb.lastIndexOf("union"),sb.length());
            sb.append(" ) as tt");
            Db.update(sb.toString());
            renderJson(Response.responseJson(0,"成功！"));
        }
    }
    public void saveDept(){
        Dept dept = getModel(Dept.class,"");
        if(dept.getId()==null){
            dept.save();
        }else{
            dept.update();
        }
        renderJson(Response.responseJson(0,"成功",dept));
    }
    public void delDept(){
        Dept dept = getBean(Dept.class,"");
        dept.delete();
        renderJson(Response.responseJson(0,"成功"));
    }
    public void updateDept(){
       Dept dept = getBean(Dept.class,"");
       dept.update();
       renderJson(Response.responseJson(0,"成功"));
    }
    public void getDepts(){
        renderJson(Response.responseJson(0,"成功", DeptService.getService().getDeptList(getParaToInt("deletedTag"))));
    }
    public void getDeptCmd(){
        renderJson(Response.responseJson(0,"成功", DeptService.getService().getCascaderDepts()));
    }
    public void getDeviceClassCmd(){
        renderJson(Response.responseJson(0,"成功", DeviceClassService.getService().getCascaderDeviceClass()));
    }
    public void saveDeviceClass(){
        DeviceClass deviceClass = getModel(DeviceClass.class,"");
        if(deviceClass.getId()==null){
            deviceClass.save();
        }else{
            deviceClass.update();
        }
        renderJson(Response.responseJson(0,"成功",deviceClass));
    }
    public void getDeviceClasses(){
        renderJson(Response.responseJson(0,"成功", DeviceClassService.getService().getDeviceClassList()));
    }
    public void delDeviceClass(){
        DeviceClass deviceClass = getBean(DeviceClass.class,"");
        deviceClass.delete();
        renderJson(Response.responseJson(0,"成功"));
    }
    public void getCompany(){
        renderJson(Response.responseJson(0,"成功", CompanyService.getService().getCompany()));
    }
    public void saveCompany(){
        Company company = getModel(Company.class,"");
        if(company.getId()==null){
            company.save();
        }else{
            company.update();
        }
        company.put("logo_tmp",Constant.FILE_PATH+company.getLogo());
        renderJson(Response.responseJson(0,"成功",company));
    }



}
