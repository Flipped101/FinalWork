package dao;

import domain.Grade;
import domain.Department;
import service.DepartmentService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class GradeDao {
    //本类的一个对象引用，保存自身对象
    private static GradeDao gradeDao = new GradeDao();

    //私有的构造方法，防止其它类创建它的对象
    private GradeDao() {
    }

    //静态方法，返回本类的惟一对象
    public static GradeDao getInstance() {
        return gradeDao;
    }

    public Collection<Grade> findAll() throws SQLException {
        //创建Grade类型的集合对象
        Set<Grade> grades = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM GRADE");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Department department = DepartmentService.getInstance().find(resultSet.getInt("department_id"));
            //创建Grade对象，根据遍历结果中的id,description,no,remarks值
            Grade grade = new Grade(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"), department);
            //向grades集合中添加Grade对象
            grades.add(grade);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回grades集合
        return grades;
    }

    public Grade find(Integer id) throws SQLException {
        //创建Grade类型变量
        Grade grade = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM GRADE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Department department = DepartmentService.getInstance().find(resultSet.getInt("department_id"));
            //创建Grade对象，根据遍历结果中的id,description,no,remarks值
            grade = new Grade(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"), department);
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Grade对象
        return grade;
    }

    public Collection<Grade> findAllByDepartment(Integer department_id) throws SQLException {
        //创建Grade类型的集合对象
        Set<Grade> grades = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM GRADE WHERE DEPARTMENT_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, department_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Department department = DepartmentService.getInstance().find(resultSet.getInt("department_id"));
            //创建Grade对象，根据遍历结果中的id,description,no,remarks值
            Grade grade = new Grade(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"), department);
            //向departemnts集合中添加Grade对象
            grades.add(grade);
        }
        //若结果集仍然有下一条记录，则执行循环体
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回grades集合
        return grades;
    }

    public boolean add(Grade grade) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO GRADE (NO,DESCRIPTION,REMARKS,DEPARTMENT_ID) VALUES" +
                        "(?,?,?,?)");
        preparedStatement.setString(1, grade.getNo());
        preparedStatement.setString(2, grade.getDescription());
        preparedStatement.setString(3, grade.getRemarks());
        preparedStatement.setInt(4, grade.getDepartment().getId());
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
        String deleteGrade_sql = "DELETE FROM GRADE WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(deleteGrade_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean update(Grade grade) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateGrade_sql = "UPDATE GRADE SET DESCRIPTION = ?, NO = ? ,REMARKS = ?, DEPARTMENT_ID = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateGrade_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, grade.getDescription());
        pstmt.setString(2, grade.getNo());
        pstmt.setString(3, grade.getRemarks());
        pstmt.setInt(4, grade.getDepartment().getId());
        pstmt.setInt(5, grade.getId());
        //执行预编译对象的executeUpdate()方法，获取更新记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

