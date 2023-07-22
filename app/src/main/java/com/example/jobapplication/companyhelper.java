package com.example.jobapplication;

public class companyhelper {
    String employerid;
    String Companyname;
    String companyaddress;
    String companyphone;
    String companylocation;
    public  companyhelper(){

    }

    public companyhelper(String employerid, String companyname, String companyaddress, String companyphone, String companylocation) {
        this.employerid = employerid;
        Companyname = companyname;
        this.companyaddress = companyaddress;
        this.companyphone = companyphone;
        this.companylocation = companylocation;
    }

    public String getEmployerid() {
        return employerid;
    }

    public void setEmployerid(String employerid) {
        this.employerid = employerid;
    }

    public String getCompanyname() {
        return Companyname;
    }

    public void setCompanyname(String companyname) {
        Companyname = companyname;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public String getCompanylocation() {
        return companylocation;
    }

    public void setCompanylocation(String companylocation) {
        this.companylocation = companylocation;
    }
}
