package com.yuanshun.service;

import com.yuanshun.entity.Admin;

import java.util.List;

public interface AdminService{
  public abstract void userInsert(Admin admin);
  public abstract boolean loginByPassword(Admin admin);
  public abstract List<Admin> queryAll();
  public abstract void addMoney(String username,int award);
  public abstract String queryCardlist(String username);
  public abstract String queryStoreCardlist(String username);
  public abstract Integer buyCard(String username,Integer cardid);
  public abstract Integer sellCard(String username,Integer cardid);
  public abstract Integer queryMoney(String username);
  public abstract String queryCardname(Integer cardid);
  public abstract String queryurl(Integer cardid);
}
