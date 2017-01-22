package com.example.emil.showsmanager.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.emil.showsmanager.R;

public class LoadingDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public static ProgressDialog showProgressDialog(Context context, String text){
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage("xxx");
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }

}
