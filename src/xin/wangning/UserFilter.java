package xin.wangning;

import xin.wangning.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            servletResponse = (HttpServletResponse)servletResponse;
            ((HttpServletResponse) servletResponse).sendRedirect("../login.html");
        }else {
            filterChain.doFilter(request, servletResponse);

        }
    }

    @Override
    public void destroy() {

    }
}
