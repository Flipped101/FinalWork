package controller.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //读取参数id
        String paraType_str = request.getParameter("paraType");
        //根据request对象，获得代表参数的JSON字串
        String User_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Teacher对象
        User userToCheck = JSON.parseObject(User_json, User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表匹配User对象对应的记录
        try {
            if (paraType_str.equals("teacher")) {
                User loginUser = UserService.getInstance().loginTeacher(userToCheck.getUsername(), userToCheck.getPassword());
                if (loginUser == null) {
                    message.put("message", "用户名或密码错误!");
                    //响应message到前端
                    response.getWriter().println(message);
                } else {
                    HttpSession session = request.getSession();
                    //如果session不活跃时间大于指定时间，则该session失败
                    session.setMaxInactiveInterval(30 * 60);
                    //设置session的属性值
                    session.setAttribute("currentUser", userToCheck);
                    UserService.getInstance().updateTeacherDate(userToCheck.getUsername());
                    responseUserByTeacherUserName(userToCheck.getUsername(), response);
                }
            } else if (paraType_str.equals("student")) {
                User loginUser = UserService.getInstance().loginStudent(userToCheck.getUsername(), userToCheck.getPassword());
                if (loginUser == null) {
                    message.put("message", "用户名或密码错误!");
                    //响应message到前端
                    response.getWriter().println(message);
                } else {
                    HttpSession session = request.getSession();
                    //如果session不活跃时间大于指定时间，则该session失败
                    session.setMaxInactiveInterval(30 * 60);
                    //设置session的属性值
                    session.setAttribute("currentUser", userToCheck);
                    UserService.getInstance().updateStudentDate(userToCheck.getUsername());
                    responseUserByStudentUserName(userToCheck.getUsername(), response);
                }
            } else {
                User loginUser = UserService.getInstance().loginAdministrator(userToCheck.getUsername(), userToCheck.getPassword());
                if (loginUser == null) {
                    message.put("message", "用户名或密码错误!");
                    //响应message到前端
                    response.getWriter().println(message);
                } else {
                    HttpSession session = request.getSession();
                    //如果session不活跃时间大于指定时间，则该session失败
                    session.setMaxInactiveInterval(30 * 60);
                    //设置session的属性值
                    session.setAttribute("currentUser", userToCheck);
                    UserService.getInstance().updateAdministratorDate(userToCheck.getUsername());
                    responseUserByAdministratorUserName(userToCheck.getUsername(), response);
                }
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常!");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }

    }

    //通过userName响应一个用户对象
    private void responseUserByAdministratorUserName(String userName, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据userName查找用户
        User user = UserService.getInstance().findUserByAdministratorUserName(userName);
        String user_json = JSON.toJSONString(user);
        //响应user_json到前端
        response.getWriter().println(user_json);
    }

    //通过userName响应一个用户对象
    private void responseUserByStudentUserName(String userName, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据userName查找用户
        User user = UserService.getInstance().findUserByStudentUserName(userName);
        String user_json = JSON.toJSONString(user);
        //响应user_json到前端
        response.getWriter().println(user_json);
    }

    //通过userName响应一个用户对象
    private void responseUserByTeacherUserName(String userName, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据userName查找用户
        User user = UserService.getInstance().findUserByTeacherUserName(userName);
        String user_json = JSON.toJSONString(user);
        //响应user_json到前端
        response.getWriter().println(user_json);
    }

}