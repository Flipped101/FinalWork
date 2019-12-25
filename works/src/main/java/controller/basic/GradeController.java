package controller.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Grade;
import service.GradeService;
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
@WebServlet("/grade.ctl")
public class GradeController extends HttpServlet {
    //请使用以下JSON测试增加功能（id为空）
    //{"description":"id为null新班级","no":"0201","remarks":"","department":{"description":"信息管理","id":2,"no":"02","remarks":""}}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的班级","id":1,"no":"0201","remarks":"","department":{"description":"信息管理","id":2,"no":"02","remarks":""}}

    /**
     * POST, http://49.235.240.61:8080/Flipped/grade.ctl, 增加班级
     * 增加一个班级对象：将来自前端请求的JSON对象，增加到数据库表中
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
        String grade_json = JSONUtil.getJSON(request);

        //将JSON字串解析为Grade对象
        Grade gradeToAdd = JSON.parseObject(grade_json, Grade.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Grade对象
        try {
            GradeService.getInstance().add(gradeToAdd);
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
     * DELETE, http://49.235.240.61:8080/Flipped/grade.ctl?id=1, 删除id=1的班级
     * 删除一个班级对象：根据来自前端请求的id，删除数据库表中id的对应记录
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

        //到数据库表中删除对应的班级
        try {
            boolean deleted = GradeService.getInstance().delete(id);
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
     * PUT, http://49.235.240.61:8080/Flipped/grade.ctl, 修改班级
     * <p>
     * 修改一个班级对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String grade_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Grade对象
        Grade gradeToAdd = JSON.parseObject(grade_json, Grade.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Grade对象对应的记录
        try {
            GradeService.getInstance().update(gradeToAdd);
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
     * GET, http://49.235.240.61:8080/Flipped/grade.ctl?id=1, 查询id=1的班级
     * GET, http://49.235.240.61:8080/Flipped/grade.ctl, 查询所有的班级
     * 把一个或所有班级对象响应到前端
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
        String department_id_str = request.getParameter("department_id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有班级对象，否则响应id指定的班级对象
            if (id_str == null && department_id_str == null) {
                responseGrades(response);
            } else if (department_id_str != null) {
                int department_id = Integer.parseInt(department_id_str);
                responseGradeByDepartment(department_id, response);
            } else {
                int id = Integer.parseInt(id_str);
                responseGrade(id, response);
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

    //响应一个班级对象
    private void responseGrade(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找班级
        Grade grade = GradeService.getInstance().find(id);
        String grade_json = JSON.toJSONString(grade);

        //响应grade_json到前端
        response.getWriter().println(grade_json);
    }

    //响应所有班级对象
    private void responseGrades(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有班级
        Collection<Grade> grades = GradeService.getInstance().findAll();
        String grades_json = JSON.toJSONString(grades, SerializerFeature.DisableCircularReferenceDetect);

        //响应grades_json到前端
        response.getWriter().println(grades_json);
    }

    private void responseGradeByDepartment(int department_id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得对应的所有班级
        Collection<Grade> grades = GradeService.getInstance().findAllByDepartment(department_id);
        String grades_json = JSON.toJSONString(grades, SerializerFeature.DisableCircularReferenceDetect);

        //响应grades_json到前端
        response.getWriter().println(grades_json);
    }
}
