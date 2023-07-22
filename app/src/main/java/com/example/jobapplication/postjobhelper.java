package com.example.jobapplication;

public class postjobhelper {
    String company;
    String description;
    String requirements;
    String closing;
    String phone;
    String location;
    String UId;
    String appliedby;
    String status;
    String Batchno;
    String applicantsid;
    String title;
    String salary;
    String category;
    String type;
    boolean expandable;

    public  postjobhelper()
    {

    }

    public postjobhelper(String company, String description, String requirements, String closing, String phone, String location,String UId,String appliedby,String status,String batchno,String applicantsid,String title,String salary,String category,String type) {
        this.company = company;
        this.description = description;
        this.requirements = requirements;
        this.closing = closing;
        this.phone = phone;
        this.location = location;
        this.UId= UId;
        this.appliedby=appliedby;
        this.status=status;
        this.Batchno=batchno;
        this.applicantsid=applicantsid;
        this.expandable=false;
        this.title=title;
        this.salary=salary;
        this.category=category;
        this.type=type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getApplicantsid() {
        return applicantsid;
    }

    public void setApplicantsid(String applicantsid) {
        this.applicantsid = applicantsid;
    }

    public String getBatchno() {
        return Batchno;
    }

    public void setBatchno(String batchno) {
        Batchno = batchno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppliedby() {
        return appliedby;
    }

    public void setAppliedby(String appliedby) {
        this.appliedby = appliedby;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
