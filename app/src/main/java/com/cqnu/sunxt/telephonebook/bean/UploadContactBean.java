package com.cqnu.sunxt.telephonebook.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.cqnu.sunxt.telephonebook.utils.Cn2Spell;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class UploadContactBean implements Comparable<UploadContactBean>, Parcelable {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "UploadContactBean")
    private String name;
    private String phoneNumber;
    private String avatarUrl;
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母
    private boolean isContainsEmoji;
    private boolean isSelected;
    private String weixin;
    private String mail;

    public UploadContactBean(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        char codePoint = name.charAt(0);

        LogUtils.d("MainActivity UploadContactBean name = " + name + "   codePoint =  " + codePoint);
        if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
            isContainsEmoji = true;
        } else {
            isContainsEmoji = false;
        }
        LogUtils.d("MainActivity UploadContactBean name = " + name + "   isContainsEmoji =  " + isContainsEmoji);

        if (isContainsEmoji) {
            pinyin = "A11111111" + Cn2Spell.getPinYin(name); // 根据姓名获取拼音
            firstLetter = "A";
        } else {
            pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
            firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
            if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                firstLetter = "#";
            }
        }
    }

    @Generated(hash = 1570726367)
    public UploadContactBean(Long id, String name, String phoneNumber, String avatarUrl, String pinyin, String firstLetter,
                             boolean isContainsEmoji, boolean isSelected, String weixin, String mail) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
        this.pinyin = pinyin;
        this.firstLetter = firstLetter;
        this.isContainsEmoji = isContainsEmoji;
        this.isSelected = isSelected;
        this.weixin = weixin;
        this.mail = mail;
    }

    @Generated(hash = 719928618)
    public UploadContactBean() {
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public boolean isContainsEmoji() {
        return isContainsEmoji;
    }

    public void setContainsEmoji(boolean containsEmoji) {
        isContainsEmoji = containsEmoji;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public int compareTo(@NonNull UploadContactBean another) {
        if (firstLetter.equals("#") && !another.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && another.getFirstLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(another.getPinyin());
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsContainsEmoji() {
        return this.isContainsEmoji;
    }

    public void setIsContainsEmoji(boolean isContainsEmoji) {
        this.isContainsEmoji = isContainsEmoji;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    @Override
    public String toString() {
        return "UploadContactBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                ", isContainsEmoji=" + isContainsEmoji +
                ", isSelected=" + isSelected +
                ", weixin='" + weixin + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.pinyin);
        dest.writeString(this.firstLetter);
        dest.writeByte(this.isContainsEmoji ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.weixin);
        dest.writeString(this.mail);
    }

    protected UploadContactBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.avatarUrl = in.readString();
        this.pinyin = in.readString();
        this.firstLetter = in.readString();
        this.isContainsEmoji = in.readByte() != 0;
        this.isSelected = in.readByte() != 0;
        this.weixin = in.readString();
        this.mail = in.readString();
    }

    public static final Creator<UploadContactBean> CREATOR = new Creator<UploadContactBean>() {
        @Override
        public UploadContactBean createFromParcel(Parcel source) {return new UploadContactBean(source);}

        @Override
        public UploadContactBean[] newArray(int size) {return new UploadContactBean[size];}
    };
}