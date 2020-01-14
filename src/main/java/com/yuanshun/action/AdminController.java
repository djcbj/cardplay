package com.yuanshun.action;

import com.yuanshun.entity.Admin;
import com.yuanshun.service.AdminService;
import com.yuanshun.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping({"/adm"})
public class AdminController
{


    @Autowired
    //private LoginDao loginDao;
    private AdminService adminService;
    @Autowired
    private ProductService ProductMapper;

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "userLogin";
    }    //返回值是页面view!

    @RequestMapping("/registerPage")
    public String registerPage() {
        return "userRegister";
    }    //返回值是页面view!

    @RequestMapping(value={"/register"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String register(Admin admin, HttpServletRequest request) throws IOException {
        //request.getSession().setAttribute("mes", "Register Succeed!");
        this.adminService.userInsert(admin);
        return "userLogin";
    }
    @RequestMapping(value={"/login2"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String confirmAdmin(Admin admin) {
        if(this.adminService.loginByPassword(admin)){
            System.out.println("Match Success");
            return "mainPage";
        }
        else{
            System.out.println("Match Error");
            return "userRegister";
        }
    }
    //登录功能
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public boolean isMatch(HttpServletRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getParameter("id"));
        admin.setPassword(request.getParameter("pswd"));
        return this.adminService.loginByPassword(admin);
    }
    //查看背包
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value="backpack",produces={"application/json;charset=UTF-8"})
    public String openBackpack(HttpServletRequest request) {
        String username = "admin";
        String arr = this.adminService.queryCardlist(username);
        String [] strArr= arr.split(",");
        JSONArray jArray = new JSONArray();
        for (String s : strArr) {
            JSONObject card = new JSONObject();
            card.put("id", s);
            card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
            card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
            jArray.put(card);
        }
        JSONObject ans = new JSONObject();
        ans.put("money", this.adminService.queryMoney(username));
        ans.put("bag", jArray);
        return ans.toString();
    }
    //加钱
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value="award",produces={"application/json;charset=UTF-8"})
    public String giveAward(HttpServletRequest request) {
        String username = request.getParameter("username");
        username = "admin";
        int level = Integer.parseInt(request.getParameter("level"));
        this.adminService.addMoney(username,level);
        return "ok";
    }
    //战斗出牌
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value="randomdeck",produces={"application/json;charset=UTF-8"})
    public String createDeck(HttpServletRequest request) {
        int level = Integer.parseInt(request.getParameter("level"));
        String [] cardmap = {"1,2,3","2,3,4","3,4,5","4,5,6","5,5,10"};
        String arr = cardmap[level-1];
        String [] strArr= arr.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(strArr));
        Collections.shuffle(list);
        JSONArray jArray = new JSONArray();
        for (String s : list) {
            JSONObject card = new JSONObject();
            card.put("id", s);
            card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
            card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
            jArray.put(card);
        }
        JSONObject ans = new JSONObject();
        ans.put("deck", jArray);
        return ans.toString();
    }
    //查询商店
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value="store",produces={"application/json;charset=UTF-8"})
    public String openStore(HttpServletRequest request) {
        String username = "admin";
        username = request.getParameter("username");
        String arr = this.adminService.queryStoreCardlist(username);
        JSONArray jArray = new JSONArray();
        if(!arr.equals("")){
            String [] strArr= arr.split(",");
            for (String s : strArr) {
                JSONObject card = new JSONObject();
                card.put("id", s);
                card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
                card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
                jArray.put(card);
            }
        }
        JSONObject ans = new JSONObject();
        ans.put("money", this.adminService.queryMoney(username));
        ans.put("bag", jArray);
        return ans.toString();
    }
    //卖卡
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value="sellcard",produces={"application/json;charset=UTF-8"})
    public String sellCardfromStore(HttpServletRequest request) {
        String username = "admin";
        username = request.getParameter("username");
        String cardid = request.getParameter("cardid");
        if(this.adminService.sellCard(username,Integer.parseInt(cardid))!=1){
            return "error";
        }
        //创建背包结果
        String backpackarr = this.adminService.queryCardlist(username);
        JSONArray jArray = new JSONArray();
        if(!backpackarr.equals("")){
            String [] strArr= backpackarr.split(",");
            for (String s : strArr) {
                JSONObject card = new JSONObject();
                card.put("id", s);
                card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
                card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
                jArray.put(card);
            }
        }
        //创建商店结果
        String storearr = this.adminService.queryStoreCardlist(username);
        JSONArray jArray2 = new JSONArray();
        if(!storearr.equals("")){
            String [] strArr2= storearr.split(",");
            for (String s : strArr2) {
                JSONObject card = new JSONObject();
                card.put("id", s);
                card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
                card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
                jArray2.put(card);
            }
        }
        JSONObject ans = new JSONObject();
        ans.put("bag", jArray);
        ans.put("store", jArray2);
        ans.put("money", this.adminService.queryMoney(username));
        return ans.toString();
    }
    //买卡
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value="buycard",produces={"application/json;charset=UTF-8"})
    public String buyCardfromStore(HttpServletRequest request) {
        String username = "admin";
        username = request.getParameter("username");
        String cardid = request.getParameter("cardid");
        int rescode = this.adminService.buyCard(username,Integer.parseInt(cardid));
        if(rescode != 1){
            if(rescode==-1) return "no money";
            else if(rescode==-2) return "buy error";
            else return "unknown error";
        }
        //创建背包结果
        String backpackarr = this.adminService.queryCardlist(username);
        JSONArray jArray = new JSONArray();
        if(!backpackarr.equals("")){
            String [] strArr= backpackarr.split(",");
            for (String s : strArr) {
                JSONObject card = new JSONObject();
                card.put("id", s);
                card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
                card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
                jArray.put(card);
            }
        }
        //创建商店结果
        String storearr = this.adminService.queryStoreCardlist(username);
        JSONArray jArray2 = new JSONArray();
        if(!storearr.equals("")){
            String [] strArr2= storearr.split(",");
            for (String s : strArr2) {
                JSONObject card = new JSONObject();
                card.put("id", s);
                card.put("cardname", this.adminService.queryCardname(Integer.parseInt(s)));
                card.put("url", this.adminService.queryurl(Integer.parseInt(s)));
                jArray2.put(card);
            }
        }
        JSONObject ans = new JSONObject();
        ans.put("bag", jArray);
        ans.put("store", jArray2);
        ans.put("money", this.adminService.queryMoney(username));
        return ans.toString();
    }

    //todo:查询用户表并用json格式返回
    //@CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping("test")
    public List<Admin> selectStudent(HttpServletResponse response) {
        System.out.println("查询全部学生信息");
        List<Admin> ans = this.adminService.queryAll();
        Cookie cookie = new Cookie("JSessionId", "1234");
        cookie.setPath("/");
        //cookie.setMaxAge(expiry);
//        Jedis jedis = new Jedis("localhost");
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
        response.addCookie(cookie);
        return ans;
    }
}