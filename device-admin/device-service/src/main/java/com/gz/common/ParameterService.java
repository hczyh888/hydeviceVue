package com.gz.common;

import java.util.Date;

import com.gz.common.model.Parameter;
import com.gz.utils.TimeUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

public class ParameterService implements Constant {
    private static ParameterService parameterService;
    private ParameterService(){}
    public static ParameterService getService(){
        if(parameterService==null){
            parameterService = new ParameterService();
        }
        return parameterService;
    }
    public static void initCheckAllBillNo(){
        //yyyymmdd001
		String no = TimeUtil.date2Str7(new Date())+"000";
		Parameter parameter;
		//入库单号
		parameter = findFirstByCode(Constant.BILL_RKDH);
		if(parameter ==null){
			insertInitBillNo(Constant.BILL_RKDH,no,Constant.RKDH_NAME);
			CacheKit.put(Constant.BILL_CACHE ,Constant.BILL_RKDH,Constant.BILL_RKDH+no);
		}else{
			CacheKit.put(Constant.BILL_CACHE ,Constant.BILL_RKDH,parameter.getValue());

		}
		//出库单号
		parameter=findFirstByCode(Constant.BILL_CKDH);
		if(parameter==null){
			insertInitBillNo(Constant.BILL_CKDH,no,Constant.CKDH_NAME);
			CacheKit.put(Constant.BILL_CACHE,Constant.BILL_CKDH,Constant.BILL_CKDH+no);
		}else{
			CacheKit.put(Constant.BILL_CACHE,Constant.BILL_CKDH,parameter.getValue());
		}
		//调拨单号
		parameter=findFirstByCode(Constant.BILL_DBDH);
		if(parameter==null){
			insertInitBillNo(Constant.BILL_DBDH,no,Constant.DBDH_NAME);
			CacheKit.put(Constant.SYS_CACHE,Constant.BILL_DBDH,Constant.BILL_DBDH+no);
		}else{
			CacheKit.put(Constant.SYS_CACHE,Constant.BILL_DBDH,parameter.getValue());

		}

	}
	public static Parameter findFirstByCode(String billType){
		String countSQL = "select * from tb_parameter where parameter= ?";
		return Parameter.dao.findFirst(countSQL,billType);
	}
	private static void insertInitBillNo(String para,String value,String billName){
		Parameter parameter = new Parameter();
		parameter.setParameter(para);
		parameter.setValue(para+value);
		parameter.setType("BILL");
		parameter.setDesc(billName);
		parameter.save();
	}
	public String getNewBillNo(String billType){
		//比较日期，看今天是否是第一单
		String d = TimeUtil.date2Str7(new Date());
		String cacheDH = CacheKit.get(Constant.BILL_CACHE,billType);
		String d2 = StrKit.notBlank(cacheDH)?cacheDH.substring(4,12):d;//取出日期部分
		String bh3 = "001";//后三位序号
		if(d.equals(d2)){ //说明今天已经有入库单号产生 ，取后三位+1得到新的单号
			bh3 =StrKit.notBlank(cacheDH)?cacheDH.substring(cacheDH.length()-3):"001";
			bh3 = String.valueOf(1000 +Integer.parseInt(bh3)+1);
			bh3 = bh3.substring(bh3.length()-3);
			return billType+d2+bh3;
		}else{  //否则这是第一单
			//no dosomething
			return billType+d+bh3;
		}
	}     
}