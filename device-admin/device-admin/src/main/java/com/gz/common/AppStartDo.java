package com.gz.common;

import com.gz.common.model.Parameter;

public class AppStartDo {
    /**
     * 检查user表是否为空，如果为空自动加入一条记录，用户admin
     */
    public static void CheckUser(){
        //initCheckUser();
    }

    /**
     * 检查参数表的所有单号，如果没有设置，增加默认设置
     */
    public static void CheckAllBillNo(){
        ParameterService.initCheckAllBillNo();

    }
    /**
     * 缓存的单据号保存到数据库
     */
    public static void SaveAllBillNo(String billType,String billNo){
      Parameter parameter =  ParameterService.findFirstByCode(billType);
      parameter.setValue(billNo);
      parameter.update();
    }

}
