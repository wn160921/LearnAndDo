package xin.wangning.servlet;

import cn.itcast.commons.CommonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xin.wangning.UserFilter;
import xin.wangning.domain.User;
import xin.wangning.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserServlet extends BaseServlet {
    private UserService service = new UserService();
    public String Login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = CommonUtils.toBean(req.getParameterMap(),User.class);
        if(user.getUsername()==null){
            return "参数不足";
        }else if(user.getPassword()==null){
            return "参数不足";
        }
        boolean result = service.checkLogin(user.getUsername(),user.getPassword());
        if(result){
            HttpSession session = req.getSession();
            UserFilter.userSessionIdMap.put(user.getUsername(),session.getId());
            session.setAttribute("user",user);
            return "success";
        }else {
            return "fail";
        }
    }
    public String getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        System.out.println("输出用户信息");
        if(user==null){
            return "fail";
        }
        return user.getUsername();
    }

    public String deleteUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("删除用户信息");
        req.getSession().removeAttribute("user");
        return "success";
    }

    public String checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(session.getId().equals(UserFilter.userSessionIdMap.get(user.getUsername()))){
            return "isIn";
        }else {
            return "notIn";
        }
    }

    public String startLinux(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "找不到用户";
        }
        try
        {
            String username = user.getUsername();
            String port = username.substring(5,10);
            String url = "http://127.0.0.1:5000/start?name="+username+"&port="+port;
            Document document = Jsoup.connect(url).get();
            String result = document.text();
            System.out.println("linux:"+result);
            return port;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "fail";
    }

    public String closeLinux(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "找不到用户";
        }
        try
        {
            String username = user.getUsername();
            String url = "http://127.0.0.1:5000/close?name="+username;
            Document document = Jsoup.connect(url).get();
            return document.html();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "fail";
    }

}
