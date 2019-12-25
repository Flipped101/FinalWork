package dao;

import domain.School;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class SchoolDao {
    //本类的一个对象引用，保存自身对象
    private static SchoolDao schoolDao = new SchoolDao();

    //私有的构造方法，防止其它类创建它的对象
    private SchoolDao(){}

    //静态方法，返回本类的惟一对象
    public static SchoolDao getInstance(){
        return schoolDao;
    }

    public Collection<School> findAll() throws SQLException {
        //创建School类型的集合对象
        Set<School> schools = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM SCHOOL");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建School对象，根据遍历结果中的id,description,no,remarks值
            School school = new School(resultSet.getInt("id"),resultSet.getString("description"),
                    resultSet.getString("no"),resultSet.getString("remarks"));
            //向schools集合中添加School对象
            schools.add(school);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        //返回schools集合
        return schools;
    }

    public School find(Integer id) throws SQLException{
        //创建School类型变量
        School school = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM SCHOOL WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1,id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()){
            //创建School对象，根据遍历结果中的id,description,no,remarks值
            school = new School(resultSet.getInt("id"),resultSet.getString("description"),
                    resultSet.getString("no"),resultSet.getString("remarks"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,pstmt,connection);
        //返回该School对象
        return school;
    }

    public boolean add (School school) throws SQLException{
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO SCHOOL (NO,DESCRIPTION,REMARKS) VALUES" +
                        "(?,?,?)");
        preparedStatement.setString(1,school.getNo());
        preparedStatement.setString(2,school.getDescription());
        preparedStatement.setString(3,school.getRemarks());
        int affectRowNum = preparedStatement.executeUpdate();
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        System.out.println("增加了 " + affectRowNum + " 行。");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectRowNum > 0;
    }

    public boolean delete(Integer id) throws SQLException{
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String deleteSchool_sql = "DELETE FROM SCHOOL WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(deleteSchool_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1,id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 "+affectedRowNum+" 行");
        //关闭资源
        JdbcHelper.close(pstmt,connection);
        return affectedRowNum > 0;
    }

    public boolean update(School school) throws SQLException{
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateSchool_sql = "UPDATE SCHOOL SET DESCRIPTION = ?, NO = ? ,REMARKS = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateSchool_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,school.getDescription());
        pstmt.setString(2,school.getNo());
        pstmt.setString(3,school.getRemarks());
        pstmt.setInt(4,school.getId());
        //执行预编译对象的executeUpdate()方法，获取修改记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 行");
        //关闭资源
        JdbcHelper.close(pstmt,connection);
        return affectedRowNum > 0;
    }

}
