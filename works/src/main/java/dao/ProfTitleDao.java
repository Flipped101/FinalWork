package dao;

import domain.ProfTitle;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class ProfTitleDao {
    //本类的一个对象引用，保存自身对象
    private static ProfTitleDao profTitleDao = new ProfTitleDao();

    //私有的构造方法，防止其它类创建它的对象
    private ProfTitleDao() {
    }

    //静态方法，返回本类的惟一对象
    public static ProfTitleDao getInstance() {
        return profTitleDao;
    }

    public Collection<ProfTitle> findAll() throws SQLException {
        //创建ProfTitle类型的集合对象
        Set<ProfTitle> profTitles = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM PROFTITLE");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            //创建ProfTitle对象，根据遍历结果中的id,description,no,remarks值
            ProfTitle profTitle = new ProfTitle(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"));
            //向profTitles集合中添加ProfTitle对象
            profTitles.add(profTitle);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回profTitles集合
        return profTitles;
    }

    public ProfTitle find(Integer id) throws SQLException {
        //创建ProfTitle类型变量
        ProfTitle profTitle = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM PROFTITLE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            //创建ProfTitle对象，根据遍历结果中的id,description,no,remarks值
            profTitle = new ProfTitle(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该ProfTitle对象
        return profTitle;
    }

    public boolean add(ProfTitle profTitle) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO PROFTITLE (NO,DESCRIPTION,REMARKS) VALUES" +
                        "(?,?,?)");
        preparedStatement.setString(1, profTitle.getNo());
        preparedStatement.setString(2, profTitle.getDescription());
        preparedStatement.setString(3, profTitle.getRemarks());
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
        String deleteProfTitle_sql = "DELETE FROM PROFTITLE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(deleteProfTitle_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean update(ProfTitle profTitle) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateDepartment_sql = "UPDATE PROFTITLE SET DESCRIPTION = ?, NO = ? ,REMARKS = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateDepartment_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, profTitle.getDescription());
        pstmt.setString(2, profTitle.getNo());
        pstmt.setString(3, profTitle.getRemarks());
        pstmt.setInt(4, profTitle.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

