package com.gz.common;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;

/**
 * Created by gongzhen on 2018/6/6.
 */
public interface Constant {
    //public final static String HOST_PATH = "http://192.168.199.175";//主机地址可以为域名
    public final static String HOST_PATH = "http://127.0.0.1";//主机地址可以为域名
    public final static String PROJECT_PATH = JFinal.me().getContextPath()+"/";
    public static String FILE_PATH=HOST_PATH+ PropKit.get("FILE_UPLOAD_PATH");//图片文件预览下载地址，'/'表示cms-admin项目根路径，如为admin则此值为HOST_PATH+"/admin/"

    String BILL_RKDH = "RKDH";  //入库单号前缀
    String RKDH_NAME = "入库单号";  //入库单名称
    String BILL_CKDH = "CKDH";  //出库单前缀
    String CKDH_NAME = "出库单号";  //出库单名称
    String BILL_DBDH = "DBDH";  //调拨单前缀
    String DBDH_NAME = "调拨单号";  //调拨单名称
    //
    String DEFAULT_CACHE = "defaultCache";
    String BILL_CACHE = "billCache";
    String DEVICECLASS_CACHE = "deviceclassCache";
    String DEVICELIST_CACHE = "devicelistCache";
    String DEPT_CACHE = "deptCache";
    String MENU_CACHE = "menuCache";
    String ROLES_CACHE = "rolesCache";
    String USER_CACHE = "userCache";
    String LEAVESTOCK_CACHE = "leaveStockCache";
    String ENTERSTOCK_CACHE = "enterStockCache";
    String WAREHOUSE_CACHE = "wareHouseCache";
    String DIY_CACHE = "diyCache";
    String JSON_CACHE = "diyCache";
    String SYS_CACHE = "sysCache";
    String DEPTCATE_CACHE = "deptcateCache";
    String ROLECATE_CACHE = "rolecateCache";
    String GROUPCUS_CACHE = "groupcusCache";
    String LOCALCUS_CACHE = "localcusCache";
    String ROUTE_CACHE = "routeCache";

}
