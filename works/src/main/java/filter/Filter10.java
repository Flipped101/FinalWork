package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter(filterName = "Filter10", urlPatterns = "/*")//对所有资源进行过滤
public class Filter10 implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //过滤器开始
        System.out.println("Filter 10 begins...");

        //将ServletRequest强转为HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) req;

        //得到请求的路径
        String path = request.getRequestURI();

        //设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        //创建Date对象
        Date currentTime = new Date();

        //转化时间格式
        String time = sdf.format(currentTime);

        System.out.println(path + " @ " + time);
        //执行其他过滤器，若过滤器已执行完毕，则执行请求
        chain.doFilter(req, resp);

        System.out.println("Filter 10 ends.");
        //过滤器结束
    }

}
