package controller.basic;

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
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数
        String paraType = request.getParameter("paraType");
        String id_str = request.getParameter("id");
        String userName_str = request.getParameter("username");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            if (paraType.equals("teacher")) {
                //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
                if (id_str == null) {
                    responseUserByTeacherUserName(userName_str, response);
                } else {
                    int id = Integer.parseInt(id_str);
                    responseUserByTeacherId(id, response);
                }
            } else if (paraType.equals("student")) {
                //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
                if (id_str == null) {
                    responseUserByStudentUserName(userName_str, response);
                } else {
                    int id = Integer.parseInt(id_str);
                    responseUserByStudentId(id, response);
                }
            } else {
                //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
                if (id_str == null) {
                    responseUserByAdministratorUserName(userName_str, response);
                } else {
                    int id = Integer.parseInt(id_str);
                    responseUserByAdministratorId(id, response);
                }
            }

        } catch (SQLException e) {
            message.put("message", "数据库操作异常!");
            e.printStackTrace();

            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常!");
            e.printStackTrace();

            //响应message到前端
            response.getWriter().println(message);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数
        String paraType = request.getParameter("paraType");
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为User对象
        User userToChange = JSON.parseObject(user_json, User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改User对象对应的记录
        try {
            if (paraType.equals("teacher")) {
                UserService.getInstance().changeTeacherPassword(userToChange);
            } else if (paraType.equals("student")) {
                UserService.getInstance().changeStudentPassword(userToChange);
            } else {
                UserService.getInstance().changeAdministratorPassword(userToChange);
            }
            message.put("message", "修改成功!");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常!");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常!");
            e.printStackTrace();
        }

        //响应message到前端
        response.getWriter().println(message);
    }

    //通过id响应一个用户对象
    private void responseUserByTeacherId(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找用户
        User user = UserService.getInstance().findUserByTeacherId(id);
        String user_json = JSON.toJSONString(user);
        //响应user_json到前端
        response.getWriter().println(user_json);
    }

    //通过id响应一个用户对象
    private void responseUserByStudentId(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找用户
        User user = UserService.getInstance().findUserByStudentId(id);
        String user_json = JSON.toJSONString(user);
        //响应user_json到前端
        response.getWriter().println(user_json);
    }

    //通过id响应一个用户对象
    private void responseUserByAdministratorId(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找用户
        User user = UserService.getInstance().findUserByAdministratorId(id);
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
    private void responseUserByAdministratorUserName(String userName, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据userName查找用户
        User user = UserService.getInstance().findUserByAdministratorUserName(userName);
        String user_json = JSON.toJSONString(user);
        //响应user_json到前端
        response.getWriter().println(user_json);
    }

}
