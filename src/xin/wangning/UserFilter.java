package xin.wangning;

import xin.wangning.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserFilter implements Filter {

    public static Map<String,String> userSessionIdMap = new HashMap<String,String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            servletResponse = (HttpServletResponse)servletResponse;
            ((HttpServletResponse) servletResponse).sendRedirect("../login.html");
        }else if(!session.getId().equals(userSessionIdMap.get(user.getUsername()))) {
            ((HttpServletResponse) servletResponse).sendRedirect("../login.html");
        }else {
            filterChain.doFilter(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
