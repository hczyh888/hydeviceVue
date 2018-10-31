package com.gz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.gz.common.Constant;
import com.gz.common.OrderService;
import com.gz.common.ProductService;
import com.gz.common.UserService;
import com.gz.common.model.*;
import com.gz.utils.JSONUtil;
import com.gz.utils.Response;
import com.gz.utils.StringUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import java.util.*;

/**
 * Created by gongzhen on 2018/6/2.
 */
public class IndexController extends Controller{
    public void index(){
        renderJson("index");
    }
    public void getAdList(){
        List<Ad> adList= ProductService.getService().getAdList();
        renderJson(Response.responseJson(0,"成功",adList));
    }
    public void getProductPage(){
        int pageNum=getParaToInt("pageNum",1);
        int pageSize=getParaToInt("pageSize",10);
        String condition=" isMarketable=true";
        Page<Product> productPage= ProductService.getService().getProductPage(pageNum,pageSize,condition);
        renderJson(Response.responseJson(0,"成功",productPage));
    }
    public void getCategorys(){
        renderJson(Response.responseJson(0,"成功",ProductService.getService().getCategoryList()));
    }
    public void getProduct(){
        Product product=ProductService.getService().getProduct(getParaToInt("id",0));
        ProductPromotion productPromotion= ProductService.getService().getProductPromotion(getParaToInt("id",0));
        product.put("productPromotion",productPromotion);
        renderJson(Response.responseJson(0,"成功",product));
    }
    /**
     * 获取商品格值组
     */
    public void getSpecification(){
        int productId=getParaToInt("productId",0);
        Map<String,Object> result=  ProductService.getService().getSpecification(productId);
        renderJson(Response.responseJson(0,"成功",result));
    }

