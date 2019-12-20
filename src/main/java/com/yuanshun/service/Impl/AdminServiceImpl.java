package com.yuanshun.service.Impl;

import com.yuanshun.dao.LoginDao;
import com.yuanshun.entity.Admin;
import com.yuanshun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl
  implements AdminService
{
  @Autowired
  public LoginDao LoginDao;

  @Override
  public void userInsert(Admin admin) {
    this.LoginDao.userInsert(admin);
  }

  @Override
  public boolean loginByPassword(Admin admin) {
    String password = admin.getPassword();
    return password.equals(this.LoginDao.queryPassword(admin));
  }
  public List<Admin> queryAll()
  {
    return this.LoginDao.queryAll();
  }
  @Override
  public String queryCardlist(String username){
      return this.LoginDao.queryCardlist(username);
  }
  @Override
  public Integer queryMoney(String username){
      return this.LoginDao.queryMoney(username);
  }
  @Override
  public String queryCardname(Integer cardid){
      return this.LoginDao.queryCardname(cardid);
  }
  @Override
  public String queryurl(Integer cardid){
    return this.LoginDao.queryurl(cardid);
  }
}