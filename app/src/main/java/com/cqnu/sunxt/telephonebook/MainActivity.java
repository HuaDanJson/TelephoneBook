package com.cqnu.sunxt.telephonebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.cqnu.sunxt.telephonebook.base.BaseDataSource;
import com.cqnu.sunxt.telephonebook.base.ContactRVAdapter;
import com.cqnu.sunxt.telephonebook.bean.ContactsInfo;
import com.cqnu.sunxt.telephonebook.bean.UploadContactBean;
import com.cqnu.sunxt.telephonebook.utils.ContactsInfoHelper;
import com.cqnu.sunxt.telephonebook.utils.DBContactBeanUtils;
import com.cqnu.sunxt.telephonebook.utils.SharedPrefUtils;
import com.cqnu.sunxt.telephonebook.utils.ToastHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rlv_main_activity) RecyclerView mRecyclerView;
    @BindView(R.id.button_add) ImageButton mButtonAdd;
    private List<UploadContactBean> mContactList = new ArrayList<>();
    private ContactRVAdapter contactRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        getPermission();
    }

    public void getPermission() {
        PermissionUtils.permission(PermissionConstants.CONTACTS, PermissionConstants.STORAGE, PermissionConstants.SMS)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {

                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            PermissionUtils.launchAppDetailsSettings();
                        }
                    }
                }).request();
    }


    public void getContactsList() {
        final List<UploadContactBean> uploadContactBeanList = new ArrayList<>();
        ContactsInfoHelper.getInstance().getContactList(new BaseDataSource.GetDataSourceCallback<List<ContactsInfo>>() {
            @Override
            public void onLoaded(@NonNull List<ContactsInfo> data) {
                LogUtils.d("MainActivity getContactsList data = " + data);
                LogUtils.d("MainActivity getContactsList data.size() = " + data.size());
                for (ContactsInfo contactsInfo : data) {
                    if (contactsInfo != null && (!(TextUtils.isEmpty(contactsInfo.getName()))) && validPhoneNumber(contactsInfo.getPhone())) {
                        UploadContactBean uploadContactBean = new UploadContactBean(contactsInfo.getName(), contactsInfo.getPhone());
                        if (contactsInfo.getPhoto() == null) {
                            uploadContactBean.setAvatarUrl("");
                        } else {
                            uploadContactBean.setAvatarUrl(contactsInfo.getPhoto());
                        }
                        uploadContactBeanList.add(uploadContactBean);
                    }
                }
                Collections.sort(uploadContactBeanList);
                if (!SharedPrefUtils.getInstance().getBoolean("GetContacts")) {
                    mContactList.addAll(uploadContactBeanList);
                    initRecyclerView(mContactList);
                    SharedPrefUtils.getInstance().putBoolean("GetContacts", true);
                    DBContactBeanUtils.getInstance().insertManyData(uploadContactBeanList);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    //恢复时读取联系人
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        mContactList = DBContactBeanUtils.getInstance().queryAllData();
        initRecyclerView(mContactList);
    }

    @OnClick(R.id.button_add)
    public void onAddClicked() {
        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }

    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //添加联系人
            case R.id.add_people:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.clear_people:
                DBContactBeanUtils.getInstance().deleteAll();
                mContactList.clear();
                initRecyclerView(mContactList);
                Toast.makeText(MainActivity.this, "已清空", Toast.LENGTH_SHORT).show();
                break;
            case R.id.import_people:
                if (!SharedPrefUtils.getInstance().getBoolean("GetContacts")) {
                    getContactsList();
                } else {
                    ToastHelper.showShortMessage("手机通讯录已经导入");
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean validPhoneNumber(String phoneNum) {
        return phoneNum.length() >= 4 && phoneNum.length() <= 17;
    }

    public void initRecyclerView(List<UploadContactBean> list) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            contactRVAdapter = new ContactRVAdapter(list, this);
            mRecyclerView.setAdapter(contactRVAdapter);
        }
    }
}