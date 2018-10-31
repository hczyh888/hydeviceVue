package com.gz.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gz.common.model.StockIn;
import com.gz.common.model.StockInDetail;
import com.gz.utils.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class StockInService{
    private static StockInService stockInService;
    private StockInService(){}
    public static StockInService  getService(){
        if(stockInService == null){
            stockInService = new StockInService();
        }
        return stockInService;
    }
    /**
     * 依据billNO获取入库单头和明细
     * @param billNo
     * @return
     */
    public StockIn getStockInByBillNo(String billNo){
        StockIn stockIn = StockIn.dao.findFirst("select * from tb_stock_in where billNo=?",billNo);
        List<StockInDetail> details= StockInDetail.dao.find("select detail.*,d.name deviceName,d.model deviceModel,d.unit deviceUnit "+
                                    " from tb_stock_in_detail detail left join tb_device d on detail.deviceId=d.id where detail.billNo=?",billNo);
        stockIn.put("detail",details);
        return stockIn;
    }
    /**
     * 带出主表和对应的明细
     * @param pageNumber
     * @param pageSize
     * @return pageList
     */
    public Page<StockIn> getStockInList(int pageNumber,int pageSize){
        Page<StockIn> pageStockIn =  StockIn.dao.paginate(pageNumber, pageSize,"select s.*,w.name warehouseName ",
                                "from tb_stock_in s left join tb_warehouse w on s.warehouseId = w.id  order by billDate desc");

        ArrayList<String> billNoLst = new  ArrayList<>();
        for(StockIn si:pageStockIn.getList()){
            billNoLst.add("'"+si.getBillNo()+"'");
        }
        String s_str =  StringUtil.arrayToString(billNoLst,",");
        List<StockInDetail> rst =null;
        List<StockInDetail> stockInDetails = StockInDetail.dao.find("select detail.*,d.name deviceName,d.code deviceCode from tb_stock_in_detail detail "+
                                                " left join tb_device d on d.id = detail.deviceId "+                                            
                                                " where detail.billNo in ("+s_str+")");
        for(StockIn si:pageStockIn.getList()){
            rst = stockInDetails.stream().filter((StockInDetail d)->(si.getBillNo().equals(d.getBillNo())))
                            .collect(Collectors.toList());
            si.put("detail", rst);
        }
        return pageStockIn;
    }
    public void updateStatus(String billNo){
        int status=1;
        Record record = Db.findFirst("select sum(quantity) sumQuantity,sum(receivedNum) sumReceivedNum from tb_stock_in_detail where billNo=?",billNo);
        double  dd=record.getDouble("sumQuantity")-record.getDouble("sumReceivedNum");
        if(dd==0){status=2;}//已全部收货
        if((record.getDouble("sumReceivedNum")>0)&&(dd>0)){status=3;}//已部分收货
        if(dd<0){status=4;}//收货异常
        StockIn stockIn = StockIn.dao.findFirst("select * from tb_stock_in where billNo=?",billNo);
        stockIn.setStatus(status);
        stockIn.update();
    }
}
