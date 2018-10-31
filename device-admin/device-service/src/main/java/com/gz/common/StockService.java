package com.gz.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gz.common.model.Stock;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class StockService {
    private static StockService stockService;
    private StockService(){}
    public static StockService getService(){
        if(stockService == null){
            stockService = new StockService();
        }
        return stockService;
    }
    public Page<Stock> getStockList(int pageNumber,int pageSize){
        return Stock.dao.paginate(pageNumber, pageSize, " select s.*,d.code,d.name,d.unit,d.model,d.initStock",
                                " from tb_stock s left join tb_device d on s.deviceId=d.id ");

    }
    public void saveStock(String billNo){
        //1.完成入库
        List<Stock> updateStockLst = new ArrayList<Stock>();
        List<Stock> saveStockLst = new ArrayList<Stock>();
        //库存表的所有deviceId
        List<Stock> deviceIds=Stock.dao.find("select id, deviceId,quantity from tb_stock");
        int len= deviceIds.size();
        String[] idArray=new String[len];
        String[] deviceIdArray=new String[len];
        String[] quantityArray=new String[len];
        int i=0;
        for(Stock item : deviceIds){
            idArray[i] = item.getStr("id");
            deviceIdArray[i] = item.getStr("deviceId");
            quantityArray[i] = item.getStr("quantity");
            i++;
        }
        //遍历该billNo的明细
        List<Record> stockInDetails = Db.find("select deviceId,sum(receiveNum) sumReceiveNum from tb_stock_in_detail "+
                                                            " where receiveNum>0 and billNo=? group by deviceId ",billNo);
        for(Record item2 : stockInDetails){
            Stock stock = new Stock();
            int index = Arrays.binarySearch(deviceIdArray, item2.getStr("deviceId"));
           if(index>=0){ //update
                stock.setId(Long.valueOf(idArray[index]));
                stock.setDeviceId(item2.getInt("deviceId") );
                stock.setQuantity(item2.getDouble("sumReceiveNum")+Double.valueOf(quantityArray[index]));
                updateStockLst.add(stock);
           }else{ //save
                stock.setDeviceId(item2.getInt("deviceId"));
                stock.setQuantity(item2.getDouble("sumReceiveNum"));
                saveStockLst.add(stock);
           }
        }
        if (saveStockLst.size() >0) {
            Db.batchSave(saveStockLst,saveStockLst.size());
        }
        if(updateStockLst.size()>0){
            Db.batchUpdate(updateStockLst,updateStockLst.size());
        }
        
    }   
}