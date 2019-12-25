package controller.administrator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Administrator;
import service.AdministratorService;
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
@WebServlet("/administrator.ctl")
public class AdministratorController extends HttpServlet {
    //请使用以下JSON测试增加功能（id为空）
    //{"description":"id为null的新管理员","no":"05","remarks":""}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的管理员","id":1,"no":"05","remarks":""}

    /**
     * POST, http://49.235.240.61:8080/Flipped/administrator.ctl, 增加管理员
     * 增加一个管理员对象：将来自前端请求的JSON对象，增加到数据库表中
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
        String administrator_json = JSONUtil.getJSON(request);

        //将JSON字串解析为Administrator对象
        Administrator administratorToAdd = JSON.parseObject(administrator_json, Administrator.class);

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Administrator对象
        boolean added = AdministratorService.getInstance().add(administratorToAdd);
        if (added) {
            message.put("message", "增加成功!");
        } else {
            message.put("message", "增加失败!");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * DELETE, http://49.235.240.61:8080/Flipped/administrator.ctl?id=1, 删除id=1的管理员
     * 删除一个管理员对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);

        //到数据库表中删除对应的管理员
        boolean deleted = AdministratorService.getInstance().delete(id);
        if (deleted) {
            message.put("message", "删除成功!");
        } else {
            message.put("message", "此条记录已被删除或不存在!");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * PUT, http://49.235.240.61:8080/Flipped/administrator.ctl, 修改管理员
     * <p>
     * 修改一个管理员对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String administrator_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Administrator对象
        Administrator administratorToAdd = JSON.parseObject(administrator_json, Administrator.class);

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Administrator对象对应的记录
        try {
            boolean updated = AdministratorService.getInstance().update(administratorToAdd);
            if (updated) {
                message.put("message", "修改成功!");
            } else {
                message.put("message", "修改失败!");
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
     * GET, http://49.235.240.61:8080/Flipped/administrator.ctl?id=1, 查询id=1的管理员
     * GET, http://49.235.240.61:8080/Flipped/administrator.ctl, 查询所有的管理员
     * 把一个或所有管理员对象响应到前端
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

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        try {
            //如果id = null, 表示响应所有管理员对象，否则响应id指定的管理员对象
            if (id_str == null) {
                responseAdministrators(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseAdministrator(id, response);
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

    //响应一个管理员对象
    private void responseAdministrator(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找管理员
        Administrator administrator = AdministratorService.getInstance().find(id);
        String administrator_json = JSON.toJSONString(administrator);

        //响应administrator_json到前端
        response.getWriter().println(administrator_json);
    }

    //响应所有管理员对象
    private void responseAdministrators(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有管理员
        Collection<Administrator> administrators = AdministratorService.getInstance().findAll();
        String administrators_json = JSON.toJSONString(administrators, SerializerFeature.DisableCircularReferenceDetect);

        //响应administrators_json到前端
        response.getWriter().println(administrators_json);
    }
}
