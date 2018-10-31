package com.gz.common;

import java.util.List;

import com.gz.common.model.Device;
import com.jfinal.plugin.activerecord.Page;

public class DeviceService {
    private static DeviceService deviceService;
    private DeviceService(){

    }
    public static DeviceService getService(){
        if(deviceService==null){
            deviceService = new DeviceService();
        }
        return deviceService;
    }
    public Page<Device> getDevicePageList(int pageNum,int pageSize){

        return Device.dao.paginate(pageNum,pageSize,"select d.*,dc.className"," from tb_device d left join tb_device_class dc on d.classId=dc.id ");
    }
    public List<Device> getDeviceList(){
        return Device.dao.find("select id,code,name,model,unit from tb_device ");
    }
}
