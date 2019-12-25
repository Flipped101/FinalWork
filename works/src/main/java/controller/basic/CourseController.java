package controller.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Course;
import service.CourseService;
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
@WebServlet("/course.ctl")
public class CourseController extends HttpServlet {
    //请使用以下JSON测试增加功能（id为空）
    //{"description":"id为null新课程","no":"0201","remarks":"","teacher":{"description":"管理工程","id":2,"no":"02","remarks":"最好的学院"}}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的课程","id":1,"no":"0201","remarks":"","teacher":{"description":"管理工程","id":2,"no":"02","remarks":"最好的学院"}}

    /**
     * POST, http://49.235.240.61:8080/Flipped/course.ctl, 增加课程
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

        //根据request对象，获得代表参数的JSON字串
        String course_json = JSONUtil.getJSON(request);

        //将JSON字串解析为Course对象
        Course courseToAdd = JSON.parseObject(course_json, Course.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Course对象
        try {
            CourseService.getInstance().add(courseToAdd);
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
     * DELETE, http://49.235.240.61:8080/Flipped/course.ctl?id=1, 删除id=1的课程
     * 删除一个课程对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的课程
        try {
            boolean deleted = CourseService.getInstance().delete(id);
            if (deleted) {
                message.put("message", "删除成功!");
            } else {
                message.put("message", "此条记录已被删除或不存在!");
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
     * PUT, http://49.235.240.61:8080/Flipped/course.ctl, 修改课程
     * <p>
     * 修改一个课程对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String course_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Course对象
        Course courseToAdd = JSON.parseObject(course_json, Course.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Course对象对应的记录
        try {
            CourseService.getInstance().update(courseToAdd);
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

    /**
     * GET, http://49.235.240.61:8080/Flipped/course.ctl?id=1, 查询id=1的课程
     * GET, http://49.235.240.61:8080/Flipped/course.ctl, 查询所有的课程
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
        String id_str = request.getParameter("id");
        String teacher_id_str = request.getParameter("teacher_id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有课程对象，否则响应id指定的课程对象
            if (id_str == null && teacher_id_str == null) {
                responseCourses(response);
            } else if (teacher_id_str != null) {
                int teacher_id = Integer.parseInt(teacher_id_str);
                responseCourseByTeacher(teacher_id, response);
            } else {
                int id = Integer.parseInt(id_str);
                responseCourse(id, response);
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

    //响应一个课程对象
    private void responseCourse(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找课程
        Course course = CourseService.getInstance().find(id);
        String course_json = JSON.toJSONString(course);

        //响应course_json到前端
        response.getWriter().println(course_json);
    }

    //响应所有课程对象
    private void responseCourses(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有课程
        Collection<Course> courses = CourseService.getInstance().findAll();
        String courses_json = JSON.toJSONString(courses, SerializerFeature.DisableCircularReferenceDetect);

        //响应courses_json到前端
        response.getWriter().println(courses_json);
    }

    private void responseCourseByTeacher(int teacher_id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得对应的所有课程
        Collection<Course> courses = CourseService.getInstance().findAllByTeacher(teacher_id);
        String courses_json = JSON.toJSONString(courses, SerializerFeature.DisableCircularReferenceDetect);

        //响应courses_json到前端
        response.getWriter().println(courses_json);
    }

}
