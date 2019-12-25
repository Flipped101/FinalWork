package filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//注解/过滤器名称/该过滤器对所有请求有效
//@WebFilter(filterName = "Filter 20", urlPatterns = "/*")
public class Filter20 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //过滤器开始
        System.out.println("Filter 20 begins...");

        //强制类型转换成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //强制类型转换成HttpServletRequest类型
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获得请求的urI
        String path = request.getRequestURI();

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        if (!path.contains("/login.administrator") && !path.contains("/login.teacher") && !path.contains("/login.student")
                && !path.contains("/logout") && !path.contains("/MyApp")) {

            //访问权限验证
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("currentUser") == null) {
                message.put("message", "请先登录或重新登录!");

                //响应message到前端
                response.getWriter().println(message);

                response.sendRedirect("/MyApp");
            }
        }
        //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("Filter 20 ends");
        //过滤器结束
    }

}
