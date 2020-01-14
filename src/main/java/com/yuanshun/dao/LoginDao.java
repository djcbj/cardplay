package com.yuanshun.dao;

import com.yuanshun.entity.Admin;

import java.util.List;

public interface LoginDao {
    public abstract int userInsert(Admin admin);
    public abstract String queryPassword(Admin admin);
    public abstract List<Admin> queryAll();
    public abstract String queryCardlist(String username);
    public abstract String queryStoreCardlist(String username);
    public abstract Integer updateStore(String username,String newCardlist);
    public abstract Integer updateBackpack(String username,String newCardlist);
    public abstract Integer updateMoney(String username,String money);
    public abstract Integer queryMoney(String username);
    public abstract String queryCardname(Integer cardid);
    public abstract String queryurl(Integer cardid);
}
