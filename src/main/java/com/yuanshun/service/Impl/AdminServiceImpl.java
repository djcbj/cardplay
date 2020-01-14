package com.yuanshun.service.Impl;

import com.yuanshun.dao.LoginDao;
import com.yuanshun.entity.Admin;
import com.yuanshun.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
  public String queryStoreCardlist(String username){
    return this.LoginDao.queryStoreCardlist(username);
  }
  @Override
  public Integer buyCard(String username,Integer cardid){
    //先检查钱
    int money = this.LoginDao.queryMoney(username);
    money = money - cardid;
    if (money<0) return -1;
    //更新商店删卡
    String storecardliststr = this.LoginDao.queryStoreCardlist(username);
    String[] cards = storecardliststr.split(",");
    ArrayList<String> storecardlist= new ArrayList<>(Arrays.asList(cards));
    if(storecardlist.contains(String.valueOf(cardid))){
      storecardlist.remove(storecardlist.indexOf(String.valueOf(cardid)));
    }
    else return -2;
    if(storecardlist.size()==0){
      this.LoginDao.updateStore(username,"");
    }
    else if(storecardlist.size()==1){
      this.LoginDao.updateStore(username,storecardlist.get(0));
    }
    else{
      this.LoginDao.updateStore(username,StringUtils.join(storecardlist,","));
    }
    //更新背包加卡
    String backpackcardliststr = this.LoginDao.queryCardlist(username);
    if(backpackcardliststr.equals("")){
      this.LoginDao.updateBackpack(username,String.valueOf(cardid));
    }
    else{
      String[] cards2 = backpackcardliststr.split(",");
      ArrayList<String> backcardlist= new ArrayList<>(Arrays.asList(cards2));
      backcardlist.add(String.valueOf(cardid));
      Collections.sort(backcardlist);
      this.LoginDao.updateBackpack(username, StringUtils.join(backcardlist,","));
    }
    //加钱
    this.LoginDao.updateMoney(username,String.valueOf(money));
    return 1;
  }
  @Override
  public Integer sellCard(String username,Integer cardid){
    //更新背包删卡
    String backpackcardliststr = this.LoginDao.queryCardlist(username);
    String[] cards = backpackcardliststr.split(",");
    ArrayList<String> backpackcardlist= new ArrayList<>(Arrays.asList(cards));
    if(backpackcardlist.contains(String.valueOf(cardid))){
      backpackcardlist.remove(backpackcardlist.indexOf(String.valueOf(cardid)));
    }
    else return -2;
    this.LoginDao.updateBackpack(username,StringUtils.join(backpackcardlist,","));
    //更新商店加卡
    String storecardliststr = this.LoginDao.queryStoreCardlist(username);
    if(storecardliststr.equals("")){
      this.LoginDao.updateStore(username, String.valueOf(cardid));
    }
    String[] cards2 = storecardliststr.split(",");
    ArrayList<String> storecardlist= new ArrayList<>(Arrays.asList(cards2));
    storecardlist.add(String.valueOf(cardid));
    Collections.sort(storecardlist);
    this.LoginDao.updateStore(username, StringUtils.join(storecardlist,","));
    //加钱
    int money = this.LoginDao.queryMoney(username);
    money = money + cardid;
    this.LoginDao.updateMoney(username,String.valueOf(money));
    return 1;
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
  @Override
  public void addMoney(String username,int award){
    int money = this.LoginDao.queryMoney(username);
    String newmoney = String.valueOf(money+award);
    this.LoginDao.updateMoney(username,newmoney);
  }
}