package com.cqnu.sunxt.telephonebook.utils;

import android.content.Context;

import com.aidebar.greendaotest.gen.DaoManager;
import com.aidebar.greendaotest.gen.UploadContactBeanDao;
import com.cqnu.sunxt.telephonebook.bean.UploadContactBean;

import java.util.List;

@Deprecated
public class DBContactBeanUtils {

    private UploadContactBeanDao dbUserInfoBeanDao;
    private static DBContactBeanUtils dbUserInvestmentUtils = null;

    public DBContactBeanUtils(Context context) {
        dbUserInfoBeanDao = DaoManager.getInstance(context).getNewSession().getUploadContactBeanDao();
    }

    public static DBContactBeanUtils getInstance() {
        return dbUserInvestmentUtils;
    }

    public static void Init(Context context) {
        if (dbUserInvestmentUtils == null) {
            dbUserInvestmentUtils = new DBContactBeanUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     *
     * @param
     * @return
     */
    public void insertOneData(UploadContactBean dbUserInvestment) {
        dbUserInfoBeanDao.insertOrReplace(dbUserInvestment);
    }

    /**
     * 完成对数据库中插入多条数据操作
     *
     * @param dbUserInvestmentList
     * @return
     */
    public boolean insertManyData(List<UploadContactBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.insertOrReplaceInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据操作
     *
     * @param dbUserInvestment
     * @return
     */
    public boolean deleteOneData(UploadContactBean dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.delete(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     *
     * @return
     */
    public boolean deleteOneDataByKey(long id) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteByKey(id);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中批量删除数据操作
     *
     * @return
     */
    public boolean deleteManData(List<UploadContactBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中数据全部删除
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteAll();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 完成对数据库更新数据操作
     *
     * @return
     */
    public boolean updateData(UploadContactBean dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.update(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     *
     * @return
     */
    public boolean updateManData(List<UploadContactBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.updateInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     *
     * @return
     */
    public UploadContactBean queryOneData(long id) {
        return dbUserInfoBeanDao.load(id);
    }

    /**
     * 完成对数据库查询所有数据操作
     *
     * @return
     */
    public List<UploadContactBean> queryAllData() {
        return dbUserInfoBeanDao.loadAll();
    }
}
