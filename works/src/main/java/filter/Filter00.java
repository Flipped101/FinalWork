package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter00", urlPatterns = "/*")//对所有资源进行过滤
public class Filter00 implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //过滤器开始
        System.out.println("Filter 00 begins...");

        //将ServletRequest强转为HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) req;

        //将ServletResponse强转为HttpServletResponse
        HttpServletResponse response = (HttpServletResponse) resp;

        //得到请求的方法路径
        String path = request.getRequestURI();

        //得到请求的方法名
        String method = request.getMethod();

        if (!path.contains("/MyApp")) {

            if ("POST-PUT".contains(method)) {
                //设置请求字符编码为UTF-8
                request.setCharacterEncoding("UTF-8");
            }

            //设置响应字符编码为UTF-8
            response.setContentType("text/html;charset=UTF-8");
        }

        //执行其他过滤器，若过滤器已执行完毕，则执行请求
        chain.doFilter(req, resp);

        System.out.println("Filter 00 ends.");
        //过滤器结束
    }

}
