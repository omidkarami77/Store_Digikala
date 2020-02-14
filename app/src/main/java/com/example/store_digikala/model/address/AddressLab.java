package com.example.Store_Digikala.model.address;

import android.content.Context;

import com.example.Store_Digikala.App;


import java.util.List;

public class AddressLab {

    public static AddressLab instance;
    private DaoSession daoSession = (App.getApp()).getDaoSession();
    private AddressDao mAddressDao = daoSession.getAddressDao();
    private Context mContext;


    public static AddressLab getInstance() {
        if (instance == null)
            instance = new AddressLab();
        return instance;
    }


    public List<Address> getAllList() {
        return mAddressDao.loadAll();

    }

    public void addAddress(Address address) {
        mAddressDao.insert(address);
    }

    public void update(Address address) {
        mAddressDao.update(address);
    }

    public void delete(Address address) {
        mAddressDao.delete(address);
    }

    public Address getAddress(String address1) {
        List<Address> addressList = mAddressDao.queryBuilder()
                .where(AddressDao.Properties.Address1.eq(address1))
                .list();
        return addressList.get(0);
    }

}
