package com.example.qrapp.login;

interface LoginInterface {

    void initUI();// find views and set onClick to it

    void checkEmailAndPassword(); // not is not Empty

    void loginUser(String email, String password);// check if user has access to login or not

    void saveUserData(); //send user data ( this device (token , os) is active  )to firebase

    void createDialogToEditPassword();//create Dialog To Edit Password

    void forgetPass(String mail);//send mail to user to change password

}
