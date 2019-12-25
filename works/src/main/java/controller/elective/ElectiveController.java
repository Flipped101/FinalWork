package controller.elective;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Elective;
import service.ElectiveService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@WebServlet("/elective.ctl")
public class ElectiveController extends HttpServlet {
    //请使用以下JSON测试增加功能（id为空）
    //{"description":"id为null的新课程","no":"05","remarks":""}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的课程","id":1,"no":"05","remarks":""}

    /**
     * POST, http://49.235.240.61:8080/Flipped/elective.ctl, 增加课程
     * 增加一个课程对象：将来自前端请求的JSON对象，增加到数据库表中
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String student_id_str = request.getParameter("student_id");
        String course_id_str = request.getParameter("course_id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Elective对象
        try {
            int student_id = Integer.parseInt(student_id_str);
            int course_id = Integer.parseInt(course_id_str);
            ElectiveService.getInstance().add(student_id, course_id);
            message.put("message", "增加成功!");
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

    /**
     * DELETE, http://49.235.240.61:8080/Flipped/elective.ctl?id=1, 删除id=1的课程
     * 删除一个课程对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //读取参数id
        String student_id_str = request.getParameter("student_id");
        String course_id_str = request.getParameter("course_id");

        int student_id = Integer.parseInt(student_id_str);
        int course_id = Integer.parseInt(course_id_str);

        //到数据库表中删除对应的课程
        try {
            boolean deleted = ElectiveService.getInstance().delete(student_id, course_id);
            if (deleted) {
                message.put("message", "退选成功!");
            } else {
                message.put("message", "此课程已被退选或未选择!");
            }
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

    /**
     * GET, http://49.235.240.61:8080/Flipped/elective.ctl?id=1, 查询id=1的课程
     * GET, http://49.235.240.61:8080/Flipped/elective.ctl, 查询所有的课程
     * 把一个或所有课程对象响应到前端
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //读取参数id
        String student_id_str = request.getParameter("student_id");
        //读取参数id
        String course_id_str = request.getParameter("course_id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        try {
            if (student_id_str != null) {
                int student_id = Integer.parseInt(student_id_str);
                responseElectives(student_id, response);
            } else {
                int course_id = Integer.parseInt(course_id_str);
                responseElectivesByCourse(course_id, response);
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

    //响应所有课程对象
    private void responseElectives(int student_id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有课程
        Collection<Elective> electives = ElectiveService.getInstance().findAll(student_id);
        String electives_json = JSON.toJSONString(electives, SerializerFeature.DisableCircularReferenceDetect);

        //响应electives_json到前端
        response.getWriter().println(electives_json);
    }

    //响应所有课程对象
    private void responseElectivesByCourse(int course_id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有课程
        Collection<Elective> electives = ElectiveService.getInstance().findAllByCourse(course_id);
        String electives_json = JSON.toJSONString(electives, SerializerFeature.DisableCircularReferenceDetect);

        //响应electives_json到前端
        response.getWriter().println(electives_json);
    }
}
