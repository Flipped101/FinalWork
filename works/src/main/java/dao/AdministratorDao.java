package dao;

import domain.Administrator;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class AdministratorDao {
    //本类的一个对象引用，保存自身对象
    private static AdministratorDao administratorDao = new AdministratorDao();

    //私有的构造方法，防止其它类创建它的对象
    private AdministratorDao() {
    }

    //静态方法，返回本类的惟一对象
    public static AdministratorDao getInstance() {
        return administratorDao;
    }

    public Collection<Administrator> findAll() throws SQLException {
        //创建Administrator类型的集合对象
        Set<Administrator> administrators = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ADMINISTRATOR");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            //创建Administrator对象，根据遍历结果中的id,name,no,phone值
            Administrator administrator = new Administrator(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("no"),
                    resultSet.getString("phone"));
            //向administrators集合中添加Administrator对象
            administrators.add(administrator);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回administrators集合
        return administrators;
    }

    public Collection<Administrator> findAllByNo(String no) throws SQLException {
        //创建Administrator类型的集合对象
        Set<Administrator> administrators = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM ADMINISTRATOR WHERE no = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, no);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            //创建Administrator对象，根据遍历结果中的id,name,no,phone值
            Administrator administrator = new Administrator(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("no"),
                    resultSet.getString("phone"));
            //向administrators集合中添加Administrator对象
            administrators.add(administrator);
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回administrators集合
        return administrators;
    }

    public Administrator find(Integer id) throws SQLException {
        //创建Administrator类型变量
        Administrator administrator = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM ADMINISTRATOR WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            //创建Administrator对象，根据遍历结果中的id,name,no,phone值
            administrator = new Administrator(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("no"), resultSet.getString("phone"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Administrator对象
        return administrator;
    }

    public boolean add(Administrator administrator) {
        //创建Connection类型变量
        Connection connection = null;
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        int affectUserRows = 0;
        int affectAdministratorRows = 0;
        int Administrator_id = 0;
        try {
            //加载驱动程序
            //获得连接对象
            connection = JdbcHelper.getConn();
            //事务开始
            connection.setAutoCommit(false);
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO ADMINISTRATOR (NO,NAME,PHONE) VALUES" +
                            "(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, administrator.getNo());
            preparedStatement.setString(2, administrator.getName());
            preparedStatement.setString(3, administrator.getPhone());
            //执行预编译对象的executeUpdate()方法，获取增加记录的行数
            affectAdministratorRows = preparedStatement.executeUpdate();
            System.out.println("Administrator Table 增加了 " + affectAdministratorRows + " 行。");

            //查询刚刚插入数据的主键并获得结果集对象（游标指向结果集的开头）
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            //若结果集仍然有下一条记录，则执行循环体
            if (resultSet.next()) {
                //将查询到的主键值赋值给teacher_id
                Administrator_id = resultSet.getInt(1);
            }
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO ADMINISTRATORUSER (USERNAME,PASSWORD,ADMINISTRATOR_ID) VALUES" +
                            "(?,?,?)");
            preparedStatement.setString(1, administrator.getNo());
            preparedStatement.setString(2, administrator.getNo());
            preparedStatement.setInt(3, Administrator_id);
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
        return affectAdministratorRows > 0 && affectUserRows > 0;
    }

    public boolean delete(Integer id) {
        //创建Connection类型变量
        Connection connection = null;
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        int affectUserRows = 0;
        int affectAdministratorRows = 0;
        try {
            //加载驱动程序
            //获得连接对象
            connection = JdbcHelper.getConn();
            //事务开始
            connection.setAutoCommit(false);
            String deleteUser_sql = "DELETE FROM ADMINISTRATORUSER WHERE ADMINISTRATOR_ID = ?";
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(deleteUser_sql);
            //为预编译的语句参数赋值
            preparedStatement.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectUserRows = preparedStatement.executeUpdate();
            System.out.println("User Table 删除了 " + affectUserRows + " 行");

            //创建sql语句，“？”作为占位符
            String deleteAdministrator_sql = "DELETE FROM ADMINISTRATOR WHERE ID = ?";
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(deleteAdministrator_sql);
            //为预编译的语句参数赋值
            preparedStatement.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectAdministratorRows = preparedStatement.executeUpdate();
            System.out.println("Administrator Table 删除了 " + affectAdministratorRows + " 行");

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
        return affectAdministratorRows > 0 && affectUserRows > 0;
    }

    public boolean update(Administrator administrator) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateAdministrator_sql = "UPDATE ADMINISTRATOR SET NAME = ?, NO = ? ,PHONE = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateAdministrator_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, administrator.getName());
        pstmt.setString(2, administrator.getNo());
        pstmt.setString(3, administrator.getPhone());
        pstmt.setInt(4, administrator.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

