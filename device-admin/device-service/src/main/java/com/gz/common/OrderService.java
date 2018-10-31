package com.gz.common;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.gz.common.model.Address;
import com.gz.common.model.Order;
import com.gz.common.model.OrderProduct;
import com.gz.common.model.Region;
import com.gz.utils.TimeUtil;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by gongzhen on 2018/6/2.
 */
public class OrderService {
    private static OrderService service;

    private OrderService() {
    }

    public static OrderService getService() {
        if (service == null) {
            service = new OrderService();
        }
        return service;
    }

    public String createOrderSN(){
        //当前系统时间戳精确到毫秒
        Long simple=System.currentTimeMillis();
        //三位随机数
        int random=new Random().nextInt(900)+100;//为变量赋随机值100-999;
        return "E"+ TimeUtil.date2Str0(new Date())+random;
    }
    public Order getOrder(int id){
        Order order=Order.dao.findById(id);
        order.put("orderProductList",getOrderProduct(order.getId()));
        order.put("youhuis", order.getYouhuis());
        return order;
    }
    /**
     *
     * @param pageNum
     * @param pageSize
     * @param order_status
     * @param userId
     * @param payment_status
     * @param shipping_status
     * @param active
     * @return
     */
    public Page<Order> getOrder(int pageNum, int pageSize, Integer order_status, Integer userId, Integer payment_status, Integer shipping_status, boolean active){
        String conditionSql=" active=false";
        if(userId!=null){
            conditionSql+=" and userId="+userId;
        }
        if(order_status!=null){
            conditionSql+=" and order_status="+order_status;
        }
        if(payment_status!=null){
            conditionSql+=" and payment_status="+payment_status;
        }
        if(shipping_status!=null){
            conditionSql+=" and shipping_status="+shipping_status;
        }
        if(shipping_status!=null){
            conditionSql+=" and shipping_status="+shipping_status;
        }
        Page<Order> orderPage= Order.dao.paginate(pageNum,pageSize,"select * ","from tb_order where "+ conditionSql+" order by createTime desc");
        for(Order order:orderPage.getList()){
            order.put("orderProductList",getOrderProduct(order.getId()));
            order.put("youhuis", order.getYouhuis());
        }
        return orderPage;
    }

    /**
     *
     * @param orderId
     * @return
     */
    public List<OrderProduct> getOrderProduct(int orderId){
      List<OrderProduct> orderProductList= OrderProduct.dao.find("SELECT * from tb_order_product where order_id=?",orderId);
        for(OrderProduct orderProduct:orderProductList){
            orderProduct.put("product_img_temp",Constant.FILE_PATH+orderProduct.getProductImg());
        }
        return orderProductList;
    }

    public Address getAddress(String provinceName,String cityName,String countyName){
     Region region=   Region.dao.findFirst("SELECT * from tb_region where type=3 and name=? and pid=(SELECT id from tb_region where type=2 and name=? and pid = (SELECT id from tb_region where type=1 and name=?))",countyName,cityName,provinceName);
        if(region==null){
            return  null;
        }
        Address address=new Address();
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setCountyName(countyName);
        address.setCode(region.getCode());
        address.setPostFee(region.getPostFee());
        return address;
    }
}
