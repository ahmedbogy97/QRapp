package com.example.qrapp;

public class User {

    private String fullname, email, password_toggle, phone, id, bloodtype, phone1, phone2, phone3, Age, medicine, diseases, surgeries;

    private String title , value ;

    public User(){

    }

    public User(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public User(String fullname, String email, String password_toggle, String phone, String id, String bloodtype, String phone1, String phone2, String phone3, String age, String medicine, String diseases, String surgeries){
        this.fullname = fullname;
        this.email = email;

    }

}
