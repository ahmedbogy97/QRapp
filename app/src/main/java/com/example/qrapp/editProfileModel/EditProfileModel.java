package com.example.qrapp.editProfileModel;

public class EditProfileModel {

    String Age;
    String blood_type;
    String diseases;
    String email;
    String medicine;
    String phone;
    String phone_1;
    String phone_2;
    String phone_3;
    String surgeries;
    String userName;

    public EditProfileModel(String age, String blood_type, String diseases, String email, String medicine, String phone, String phone_1, String phone_2, String phone_3, String surgeries, String userName) {
        Age = age;
        this.blood_type = blood_type;
        this.diseases = diseases;
        this.email = email;
        this.medicine = medicine;
        this.phone = phone;
        this.phone_1 = phone_1;
        this.phone_2 = phone_2;
        this.phone_3 = phone_3;
        this.surgeries = surgeries;
        this.userName = userName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_1() {
        return phone_1;
    }

    public void setPhone_1(String phone_1) {
        this.phone_1 = phone_1;
    }

    public String getPhone_2() {
        return phone_2;
    }

    public void setPhone_2(String phone_2) {
        this.phone_2 = phone_2;
    }

    public String getPhone_3() {
        return phone_3;
    }

    public void setPhone_3(String phone_3) {
        this.phone_3 = phone_3;
    }

    public String getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(String surgeries) {
        this.surgeries = surgeries;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
