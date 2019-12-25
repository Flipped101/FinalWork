package dao;

import domain.Degree;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class DegreeDao {
    //本类的一个对象引用，保存自身对象
    private static DegreeDao degreeDao = new DegreeDao();

    //私有的构造方法，防止其它类创建它的对象
    private DegreeDao() {
    }

    //静态方法，返回本类的惟一对象
    public static DegreeDao getInstance() {
        return degreeDao;
    }

    public Collection<Degree> findAll() throws SQLException {
        //创建Degree类型的集合对象
        Set<Degree> degrees = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM DEGREE");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            //创建Degree对象，根据遍历结果中的id,description,no,remarks值
            Degree degree = new Degree(resultSet.getInt("id"),
                    resultSet.getString("description"), resultSet.getString("no"),
                    resultSet.getString("remarks"));
            //向degrees集合中添加Degree对象
            degrees.add(degree);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回degrees集合
        return degrees;
    }

    public Degree find(Integer id) throws SQLException {
        //创建Degree类型变量
        Degree degree = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM DEGREE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            //创建Degree对象，根据遍历结果中的id,description,no,remarks值
            degree = new Degree(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"));
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Degree对象
        return degree;
    }

    public boolean add(Degree degree) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO DEGREE (NO,DESCRIPTION,REMARKS) VALUES" +
                        "(?,?,?)");
        preparedStatement.setString(1, degree.getNo());
        preparedStatement.setString(2, degree.getDescription());
        preparedStatement.setString(3, degree.getRemarks());
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
        String deleteDegree_sql = "DELETE FROM DEGREE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(deleteDegree_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean update(Degree degree) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateDegree_sql = "UPDATE DEGREE SET DESCRIPTION = ?, NO = ? ,REMARKS = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateDegree_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, degree.getDescription());
        pstmt.setString(2, degree.getNo());
        pstmt.setString(3, degree.getRemarks());
        pstmt.setInt(4, degree.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

