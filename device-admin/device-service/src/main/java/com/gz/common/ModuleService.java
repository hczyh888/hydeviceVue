package com.gz.common;

import com.gz.common.model.Module;

import java.util.List;

public class ModuleService {
    private static ModuleService moduleService;
    private ModuleService(){

    }
    public static ModuleService getService(){
        if (moduleService == null){
            moduleService = new ModuleService();
        }
        return moduleService;
    }
    public List<Module> getModuleList(){
       return Module.dao.find("select * from tb_module order by sort_num ");
    }
    public List<Module> getModuleCmb(){
        return Module.dao.find("select id,name from tb_module");
    }

}
