package com.example.Store_Digikala.modeldata;

import com.example.Store_Digikala.App;
import com.example.Store_Digikala.model.address.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class CartLab {
    private static CartLab ourInstance;
    private DaoSession daoSession = App.getApp().getDaoSession();
    private CartDao mCartDao = daoSession.getCartDao();

    private CartLab() {

    }

    public static CartLab getInstance() {
        if (ourInstance == null) {
            ourInstance = new CartLab();
        }
        return ourInstance;
    }

    public void addCart(Cart cart) {
        mCartDao.insert(cart);
    }

    public void deleteCart(Cart cart) {
        mCartDao.delete(cart);
    }

    public List<Cart> getCarts() {
        return mCartDao.loadAll();
    }

    public Cart checkCart(Integer id) {
        List<Cart> carts = mCartDao.queryBuilder()
                .where(CartDao.Properties.MProductId.eq(id)).list();
        for (Cart cart : carts) {
            return cart;
        }
        return null;
    }

    public List<Integer> getCartsID() {
        List<Cart> carts = getCarts();
        List<Integer> mIdProducts = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            int id = carts.get(i).getMProductId();
            mIdProducts.add(id);
        }
        return mIdProducts;
    }

    public void deleteAllCart(){
        mCartDao.deleteAll();
    }


    public Float getAllPrice(){
        List<Cart> carts=new ArrayList<>();
        carts=getCarts();
        float mPrice=0;
        for (int i = 0; i < carts.size(); i++) {
          mPrice=(mPrice+carts.get(i).getMPrice());
        }
        return mPrice;
    }
}
