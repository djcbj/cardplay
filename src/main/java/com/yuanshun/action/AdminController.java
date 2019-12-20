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
    //@CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public boolean isMatch(HttpServletRequest request) {
        Admin admin = new Admin();
        admin.setUsername(request.getParameter("id"));
        admin.setPassword(request.getParameter("pswd"));
        return this.adminService.loginByPassword(admin);
    }

    //@CrossOrigin(origins = "*", maxAge = 3600)
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