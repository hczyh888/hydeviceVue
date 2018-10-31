package com.gz.common;

import java.util.List;

import com.gz.common.model.Warehouse;

public class WarehouseService {
    private static WarehouseService warehouseService;
    private WarehouseService(){}
    public static WarehouseService getService(){
        if(warehouseService==null){
            warehouseService=new WarehouseService();
        }
        return warehouseService;
    }
    public List<Warehouse> getWarehouseList(){
        return Warehouse.dao.find("select * from tb_warehouse ");
    }
    public Warehouse getWarehouse(Integer id){
        return Warehouse.dao.findById(id);
    }
}