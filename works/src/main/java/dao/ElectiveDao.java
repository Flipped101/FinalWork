package dao;

import domain.Course;
import domain.Elective;
import domain.Student;
import service.CourseService;
import service.StudentService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;

public final class ElectiveDao {
    //本类的一个对象引用，保存自身对象
    private static ElectiveDao electiveDao = new ElectiveDao();

    //私有的构造方法，防止其它类创建它的对象
    private ElectiveDao() {
    }

    //静态方法，返回本类的惟一对象
    public static ElectiveDao getInstance() {
        return electiveDao;
    }

    public Collection<Elective> findAll(int student_id) throws SQLException {
        //创建Elective类型的集合对象
        Set<Elective> electives = new HashSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SC WHERE STUDENT_ID = ?");
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, student_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Student student = StudentService.getInstance().find(resultSet.getInt("student_id"));
            Course course = CourseService.getInstance().find(resultSet.getInt("course_id"));
            //创建Elective对象，根据遍历结果中的id,title,graduateProjectCategory,graduateProjectType,graduateProjectStatus,teacher字段
            Elective elective = new Elective(student, course);
            //向electives集合中添加Elective对象
            electives.add(elective);
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        //返回graduateProjects集合
        return electives;
    }

    public Collection<Elective> findAllByCourse(int course_id) throws SQLException {
        //创建Elective类型的集合对象
        Set<Elective> electives = new HashSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SC WHERE COURSE_ID = ?");
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, course_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Student student = StudentService.getInstance().find(resultSet.getInt("student_id"));
            Course course = CourseService.getInstance().find(resultSet.getInt("course_id"));
            //创建Elective对象，根据遍历结果中的id,title,graduateProjectCategory,graduateProjectType,graduateProjectStatus,teacher字段
            Elective elective = new Elective(student, course);
            //向electives集合中添加Elective对象
            electives.add(elective);
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        //返回graduateProjects集合
        return electives;
    }

    public boolean add(Integer student_id, Integer course_id) throws SQLException {
        int countNum = 0;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        preparedStatement = connection.prepareStatement(
                "INSERT INTO SC (STUDENT_ID,COURSE_ID) VALUES" +
                        "(?,?)");
        preparedStatement.setInt(1, student_id);
        preparedStatement.setInt(2, course_id);
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectRowNum = preparedStatement.executeUpdate();
        System.out.println("增加了 " + affectRowNum + " 行。");

        //创建sql语句，“？”作为占位符
        String str = "SELECT COURSE_ID, COUNT(*) AS COUNT FROM SC GROUP BY COURSE_ID HAVING COURSE_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        preparedStatement = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, course_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            countNum = resultSet.getInt("count");
        }

        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        preparedStatement = connection.prepareStatement("UPDATE COURSE SET COUNTNUM = ? WHERE ID = ?");
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, countNum);
        preparedStatement.setInt(2, course_id);
        preparedStatement.executeUpdate();

        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectRowNum > 0;
    }

    public boolean delete(Integer student_id, Integer course_id) throws SQLException {
        int countNum = 0;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        //创建sql语句，“？”作为占位符
        String deleteElective_sql = "DELETE FROM SC WHERE  STUDENT_ID= ? AND COURSE_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        preparedStatement = connection.prepareStatement(deleteElective_sql);
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, student_id);
        preparedStatement.setInt(2, course_id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = preparedStatement.executeUpdate();
        System.out.println("删除了 " + affectedRowNum + " 行");

        //创建sql语句，“？”作为占位符
        String str = "SELECT COURSE_ID, COUNT(*) AS COUNT FROM SC GROUP BY COURSE_ID HAVING COURSE_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        preparedStatement = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, course_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            countNum = resultSet.getInt("count");
        }

        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        preparedStatement = connection.prepareStatement("UPDATE COURSE SET COUNTNUM = ? WHERE ID = ?");
        //为预编译的语句参数赋值
        preparedStatement.setInt(1, countNum);
        preparedStatement.setInt(2, course_id);
        preparedStatement.executeUpdate();

        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRowNum > 0;
    }

}

