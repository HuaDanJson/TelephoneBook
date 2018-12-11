package com.cqnu.sunxt.telephonebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cqnu.sunxt.telephonebook.bean.UploadContactBean;
import com.cqnu.sunxt.telephonebook.utils.DBContactBeanUtils;
import com.cqnu.sunxt.telephonebook.utils.ToastHelper;

public class AddActivity extends AppCompatActivity {
    //声明控件
    ImageButton returnButton;
    ImageButton finishButton;
    EditText name;
    EditText telephone;
    EditText weixin;
    EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        getSupportActionBar().hide();//隐藏标题栏

        //实例化控件
        returnButton = (ImageButton) findViewById(R.id.button_return);
        finishButton = (ImageButton) findViewById(R.id.button_finish);
        name = (EditText) findViewById(R.id.name);
        telephone = (EditText) findViewById(R.id.telephone);
        weixin = (EditText) findViewById(R.id.weixin);
        mail = (EditText) findViewById(R.id.mail);

        initText();//初始化文本
        //标题栏按钮事件
        returnButton.setOnClickListener(new Key());
        finishButton.setOnClickListener(new Key());
    }

    //初始化文本
    private void initText() {
        name.setText("");
        telephone.setText("");
        weixin.setText("");
        mail.setText("");
    }

    //标题栏按钮事件
    class Key implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_return:
                    finish();
                    break;
                case R.id.button_finish:
                    //判断姓名是否为空
                    if (name.getText().toString().equals("") == true) {
                        Toast.makeText(AddActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //判断电话格式是否正确
                    else if (telephone.getText().toString().matches("1\\d{10}") == false) {
                        Toast.makeText(AddActivity.this, "电话格式不正确", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //将数据存入数据库
                    UploadContactBean uploadContactBean = new UploadContactBean();
                    uploadContactBean.setName(name.getText().toString());
                    uploadContactBean.setPhoneNumber(telephone.getText().toString());
                    uploadContactBean.setWeixin(weixin.getText().toString());
                    uploadContactBean.setMail(mail.getText().toString());
                    DBContactBeanUtils.getInstance().insertOneData(uploadContactBean);
                    ToastHelper.showShortMessage("ADD success");
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
