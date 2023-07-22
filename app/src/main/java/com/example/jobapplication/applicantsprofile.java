package com.example.jobapplication;

public class applicantsprofile {

    String idnumber;
    String fullname;
    String email;
    String skills;
    String phoneno;
    String dategraduated;
    String jobcategory;
    String education;
    String gender;

    public applicantsprofile(){

    }

    public applicantsprofile(String idnumber, String fullname, String email, String skills, String phoneno, String dategraduated, String jobcategory, String education, String gender) {
        this.idnumber = idnumber;
        this.fullname = fullname;
        this.email = email;
        this.skills = skills;
        this.phoneno = phoneno;
        this.dategraduated = dategraduated;
        this.jobcategory = jobcategory;
        this.education = education;
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDategraduated() {
        return dategraduated;
    }

    public void setDategraduated(String dategraduated) {
        this.dategraduated = dategraduated;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
