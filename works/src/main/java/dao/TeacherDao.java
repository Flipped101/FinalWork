package dao;

import domain.Degree;
import domain.Department;
import domain.ProfTitle;
import domain.Teacher;
import service.DegreeService;
import service.DepartmentService;
import service.ProfTitleService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class TeacherDao {
    //本类的一个对象引用，保存自身对象
    private static TeacherDao teacherDao = new TeacherDao();

    //私有的构造方法，防止其它类创建它的对象
    private TeacherDao() {
    }

    //静态方法，返回本类的惟一对象
    public static TeacherDao getInstance() {
        return teacherDao;
    }

    public Collection<Teacher> findAll() throws SQLException {
        //创建Teacher类型的集合对象
        Set<Teacher> teachers = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TEACHER");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            ProfTitle profTitle = ProfTitleService.getInstance().find(resultSet.getInt("profTitle_id"));
            Degree degree = DegreeService.getInstance().find(resultSet.getInt("degree_id"));
            Department department = DepartmentService.getInstance().find(resultSet.getInt("department_id"));
            //创建Teacher对象，根据遍历结果中的id,profTitle,no,degree,department字段
            Teacher teacher = new Teacher(resultSet.getInt("id"), resultSet.getString("no"),
                    resultSet.getString("name"),
                    profTitle, degree, department, resultSet.getString("phone"));
            //向teachers集合中添加Teacher对象
            teachers.add(teacher);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回teachers集合
        return teachers;
    }

    public Teacher find(Integer id) throws SQLException {
        //创建Teacher类型变量
        Teacher teacher = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM TEACHER WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            ProfTitle profTitle = ProfTitleService.getInstance().find(resultSet.getInt("profTitle_id"));
            Degree degree = DegreeService.getInstance().find(resultSet.getInt("degree_id"));
            Department department = DepartmentService.getInstance().find(resultSet.getInt("department_id"));
            //创建Teacher对象，根据遍历结果中的id,profTitle,no,degree,department字段
            teacher = new Teacher(resultSet.getInt("id"), resultSet.getString("no"),
                    resultSet.getString("name"),
                    profTitle, degree, department, resultSet.getString("phone"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Teacher对象
        return teacher;
    }

    public boolean update(Teacher teacher) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateTeacher_sql = "UPDATE TEACHER SET NO = ?, NAME=?, PROFTITLE_ID=?,DEGREE_ID=?, DEPARTMENT_ID=?, PHONE = ? WHERE ID=?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(updateTeacher_sql);
        //为预编译参数赋值
        preparedStatement.setString(1, teacher.getNo());
        preparedStatement.setString(2, teacher.getName());
        preparedStatement.setInt(3, teacher.getTitle().getId());
        preparedStatement.setInt(4, teacher.getDegree().getId());
        preparedStatement.setInt(5, teacher.getDepartment().getId());
        preparedStatement.setString(6, teacher.getPhone());
        preparedStatement.setInt(7, teacher.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println(affectedRows);
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }

    public boolean add(Teacher teacher) {
        //创建Connection类型变量
        Connection connection = null;
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        int affectUserRows = 0;
        int affectTeacherRows = 0;
        int teacher_id = 0;
        try {
            //加载驱动程序
            //获得连接对象
            connection = JdbcHelper.getConn();
            //事务开始
            connection.setAutoCommit(false);
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO TEACHER (NO,NAME,PROFTITLE_ID,DEGREE_ID,DEPARTMENT_ID,PHONE) VALUES" +
                            "(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, teacher.getNo());
            preparedStatement.setString(2, teacher.getName());
            preparedStatement.setInt(3, teacher.getTitle().getId());
            preparedStatement.setInt(4, teacher.getDegree().getId());
            preparedStatement.setInt(5, teacher.getDepartment().getId());
            preparedStatement.setString(6, teacher.getPhone());
            //执行预编译对象的executeUpdate()方法，获取增加记录的行数
            affectTeacherRows = preparedStatement.executeUpdate();
            System.out.println("Teacher Table 增加了 " + affectTeacherRows + " 行。");

            //查询刚刚插入数据的主键并获得结果集对象（游标指向结果集的开头）
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            //若结果集仍然有下一条记录，则执行循环体
            if (resultSet.next()) {
                //将查询到的主键值赋值给teacher_id
                teacher_id = resultSet.getInt(1);
            }
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO TEACHERUSER (USERNAME,PASSWORD,TEACHER_ID) VALUES" +
                            "(?,?,?)");
            preparedStatement.setString(1, teacher.getNo());
            preparedStatement.setString(2, teacher.getNo());
            preparedStatement.setInt(3, teacher_id);
            //执行预编译对象的executeUpdate()方法，获取增加记录的行数
            affectUserRows = preparedStatement.executeUpdate();
            System.out.println("User Table 增加了 " + affectUserRows + " 行。");

            //提交当前连接所做的操作
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n errorCode = " + e.getErrorCode());
            try {
                //回滚当前连接所做的操作
                if (connection != null) {
                    //事务已回滚的方式结束
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                //恢复自动提交
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(preparedStatement, connection);
        }
        return affectTeacherRows > 0 && affectUserRows > 0;
    }

    public boolean delete(Integer id) {
        //创建Connection类型变量
        Connection connection = null;
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        int affectUserRows = 0;
        int affectTeacherRows = 0;
        try {
            //加载驱动程序
            //获得连接对象
            connection = JdbcHelper.getConn();
            //事务开始
            connection.setAutoCommit(false);
            String deleteUser_sql = "DELETE FROM TEACHERUSER WHERE TEACHER_ID = ?";
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(deleteUser_sql);
            //为预编译的语句参数赋值
            preparedStatement.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectUserRows = preparedStatement.executeUpdate();
            System.out.println("User Table 删除了 " + affectUserRows + " 行");

            //创建sql语句，“？”作为占位符
            String deleteTeacher_sql = "DELETE FROM TEACHER WHERE ID = ?";
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(deleteTeacher_sql);
            //为预编译的语句参数赋值
            preparedStatement.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectTeacherRows = preparedStatement.executeUpdate();
            System.out.println("Teacher Table 删除了 " + affectTeacherRows + " 行");

            //提交当前连接所做的操作
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n errorCode = " + e.getErrorCode());
            try {
                //回滚当前连接所做的操作
                if (connection != null) {
                    //事务已回滚的方式结束
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                //回恢复自动提交
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(preparedStatement, connection);
        }
        return affectTeacherRows > 0 && affectUserRows > 0;
    }

}
