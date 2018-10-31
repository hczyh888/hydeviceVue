package com.gz.controller;

import com.gz.common.DeviceService;
import com.gz.common.model.Device;
import com.gz.utils.Response;
import com.jfinal.core.Controller;

public class DeviceController extends Controller {
    public void saveDevice(){
        Device device=getModel(Device.class,"");
        if(device.getId()==null){
            device.save();
        }else{
            device.update();
        }
        renderJson(Response.responseJson(0,"成功"));
    }
    public void getDevice(){
        int pageNum = getParaToInt("pageNum",1);
        int pageSize = getParaToInt("pageSize",10);
        renderJson(Response.responseJson(0,"成功", DeviceService.getService().getDevicePageList(pageNum,pageSize)));
    }
    public void getDevices(){
        renderJson(Response.responseJson(0,"成功", DeviceService.getService().getDeviceList()));

    }
    
}
