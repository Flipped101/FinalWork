package dao;

import domain.Department;
import domain.School;
import service.SchoolService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class DepartmentDao {
    //本类的一个对象引用，保存自身对象
    private static DepartmentDao departmentDao = new DepartmentDao();

    //私有的构造方法，防止其它类创建它的对象
    private DepartmentDao() {
    }

    //静态方法，返回本类的惟一对象
    public static DepartmentDao getInstance() {
        return departmentDao;
    }

    public Collection<Department> findAll() throws SQLException {
        //创建Department类型的集合对象
        Set<Department> departments = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM DEPARTMENT");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            School school = SchoolService.getInstance().find(resultSet.getInt("school_id"));
            //创建Department对象，根据遍历结果中的id,description,no,remarks值
            Department department = new Department(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"), school);
            //向departments集合中添加Department对象
            departments.add(department);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回departments集合
        return departments;
    }

    public Department find(Integer id) throws SQLException {
        //创建Department类型变量
        Department department = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM DEPARTMENT WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            School school = SchoolService.getInstance().find(resultSet.getInt("school_id"));
            //创建Department对象，根据遍历结果中的id,description,no,remarks值
            department = new Department(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"), school);
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Department对象
        return department;
    }

    public Collection<Department> findAllBySchool(Integer school_id) throws SQLException {
        //创建Department类型的集合对象
        Set<Department> departments = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM DEPARTMENT WHERE SCHOOL_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, school_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            School school = SchoolService.getInstance().find(resultSet.getInt("school_id"));
            //创建Department对象，根据遍历结果中的id,description,no,remarks值
            Department department = new Department(resultSet.getInt("id"), resultSet.getString("description"),
                    resultSet.getString("no"), resultSet.getString("remarks"), school);
            //向departemnts集合中添加Department对象
            departments.add(department);
        }
        //若结果集仍然有下一条记录，则执行循环体
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回departments集合
        return departments;
    }

    public boolean add(Department department) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO DEPARTMENT (NO,DESCRIPTION,REMARKS,SCHOOL_ID) VALUES" +
                        "(?,?,?,?)");
        preparedStatement.setString(1, department.getNo());
        preparedStatement.setString(2, department.getDescription());
        preparedStatement.setString(3, department.getRemarks());
        preparedStatement.setInt(4, department.getSchool().getId());
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
        String deleteDepartment_sql = "DELETE FROM DEPARTMENT WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(deleteDepartment_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行预编译对象的executeUpdate()方法，获取删除记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

    public boolean update(Department department) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateDepartment_sql = "UPDATE DEPARTMENT SET DESCRIPTION = ?, NO = ? ,REMARKS = ?, SCHOOL_ID = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateDepartment_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, department.getDescription());
        pstmt.setString(2, department.getNo());
        pstmt.setString(3, department.getRemarks());
        pstmt.setInt(4, department.getSchool().getId());
        pstmt.setInt(5, department.getId());
        //执行预编译对象的executeUpdate()方法，获取更新记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

