package com.gz.common;

import com.gz.common.model.Company;

public class CompanyService {
    private static CompanyService companyService;
    private CompanyService(){}
    public static CompanyService getService(){
        if(companyService == null){
            companyService = new CompanyService();
        }
        return companyService;
    }
    public Company getCompany(){
        Company company= Company.dao.findFirst("select * from tb_company");
        company.put("logo_tmp",Constant.FILE_PATH+company.getLogo());
        return company;
    }
}
