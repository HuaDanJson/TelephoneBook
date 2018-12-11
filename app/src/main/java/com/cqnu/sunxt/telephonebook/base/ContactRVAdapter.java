package com.cqnu.sunxt.telephonebook.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.cqnu.sunxt.telephonebook.DataActivity;
import com.cqnu.sunxt.telephonebook.R;
import com.cqnu.sunxt.telephonebook.bean.UploadContactBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ContactRVAdapterHolder> {

    private List<UploadContactBean> contactList = new ArrayList<>();
    private Activity mActivity;

    public ContactRVAdapter(List<UploadContactBean> unlockADataLis, Activity activity) {
        this.contactList = unlockADataLis;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ContactRVAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unlock_plana_contact_list, parent, false);
        return new ContactRVAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactRVAdapterHolder holder, final int position) {
        LogUtils.d("MainActivity ContactRVAdapter onBindViewHolder position = " + contactList.get(position));
        holder.mItem.setSelected(false);
        contactList.get(position).setSelected(false);
        Glide.with(mActivity)
                .load(contactList.get(position).getAvatarUrl())
                .placeholder(R.drawable.icon_normal)
                .fitCenter()
                .dontAnimate()
                .into((holder).mAvatar);

        holder.mName.setText(contactList.get(position).getName());
        holder.mPhoneNumber.setText(contactList.get(position).getPhoneNumber());
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, DataActivity.class);
                intent.putExtra("Data", contactList.get(position));
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public List<UploadContactBean> getSelectContacts() {
        List<UploadContactBean> selectList = new ArrayList<>();
        for (UploadContactBean uploadContactBean : contactList) {
            if (uploadContactBean.isSelected()) {
                selectList.add(uploadContactBean);
            }
        }
        return selectList;
    }

    public static class ContactRVAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_avatar_item_unlock_plana_contact_list) CircleImageView mAvatar;
        @BindView(R.id.tv_name_item_unlock_plana_contact_list) TextView mName;
        @BindView(R.id.tv_phone_number_item_unlock_plana_contact_list) TextView mPhoneNumber;
        @BindView(R.id.ll_item_unlock_plana_contact_list) LinearLayout mItem;

        public ContactRVAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
