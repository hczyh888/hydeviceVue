package com.gz.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gz.common.AppStartDo;
import com.gz.common.Constant;
import com.gz.common.ParameterService;
import com.gz.common.StockInService;
import com.gz.common.StockService;
import com.gz.common.WarehouseService;
import com.gz.common.model.StockIn;
import com.gz.common.model.StockInDetail;
import com.gz.common.model.Warehouse;
import com.gz.utils.Response;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

import hyevent.StockInEvent;
import net.dreamlu.event.EventKit;

public class WarehouseController extends Controller{
    /**
     * 库房
     */
    public void saveWarehouse(){
        Warehouse warehouse=getModel(Warehouse.class,"");
        if(warehouse.getId()!=null){
            warehouse.update();
        }else{
            warehouse.save();
        }
        renderJson(Response.responseJson(0,"成功"));
    }
    public void delWarehouse(){
        Warehouse warehouse=WarehouseService.getService().getWarehouse(getParaToInt("id",0));
        if(warehouse.delete()){
            renderJson(Response.responseJson(0,"删除成功"));
        }else{
            renderJson(Response.responseJson(1,"删除失败"));
        }
    }
    public void getWarehouseList(){
        renderJson(Response.responseJson(0,"成功", WarehouseService.getService().getWarehouseList()));

    }
    /**
     * 获取单据编号
     */
    public void getBillNo(){
        String billType = getPara("billType","RKDH");
        String no= ParameterService.getService().getNewBillNo(billType);
        renderJson(Response.responseJson(0,"成功",Kv.create().set("billNo",no)));
     }
     /**
      * 保存入库单主表和明细表
      */
     @Before(Tx.class)
     public void saveStockIn(){
         String act=getPara("action");
         //入库主表
         StockIn stockIn= (StockIn)JSONObject.parseObject(getPara("stockIn"),StockIn.class);
         if(StrKit.notNull(stockIn)){
             if(StrKit.notNull(stockIn.getId())){
                 stockIn.update();
            }else{
                CacheKit.put(Constant.BILL_CACHE,Constant.BILL_RKDH,stockIn.getBillNo());
                AppStartDo.SaveAllBillNo(Constant.BILL_RKDH,stockIn.getBillNo());
                stockIn.save();
             }
         }
         //入库明细表
         List<StockInDetail> stockInDetailLst = new ArrayList<StockInDetail>();
         JSONArray stockInDetails = JSONArray.parseArray(getPara("stockInDetail"));
         switch(act){
             case "add": //新增
                if(StrKit.notNull(stockInDetails)){
                    for(int i=0;i<stockInDetails.size();i++){
                        JSONObject jsonObject = (JSONObject)stockInDetails.get(i);
                        StockInDetail stockInDetail = new StockInDetail();
                        stockInDetail.setBillNo(stockIn.getBillNo());
                        stockInDetail.setDeviceId(jsonObject.getInteger("id"));
                        stockInDetail.setQuantity(jsonObject.getDouble("quantity"));
                        stockInDetail.setPrice(jsonObject.getDouble("price"));
                        stockInDetail.setAmount(jsonObject.getDouble("amount"));
                        stockInDetailLst.add(stockInDetail);
                    }
                    Db.batchSave(stockInDetailLst, stockInDetailLst.size());
                }
                break;
             case "edit": //编辑
                Db.delete("delete from tb_stock_in_detail where billNo=?",stockIn.getBillNo());
                if(StrKit.notNull(stockInDetails)){
                    for(int i=0;i<stockInDetails.size();i++){
                        JSONObject jsonObject = (JSONObject)stockInDetails.get(i);
                        StockInDetail stockInDetail = new StockInDetail();
                        stockInDetail.setBillNo(stockIn.getBillNo());
                        stockInDetail.setDeviceId(jsonObject.getInteger("id"));
                        stockInDetail.setQuantity(jsonObject.getDouble("quantity"));
                        stockInDetail.setPrice(jsonObject.getDouble("price"));
                        stockInDetail.setAmount(jsonObject.getDouble("amount"));
                        stockInDetailLst.add(stockInDetail);
                    }
                    Db.batchSave(stockInDetailLst, stockInDetailLst.size());
                }
                break;
                case "receive": //收货
                if(StrKit.notNull(stockInDetails)){
                    for(int i=0;i<stockInDetails.size();i++){
                        JSONObject jsonObject = (JSONObject)stockInDetails.get(i);
                        StockInDetail stockInDetail = new StockInDetail();
                        stockInDetail.setId(jsonObject.getLong("id"));
                        stockInDetail.setReceivedNum(jsonObject.getDouble("receivedNum") + jsonObject.getDouble("receiveNum"));
                        stockInDetail.setReceiveNum(jsonObject.getDouble("receiveNum"));
                        stockInDetail.setReceiveDate(jsonObject.getDate("receiveDate"));
                        stockInDetailLst.add(stockInDetail);
                    }
                    Db.batchUpdate(stockInDetailLst, stockInDetailLst.size());
                    //发送stockinEvent事件，更改入库单状态,办理入库
                    JSONObject jsonObject2 = (JSONObject)stockInDetails.get(0);
                    Kv kv= Kv.by("flag","receive").set("billNo", jsonObject2.getString("billNo"));
                    EventKit.post(new StockInEvent(kv));
                }
                break;
             default:
                break;
         }
         renderJson(Response.responseJson(0,"保存成功"));
     }
     /**
      * 根据入库单编号获取入库单信息
      */
     public void getStockIn(){
         StockIn stockIn = StockInService.getService().getStockInByBillNo(getPara("billNo"));
         renderJson(Response.responseJson(0,"OK",stockIn));
     }
     /**
      * 入库历史列表
      */
     public void getStockInList(){
         int pageNumber = getParaToInt("pageNum", 1);
         int pageSize = getParaToInt("pageSize", 10);
         renderJson(Response.responseJson(0,"ok",StockInService.getService().getStockInList(pageNumber, pageSize)));
        }
        /**
         * 库存列表
         */
    public void getStockList(){
        int pageNumber = getParaToInt("pageNum",1);
        int pageSize = getParaToInt("pageSize",10);
        renderJson(Response.responseJson(0,"ok",StockService.getService().getStockList(pageNumber, pageSize)));
     }


}