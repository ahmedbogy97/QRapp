package com.example.qrapp.services;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.qrapp.R;
import com.victor.loading.rotate.RotateLoading;

public class Dialogs {



    public static Dialog createProgressBarDialog(Context mContext, String text) {
        Dialog mDialog;
        mDialog = new Dialog(mContext, R.style.Theme_Dialog);
        mDialog.setContentView(R.layout.dialog_progress_bar);

        //find Views
        RotateLoading rotateLoading =(RotateLoading)mDialog.findViewById(R.id.rotate_loading);
        rotateLoading.start();

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCancelable(false);

        return mDialog;
    }
}
