package com.gz.common;

import java.util.ArrayList;
import java.util.List;

import com.gz.common.model.DeviceClass;
import com.gz.common.objects.CascaderObject;
import com.jfinal.plugin.activerecord.Page;

public class DeviceClassService {
    private static DeviceClassService deviceClassService;
    private DeviceClassService(){}
    public static DeviceClassService getService(){
        if(deviceClassService ==null){
            deviceClassService = new DeviceClassService();
        }
        return deviceClassService;
    }

    public List<DeviceClass>  getDeviceClassList(){
        List<DeviceClass> deviceClassesList = DeviceClass.dao.find("select * from tb_device_class order by pid,sort_num");
        List<DeviceClass> parentList = DeviceClass.dao.find("select * from tb_device_class where pid=0 order by sort_num");
        List<DeviceClass> rstList = new ArrayList<>();
        for(DeviceClass d1:parentList){
            rstList.add(d1);
            List<DeviceClass> deviceClassesChilds= new ArrayList<>();
            findChildDeviceClass(d1.getId(),deviceClassesList,deviceClassesChilds);
            rstList.addAll(deviceClassesChilds);
        }
        return rstList;
    }
    private void findChildDeviceClass(long pid,List<DeviceClass> deviceClassesList,List<DeviceClass> deviceClassesChilds){
        for(DeviceClass d:deviceClassesList){
            if(pid==d.getPid()){
                deviceClassesChilds.add(d);
                findChildDeviceClass(d.getId(),deviceClassesList,deviceClassesChilds);
            }
        }
    }
    public Page<DeviceClass> getDeviceClassPageList(int pageNum,int pageSize){
       return  DeviceClass.dao.paginate(pageNum,pageSize,"select *,(select className from tb_device_class aa where aa.id=bb.pid ) pName ",
               " from tb_device_class bb order by pid,sort_num");
    }
    /**
     * 1.获取分类好的层级关系，按顺序排好的 2.循环，根据level判断，目前只判断4层
     * @return
     */
    public List<CascaderObject> getCascaderDeviceClass(){
        List<CascaderObject> LstAll = new ArrayList<>();
        List<CascaderObject> Lst0 = new ArrayList<>();
        List<CascaderObject> Lst1 = new ArrayList<>();
        List<CascaderObject> Lst2 = new ArrayList<>();
        List<CascaderObject> Lst3 = new ArrayList<>();
        CascaderObject sub0 = new CascaderObject();
        CascaderObject sub1 = new CascaderObject();
        CascaderObject sub2 = new CascaderObject();
        CascaderObject sub3 = new CascaderObject();
        List<DeviceClass> deviceClassList = getDeviceClassList();
        for(DeviceClass deviceClass:deviceClassList){
            if(deviceClass.getLevel()==0){
                if(Lst0.size()>0){
                   LstAll.add(Lst0.get(0));

                    Lst0.clear();
                    Lst1.clear();
                }
                sub0.setValue(deviceClass.getId().toString());
                sub0.setLabel(deviceClass.getClassName());
                sub0.setChildren(null);
                Lst0.add(sub0.myclone());
            }
            if(deviceClass.getLevel()==1){ //从第一层开始
                if(Lst1.size()>0){

                    sub2.setChildren(Lst3);
                    if(Lst2.size()>0){
                        Lst2.set(0, sub2.myclone());
                    }
                    sub1.setChildren(Lst2);
                    Lst1.set(0, sub1.myclone());
                    sub0.setChildren(Lst1);
                    Lst0.set(0,sub0.myclone());

                    Lst1.clear();
                    Lst2.clear();
                    Lst3.clear();
                }
                sub1.setValue(deviceClass.getId().toString());
                sub1.setLabel(deviceClass.getClassName());
                sub1.setChildren(null);
                Lst1.add(sub1.myclone());
            }else if(deviceClass.getLevel()==2){
                if(Lst2.size()>0){
                    sub2.setChildren(Lst3);
                    Lst2.set(0,sub2.myclone());
                    sub1.setChildren(Lst2);
                    Lst1.set(0,sub1);

                    Lst2.clear();
                    Lst3.clear();
                }
                sub2.setValue(deviceClass.getId().toString());
                sub2.setLabel(deviceClass.getClassName());
                sub2.setChildren(null);
                Lst2.add(sub2.myclone());
            }else if(deviceClass.getLevel()==3){
                sub3.setValue(deviceClass.getId().toString());
                sub3.setLabel(deviceClass.getClassName());
                Lst3.add(sub3.myclone());
            }
        }
        if(Lst0.size()>0){ //最后一条
            sub2.setChildren(Lst3);
            sub1.setChildren(Lst2);
            Lst1.set(0,sub1);
            if(Lst0.get(0).getChildren().size() > 0){
                Lst1.add(0,Lst0.get(0).getChildren().get(0));
            }
            sub0.setChildren(Lst1);
            Lst0.set(0,sub0); //update sub0
            LstAll.add(Lst0.get(0));
        }
        return LstAll;

    }
}
