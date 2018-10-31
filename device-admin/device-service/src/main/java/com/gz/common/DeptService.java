package com.gz.common;

import com.gz.common.model.Dept;
import com.gz.common.objects.CascaderObject;
import com.jfinal.plugin.activerecord.Db;

import java.util.*;

public class DeptService {
    private static DeptService deptService;
    private DeptService(){

    }
    public static DeptService getService(){
        if(deptService==null){
            deptService = new DeptService();
        }
        return deptService;
    }

    /**
     * 按tree的样式显示
     * @param deletedTag
     * @return
     */
    public List<Dept>  getDeptList(int deletedTag){
        List<Dept> deptList = Dept.dao.find("select * from tb_dept where deleted=? order by pid,sort_num",deletedTag);
        List<Dept> parentList = Dept.dao.find("select * from tb_dept where pid=0 order by sort_num");
        List<Dept> deptChilds= new ArrayList<>();
        List<Dept> rstList = new ArrayList<>();
        for(Dept d1:parentList){
            rstList.add(d1);
            findChildDept(d1.getId(),deptList,deptChilds);

        }
        rstList.addAll(deptChilds);
        return rstList;
    }
    private void findChildDept(long pid,List<Dept> deptList,List<Dept> deptChilds){
        for(Dept d:deptList){
            if(pid==d.getPid()){
                deptChilds.add(d);
                findChildDept(d.getId(),deptList,deptChilds);
            }
        }
    }

    /**
     * 1.获取分类好的层级关系，按顺序排好的 2.循环，根据level判断，目前只判断三层
     * @return
     */
    public List<CascaderObject> getCascaderDepts(){
        List<CascaderObject> Lst0 = new ArrayList<>();
        List<CascaderObject> Lst1 = new ArrayList<>();
        List<CascaderObject> Lst2 = new ArrayList<>();
        List<CascaderObject> Lst3 = new ArrayList<>();
        CascaderObject sub1 = new CascaderObject();
        CascaderObject sub2 = new CascaderObject();
        CascaderObject sub3 = new CascaderObject();
        List<Dept> deptList = getDeptList(0);
        for(Dept dept:deptList){
            if(dept.getLevel()==1){ //从第一层开始

                if(Lst3.size()>0){//先解决第三层
                    sub2.setChildren(Lst3);
                    sub1.setChildren(Lst2);
                    Lst0.add(sub1.myclone());

                    Lst1.clear();
                    Lst2.clear();
                    Lst3.clear();
                }else if(Lst2.size()>0){  //有下一级的情况
                    sub1.setChildren(Lst2);
                    Lst0.add(sub1.myclone());

                    Lst1.clear();
                    Lst2.clear();
                }
                if(Lst1.size()>0){
                    sub1.setValue(dept.getId().toString());
                    sub1.setLabel(dept.getName());
                    Lst1.clear();
                    Lst0.add(sub1.myclone());
                }else{
                    sub1.setValue(dept.getId().toString());
                    sub1.setLabel(dept.getName());
                    Lst1.add(sub1);
                }
            }else if(dept.getLevel()==2){
                sub2.setValue(dept.getId().toString());
                sub2.setLabel(dept.getName());
                Lst2.add(sub2);
            }else if(dept.getLevel()==3){
                sub3.setValue(dept.getId().toString());
                sub3.setLabel(dept.getName());
                Lst3.add(sub3);
            }
        }
        if(Lst1.size()>0){
            Lst0.add(Lst1.get(0));
        }
        return Lst0;

    }
    public void recycleDept(int id){
        Db.update("update tb_dept set deleted=1 where id=?",id);
    }
}
