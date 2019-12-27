package dao;

import domain.Course;
import domain.Teacher;
import service.TeacherService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class CourseDao {
    //本类的一个对象引用，保存自身对象
    private static CourseDao courseDao = new CourseDao();

    //私有的构造方法，防止其它类创建它的对象
    private CourseDao() {
    }

    //静态方法，返回本类的惟一对象
    public static CourseDao getInstance() {
        return courseDao;
    }

    public Collection<Course> findAll() throws SQLException {
        //创建Course类型的集合对象
        Set<Course> courses = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSE");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建Course对象，根据遍历结果中的id,description,no,remarks值
            Course course = new Course(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getInt("credit"), teacher, resultSet.getString("category"),
                    resultSet.getString("time"), resultSet.getString("place"), resultSet.getInt("countNum"));
            //向courses集合中添加Course对象
            courses.add(course);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回courses集合
        return courses;
    }

    public Collection<Course> findAllByDes(String des) throws SQLException {
        //创建Course类型的集合对象
        Set<Course> courses = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM COURSE where description like ?");
        //为预编译的语句参数赋值
        preparedStatement.setString(1, "%" + des + "%");

        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建Course对象，根据遍历结果中的id,description,no,remarks值
            Course course = new Course(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getInt("credit"), teacher, resultSet.getString("category"),
                    resultSet.getString("time"), resultSet.getString("place"), resultSet.getInt("countNum"));
            //向courses集合中添加Course对象
            courses.add(course);
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        //返回courses集合
        return courses;
    }

    public Course find(Integer id) throws SQLException {
        //创建Course类型变量
        Course course = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM COURSE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建Course对象，根据遍历结果中的id,description,no,remarks值
            course = new Course(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getInt("credit"), teacher, resultSet.getString("category"),
                    resultSet.getString("time"), resultSet.getString("place"), resultSet.getInt("countNum"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Course对象
        return course;
    }

    public Collection<Course> findAllByTeacher(Integer teacher_id) throws SQLException {
        //创建Course类型的集合对象
        Set<Course> courses = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM COURSE WHERE TEACHER_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, teacher_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建Course对象，根据遍历结果中的id,description,no,remarks值
            Course course = new Course(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getInt("credit"), teacher, resultSet.getString("category"),
                    resultSet.getString("time"), resultSet.getString("place"), resultSet.getInt("countNum"));
            //向departemnts集合中添加Course对象
            courses.add(course);
        }
        //若结果集仍然有下一条记录，则执行循环体
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回courses集合
        return courses;
    }

    public Integer Count(Integer course_id) throws SQLException {
        int countNum = 0;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT COURSE_ID, COUNT(*) AS COUNT FROM SC GROUP BY COURSE_ID HAVING COURSE_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, course_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            countNum = resultSet.getInt("count");
        }
        return countNum;
    }

    public boolean add(Course course) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO COURSE (NO,DESCRIPTION,CREDIT,CATEGORY,TIME,PLACE,TEACHER_ID) VALUES" +
                        "(?,?,?,?,?,?,?)");
        preparedStatement.setString(1, course.getNo());
        preparedStatement.setString(2, course.getDescription());
        preparedStatement.setInt(3, course.getCredit());
        preparedStatement.setString(4, course.getCategory());
        preparedStatement.setString(5, course.getTime());
        preparedStatement.setString(6, course.getPlace());
        preparedStatement.setInt(7, course.getTeacher().getId());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectRowNum = preparedStatement.executeUpdate();
        System.out.println("增加了 " + affectRowNum + " 行。");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectRowNum > 0;
    }

    public boolean delete(Integer id) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String deleteCourse_sql = "DELETE FROM COURSE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(deleteCourse_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean update(Course course) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateCourse_sql = "UPDATE COURSE SET DESCRIPTION = ?, NO = ? ,CREDIT = ?, CATEGORY = ?, TIME = ?, PLACE = ?, TEACHER_ID = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateCourse_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, course.getDescription());
        pstmt.setString(2, course.getNo());
        pstmt.setInt(3, course.getCredit());
        pstmt.setString(4, course.getCategory());
        pstmt.setString(5, course.getTime());
        pstmt.setString(6, course.getPlace());
        pstmt.setInt(7, course.getTeacher().getId());
        pstmt.setInt(8, course.getId());
        //执行预编译对象的executeUpdate()方法，获取更新记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