    /**
     * 准备下单数据
     */
    public void getOrderData(){
        JSONObject OrderData= JSON.parseObject(getPara("orderData"));
        Order order=new Order();
        order.setPostFee(0f);
        float totalPrice=0;
        User user= UserService.getService().getUserByToken(getPara("token"));

        List<ProductPromotion> productPromotionList=new ArrayList<>();
        JSONArray goods=OrderData.getJSONArray("goods");
        List<OrderProduct> orderProducts=new ArrayList<>();
        for (int i = 0; i <goods.size() ; i++) {
            JSONObject jsonObject=goods.getJSONObject(i);
            Product product=jsonObject.getObject("goods",Product.class);
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setProductId(product.getId());
            orderProduct.setName(product.getName());
            orderProduct.setPrice(product.getPrice());
            orderProduct.setSn(product.getSn());
            orderProduct.setProductImg(product.getThumbnail());
            orderProduct.put("product_img_temp",jsonObject.getString("thumbnail"));
            orderProduct.setQuantity(jsonObject.getInteger("count"));
            orderProduct.setPrice(jsonObject.getFloat("price"));
            totalPrice+=orderProduct.getPrice();
            JSONObject productPromotionJson=jsonObject.getJSONObject("goods").getJSONObject("productPromotion");
            if(productPromotionJson!=null){
                ProductPromotion productPromotion=jsonObject.getJSONObject("goods").getObject("productPromotion",ProductPromotion.class);
                orderProduct.setPromotionName(productPromotionJson.getString("name"));
                if(productPromotionJson.getIntValue("type")==1){
                    boolean flag=false;
                    for(ProductPromotion productPromotion2:productPromotionList){
                        if(productPromotion2.getPromotionId()==productPromotion.getPromotionId()){
                            productPromotion2.put("monetary",productPromotion2.getFloat("monetary")!=null?productPromotion2.getFloat("monetary")+orderProduct.getPrice()*orderProduct.getQuantity():orderProduct.getPrice()*orderProduct.getQuantity());
                            flag=true;
                            break;
                        }
                    }
                    if(flag==false){
                        productPromotion.put("monetary",orderProduct.getPrice()*orderProduct.getQuantity());
                        productPromotion.put("lastSpendMoney",productPromotionJson.getIntValue("lastSpendMoney"));
                        productPromotion.put("promotion_reduceMoney",productPromotionJson.getIntValue("promotion_reduceMoney"));
                        productPromotion.put("name",productPromotionJson.getString("name"));
                        productPromotionList.add(productPromotion);
                    }
                }
            }
            orderProduct.setSpecificationValue(jsonObject.getString("selectedSpecificationsStr"));
            orderProduct.setCreateTime(new Date());
            orderProducts.add(orderProduct);
        }
        order.put("orderProducts",orderProducts);
        order.setTotalPrice(totalPrice);
        Coupon coupon=ProductService.getService().getCouponByUserId(user.getId(),order.getTotalPrice());
        order.setCouponMoney(0);
        if(coupon!=null){
            order.setCouponMoney(coupon.getMoney());
            order.setCouponName(coupon.getName());
            order.setUserCouponId(coupon.getInt("user_coupon_id"));
        }
        JSONArray youhuis=new JSONArray();
        float reduceMoney=0;
        for(ProductPromotion productPromotion3:productPromotionList){
            if(productPromotion3.getFloat("monetary")>=productPromotion3.getFloat("lastSpendMoney")){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("youhuiname",productPromotion3.get("name"));
                jsonObject.put("youhuimoney",productPromotion3.getFloat("promotion_reduceMoney"));
                reduceMoney+=productPromotion3.getFloat("promotion_reduceMoney");
                youhuis.add(jsonObject);
            }
        }
        order.setYouhuis(youhuis.toJSONString());
        if(!StringUtil.isEmpty(user.getAddress())){
            Address address=JSONUtil.getObjFromJson(user.getAddress(),Address.class);
            order.setPostFee(address.getPostFee());
            order.setAddress(user.getAddress());
        }
        order.setPayFee(order.getTotalPrice()-order.getCouponMoney()-reduceMoney);
        renderJson(Response.responseJson(0,"成功",order));
    }
    public void chooseAddress(){
        Address address=JSONUtil.getObjFromJson(getPara("address"),Address.class);
        User user= UserService.getService().getUserByToken(getPara("token"));
        if(address!=null){
           Address address2=OrderService.getService().getAddress(address.getProvinceName(),address.getCityName(),address.getCountyName());
            address.setPostFee(address2.getPostFee());
            user.setAddress(JSONObject.toJSONString(address));
            user.update();
            renderJson(Response.responseJson(0,"成功",address));
        }else{
            renderJson(Response.responseJson(1,"地址解析错误"));
        }

    }
    /**
     * 创建订单
     */
    public void createOrder(){
        JSONObject OrderData= JSON.parseObject(getPara("order"));
        int code=0;
        if(OrderData==null||StringUtil.isEmpty(OrderData.getString("address"))){
            renderJson(Response.responseJson(1,"订单信息错误，请刷新重试"));
            return;
        }
        Order order=JSONObject.parseObject(getPara("order"),Order.class);
        order.setCreateTime(new Date());
        order.setOrderSN(OrderService.getService().createOrderSN());
        User user= UserService.getService().getUserByToken(getPara("token"));
        order.setUserId(user.getId());
        if(order.getId()==null)
        order.save();
        List<OrderProduct> orderProducts=JSONObject.parseArray(OrderData.getString("orderProducts"),OrderProduct.class);
        float totalPrice=0;
        for(OrderProduct orderProduct:orderProducts){
            totalPrice+=orderProduct.getQuantity()*orderProduct.getPrice();
            orderProduct.setOrderId(order.getId());
            orderProduct.put("product_img_temp", Constant.FILE_PATH+orderProduct.getProductImg());
            if(orderProduct.getId()==null) {
                orderProduct.save();
               Product product= ProductService.getService().getProduct(orderProduct.getProductId());
                product.setSales(product.getSales()+1);
                product.update();
            }
        }
        order.put("orderProducts",orderProducts);
        boolean flag=false;
        if(order.getTotalPrice()!=totalPrice){
            flag=true;
            code=2;
            order.setTotalPrice(totalPrice);
        }
        order.setTotalPrice(OrderData.getFloat("totalPrice"));


        Coupon coupon=ProductService.getService().getCouponByUserId(user.getId(),order.getTotalPrice());
        order.setCouponMoney(0);
        if(coupon!=null){
            if(order.getCouponMoney()!=coupon.getMoney()){
                flag=true;
            }
            order.setCouponMoney(coupon.getMoney());
            order.setCouponName(coupon.getName());
            order.setUserCouponId(coupon.getInt("user_coupon_id"));
            UserCoupon userCoupon=UserCoupon.dao.findById(coupon.getInt("user_coupon_id"));
            userCoupon.setStatus(true).update();
        }
        JSONArray youhuis=OrderData.getJSONArray("youhuis");
        float reduceMoney=0;
        for(int i=0;i<youhuis.size();i++){
            JSONObject jsonObject=youhuis.getJSONObject(i);
            reduceMoney+=jsonObject.getFloat("youhuimoney");
        }
        float payFee=order.getTotalPrice()-order.getCouponMoney()-order.getPostFee()-reduceMoney;
        if(order.getPayFee()!=payFee){
            flag=true;
            code=3;
        }
        order.setPayFee(payFee);
        order.update();
        if(flag==true){
            renderJson(Response.responseJson(code,"订单异常请刷新重试",order));
        }else{
            renderJson(Response.responseJson(0,"下单成功",order));
        }

    }
    public void getOrderList(){
        Integer pageNum=getParaToInt("pageNum",1);
        Integer pageSize=getParaToInt("pageSize",10);
        Integer order_status=getParaToInt("order_status");
        Integer payment_status=getParaToInt("payment_status");
        Integer shipping_status=getParaToInt("shipping_status");
        User user= UserService.getService().getUserByToken(getPara("token"));
        Page<Order> orderPage= OrderService.getService().getOrder( pageNum,  pageSize,  order_status,  user.getId(),  payment_status, shipping_status, false);
        renderJson(Response.responseJson(0,"成功",orderPage));
    }
    public void getOrder(){
        Integer orderId=getParaToInt("orderId",0);
        Order order= OrderService.getService().getOrder(orderId);
        renderJson(Response.responseJson(0,"成功",order));
    }
    public void getNoLCouponList(){
        User user= UserService.getService().getUserByToken(getPara("token"));
        renderJson(Response.responseJson(0,"成功",ProductService.getService().getNoLCouponList(user.getId())));
    }
    public void lingquCoupon(){
        User user= UserService.getService().getUserByToken(getPara("token"));
        int couponId=getParaToInt("couponId",0);
        UserCoupon userCoupon=ProductService.getService().lingquCoupon(user.getId(),couponId);
        if(userCoupon!=null){
            renderJson(Response.responseJson(0,"成功",userCoupon));
        }else{
            renderJson(Response.responseJson(1,"领取失败"));
        }

    }

}
