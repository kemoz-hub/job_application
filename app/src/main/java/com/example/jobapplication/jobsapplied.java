package com.example.jobapplication;

public class jobsapplied {
    public jobsapplied() {
    }

    String uid;
    String idnumber;
    String fullname;
    String companyname;
    String phoneno;
    String jobcategory;
    String education;
    String coverletter;
    String empid;
    String dategraduated;
    String skills;
    String gender;
    String status;
    String  Obnumber;

    public jobsapplied(String uid, String idnumber, String fullname, String companyname, String phoneno, String jobcategory, String education, String coverletter, String empid, String dategraduated, String skills, String gender,String status,String obnumber) {
        this.uid = uid;
        this.idnumber = idnumber;
        this.fullname = fullname;
        this.companyname = companyname;
        this.phoneno = phoneno;
        this.jobcategory = jobcategory;
        this.education = education;
        this.coverletter = coverletter;
        this.empid = empid;
        this.dategraduated = dategraduated;
        this.skills = skills;
        this.gender = gender;
        this.status=status;
        this.Obnumber=obnumber;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObnumber() {
        return Obnumber;
    }

    public void setObnumber(String obnumber) {
        Obnumber = obnumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getJobcategory() {
        return jobcategory;
    }

    public void setJobcategory(String jobcategory) {
        this.jobcategory = jobcategory;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCoverletter() {
        return coverletter;
    }

    public void setCoverletter(String coverletter) {
        this.coverletter = coverletter;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getDategraduated() {
        return dategraduated;
    }

    public void setDategraduated(String dategraduated) {
        this.dategraduated = dategraduated;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
