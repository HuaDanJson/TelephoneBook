package com.cqnu.sunxt.telephonebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cqnu.sunxt.telephonebook.bean.UploadContactBean;
import com.cqnu.sunxt.telephonebook.utils.ActivityUtil;
import com.cqnu.sunxt.telephonebook.utils.DBContactBeanUtils;
import com.cqnu.sunxt.telephonebook.utils.ToastHelper;


public class DataActivity extends AppCompatActivity {

    private UploadContactBean mUploadContactBean;
    private EditText dataTelephone;
    private EditText dataWeixin;
    private EditText dataMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_layout);
        getSupportActionBar().hide();
        mUploadContactBean = getIntent().getParcelableExtra("Data");
        setActionBar();
        setData();
        //删除按钮
        ImageButton deleteButton = (ImageButton) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDelete();
            }
        });

        findViewById(R.id.btn_fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUploadContactBean.setPhoneNumber(dataTelephone.getText().toString());
                mUploadContactBean.setWeixin(dataWeixin.getText().toString());
                mUploadContactBean.setMail(dataMail.getText().toString());
                DBContactBeanUtils.getInstance().updateData(mUploadContactBean);
                ToastHelper.showShortMessage("修改成功");
                finish();
            }
        });

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String telephone = "tel:" + mUploadContactBean.getPhoneNumber();
                intent.setData(Uri.parse(telephone));
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.sendSMSMessage("这是短信内容", DataActivity.this, mUploadContactBean.getPhoneNumber());
            }
        });
    }

    //设置标题栏
    private void setActionBar() {
        //返回按钮
        ImageButton returnButton = (ImageButton) findViewById(R.id.button_return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //标题栏
        TextView dataName = (TextView) findViewById(R.id.data_name);
        dataName.setText(mUploadContactBean.getName());
    }

    //设置数据
    private void setData() {
        dataTelephone = (EditText) findViewById(R.id.data_telephone);
        dataWeixin = (EditText) findViewById(R.id.data_weixin);
        dataMail = (EditText) findViewById(R.id.data_mail);
        dataTelephone.setText(mUploadContactBean.getPhoneNumber());
        dataWeixin.setText(mUploadContactBean.getWeixin());
        dataMail.setText(mUploadContactBean.getMail());

        //拨号
        dataTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String telephone = "tel:" + mUploadContactBean.getPhoneNumber();
                intent.setData(Uri.parse(telephone));
                startActivity(intent);
            }
        });
    }

    //设置删除按钮
    private void setDelete() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("您确定要删除该联系人吗");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    //执行删除
    private void delete() {
        DBContactBeanUtils.getInstance().deleteOneData(mUploadContactBean);
        finish();
    }
}
