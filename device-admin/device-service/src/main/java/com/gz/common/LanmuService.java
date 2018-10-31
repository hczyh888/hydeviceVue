package com.gz.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gz.common.model.Lanmu;

/**
 * Created by gongzhen on 2017/7/12.
 */
public class LanmuService {
    private static LanmuService lanmuService;

    private LanmuService() {
    }

    public static LanmuService getService() {
        if (lanmuService == null) {
            lanmuService = new LanmuService();
        }
        return lanmuService;
    }
    public List<Lanmu> getAllLanmuList(){
        return Lanmu.dao.find("SELECT * FROM tb_lanmu WHERE is_systerm!=1 order by sort_num asc");
    }
    public List<Lanmu> getLanmuList(int roleId){
        return Lanmu.dao.find("SELECT * FROM tb_lanmu where id in(SELECT lanmu_id from tb_role_lanmu where role_id=?) order by sort_num asc",roleId);
    }
    public  List<Map<String,Object> >  getLanmuMenus(int roleId){
        List<Lanmu> lList= Lanmu.dao.find("SELECT * FROM tb_lanmu where id in(SELECT lanmu_id from tb_role_lanmu where role_id=?) order by sort_num asc",roleId);
        List<Map<String,Object> > rows=new ArrayList<>();
        for(Lanmu lanmu:lList) {
            Map<String,Object> row=new HashMap<>();
            row.put("title",lanmu.getName());
            row.put("icon",lanmu.getFontIcon());
            row.put("index",lanmu.getRoute());
            rows.add(row);
        }
        return rows;
    }
    public List<Lanmu> getAllLanmu(int roleId,int moduleId){
        List<Lanmu> lanmus=new ArrayList<>();
        Object[] paras = new Object[2];
        paras[0] = roleId;
        List<Lanmu> lanmuChilds =getLanmuListByRoleId(roleId);

        StringBuffer sb = new StringBuffer();
        sb.append(" select *, ");
        sb.append(" (select name from tb_module where id = module_id) as module_name ");
        sb.append(" FROM tb_lanmu where id in (SELECT lanmu_id from tb_role_lanmu " );
        sb.append("where up_levelId=0 and role_id=? ");
        if (moduleId >0){
            sb.append(" and module_id=? ) ");
            paras[1] = moduleId;
        }else{
            sb.append(" )");
            paras = Arrays.copyOf(paras,paras.length-1);
        }
        sb.append(" order by sort_num desc,id asc");
        List<Lanmu> lanmu_groups=Lanmu.dao.find(sb.toString(),paras);
        for(Lanmu lanmu1:lanmu_groups){
            lanmus.add(lanmu1);
            getLowLanmus(lanmu1.getId(),lanmuChilds,lanmus);
        }
        return lanmus;
    }
    public void getLowLanmus(int pid,List<Lanmu> lanmuChilds,List<Lanmu> lanmus){
        for(Lanmu lanmu:lanmuChilds){
            if(lanmu.getUpLevelid() == pid){
                lanmus.add(lanmu);
                getLowLanmus(lanmu.getId(),lanmuChilds,lanmus);
            }
        }

    }
    public void getSubLanmus(int pid,List<Lanmu> lanmuChilds,List<Map<String,Object>> subLanmu){
        for(Lanmu lanmu:lanmuChilds){
            if(lanmu.getUpLevelid() == pid){
                Map<String,Object> subTemp=new HashMap<>();
                subTemp.put("title",lanmu.getName());
                subTemp.put("icon",lanmu.getFontIcon()!=null?lanmu.getFontIcon():"");
                subTemp.put("index",lanmu.getRoute());
                subTemp.put("lanmuId",lanmu.getId());
                subLanmu.add(subTemp);
                getSubLanmus(lanmu.getId(),lanmuChilds,subLanmu);
            }
        }

    }
    public List<Map<String,Object>> getMenu(int roleId,int moduleId){
        List<Map<String,Object>> menus=new ArrayList<>();
        List<Lanmu> lanmuChilds =getLanmuListByRoleId(roleId);

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM tb_lanmu where id in (SELECT lanmu_id from tb_role_lanmu where role_id=?)");
        Object[] paras = new Object[2];
        paras[0] = roleId;
        if(moduleId>0){
            sb.append(" and module_id=? ");
            paras[1] = moduleId;
        }else{
            paras = Arrays.copyOf(paras,paras.length-1);
        }
        sb.append(" and up_levelId=0  order by sort_num asc ");
        List<Lanmu> lanmu_groups=Lanmu.dao.find(sb.toString(),paras);

        for(Lanmu lanmu1:lanmu_groups){
            Map<String,Object> temp1=new HashMap<>();
            List<Map<String,Object>> subLanmu=new ArrayList<>();
            temp1.put("title",lanmu1.getName());
            temp1.put("icon",lanmu1.getFontIcon()!=null?lanmu1.getFontIcon():"");
            temp1.put("index",lanmu1.getRoute());
            temp1.put("lanmuId",lanmu1.getId());
            getSubLanmus(lanmu1.getId(),lanmuChilds,subLanmu);
            if(subLanmu.size()>0)
            temp1.put("subs",subLanmu);
            menus.add(temp1);
        }
        return menus;
    }

    public Lanmu getLanmu(Integer lanmuId) {
        return Lanmu.dao.findById(lanmuId);
    }
    public List<Lanmu> getLanmuListByRoleId(int roleId){
        return Lanmu.dao.find("select *," +
                        " (select name from tb_module where id = module_id) as module_name " +
                        " from tb_lanmu where id in (SELECT lanmu_id from tb_role_lanmu where  role_id=?) and  up_levelId!=0 order by sort_num ,id ",
                roleId);
    }
}
