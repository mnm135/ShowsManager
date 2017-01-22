package com.example.emil.showsmanager.activities;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {

    public static ProgressDialog showProgressDialog(Context context, String text){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(text);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
}
