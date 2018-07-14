package xin.wangning.servlet;

import cn.itcast.commons.CommonUtils;
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
            session.setAttribute("user",user);
            return "success";
        }else {
            return "fail";
        }
    }
    public String getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            return "fail";
        }
        return user.getUsername();
    }

    public String deleteUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
        return "success";
    }

}
