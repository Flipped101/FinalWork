package dao;

import domain.Administrator;
import domain.Student;
import domain.Teacher;
import domain.User;
import service.AdministratorService;
import service.StudentService;
import service.TeacherService;
import util.JdbcHelper;

import java.sql.*;


public final class UserDao {
    //本类的一个对象引用，保存自身对象
    private static UserDao userDao = new UserDao();

    //私有的构造方法，防止其它类创建它的对象
    private UserDao() {
    }

    //静态方法，返回本类的惟一对象
    public static UserDao getInstance() {
        return userDao;
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------------*/

    public User loginTeacher(String username, String password) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM TEACHERUSER WHERE USERNAME = ? AND PASSWORD = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), teacher, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    public User loginStudent(String username, String password) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM STUDENTUSER WHERE USERNAME = ? AND PASSWORD = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Student student = StudentService.getInstance().find(resultSet.getInt("student_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), student, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    public User loginAdministrator(String username, String password) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM ADMINISTRATORUSER WHERE USERNAME = ? AND PASSWORD = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Administrator administrator = AdministratorService.getInstance().find(resultSet.getInt("administrator_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), administrator, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------------*/

    public boolean updateTeacherDate(String username) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "UPDATE TEACHERUSER SET LOGINTIME = CURRENT_TIMESTAMP WHERE USERNAME = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        pstmt.setString(1, username);
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        return affectedRowNum > 0;
    }

    public boolean updateStudentDate(String username) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "UPDATE STUDENTUSER SET LOGINTIME = CURRENT_TIMESTAMP WHERE USERNAME = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        pstmt.setString(1, username);
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        return affectedRowNum > 0;
    }

    public boolean updateAdministratorDate(String username) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "UPDATE ADMINISTRATORUSER SET LOGINTIME = CURRENT_TIMESTAMP WHERE USERNAME = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        pstmt.setString(1, username);
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        return affectedRowNum > 0;
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------------*/

    public User findUserByTeacherId(Integer id) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM TEACHERUSER WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), teacher, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    public User findUserByStudentId(Integer id) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM STUDENTUSER WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Student student = StudentService.getInstance().find(resultSet.getInt("student_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), student, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    public User findUserByAdministratorId(Integer id) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM ADMINISTRATORUSER WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Administrator administrator = AdministratorService.getInstance().find(resultSet.getInt("administrator_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), administrator, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------------*/

    public User findUserByTeacherUserName(String username) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM TEACHERUSER WHERE USERNAME = ? ";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, username);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Teacher teacher = TeacherService.getInstance().find(resultSet.getInt("teacher_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), teacher, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    public User findUserByStudentUserName(String username) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM STUDENTUSER WHERE USERNAME = ? ";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, username);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Student student = StudentService.getInstance().find(resultSet.getInt("student_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), student, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    public User findUserByAdministratorUserName(String username) throws SQLException {
        //创建User类型变量
        User user = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM ADMINISTRATORUSER WHERE USERNAME = ? ";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, username);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Administrator administrator = AdministratorService.getInstance().find(resultSet.getInt("administrator_id"));
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), administrator, resultSet.getTimestamp("loginTime"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该User对象
        return user;
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------------------------*/

    public boolean changeTeacherPassword(User user) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateUser_sql = "UPDATE TEACHERUSER SET PASSWORD = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateUser_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, user.getPassword());
        pstmt.setInt(2, user.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean changeStudentPassword(User user) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateUser_sql = "UPDATE STUDENTUSER SET PASSWORD = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateUser_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, user.getPassword());
        pstmt.setInt(2, user.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean changeAdministratorPassword(User user) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateUser_sql = "UPDATE ADMINISTRATORUSER SET PASSWORD = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateUser_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, user.getPassword());
        pstmt.setInt(2, user.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}
