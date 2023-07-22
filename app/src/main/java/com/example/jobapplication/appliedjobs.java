package com.example.jobapplication;

public class appliedjobs {
    String company;
    String BAtchno;
    String name;
    String Job;
    String status;
    String phoneno;
    public appliedjobs(){

    }

    public appliedjobs(String company, String BAtchno, String name, String job, String status, String phoneno) {
        this.company = company;
        this.BAtchno = BAtchno;
        this.name = name;
        Job = job;
        this.status = status;
        this.phoneno = phoneno;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBAtchno() {
        return BAtchno;
    }

    public void setBAtchno(String BAtchno) {
        this.BAtchno = BAtchno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
