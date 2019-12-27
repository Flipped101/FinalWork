package dao;

import domain.Student;
import domain.Grade;
import service.GradeService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

public final class StudentDao {
    //本类的一个对象引用，保存自身对象
    private static StudentDao studentDao = new StudentDao();

    //私有的构造方法，防止其它类创建它的对象
    private StudentDao() {
    }

    //静态方法，返回本类的惟一对象
    public static StudentDao getInstance() {
        return studentDao;
    }

    public Collection<Student> findAll() throws SQLException {
        //创建Student类型的集合对象
        Set<Student> students = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM STUDENT");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Grade grade = GradeService.getInstance().find(resultSet.getInt("grade_id"));
            //创建Student对象，根据遍历结果中的id,name,no,phone值
            Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("no"), resultSet.getString("phone"), grade);
            //向students集合中添加Student对象
            students.add(student);
        }
        //关闭资源
        JdbcHelper.close(resultSet, statement, connection);
        //返回students集合
        return students;
    }

    public Student find(Integer id) throws SQLException {
        //创建Student类型变量
        Student student = null;
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM STUDENT WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行if语句内语句
        if (resultSet.next()) {
            Grade grade = GradeService.getInstance().find(resultSet.getInt("grade_id"));
            //创建Student对象，根据遍历结果中的id,name,no,phone值
            student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("no"), resultSet.getString("phone"), grade);
        }
        //关闭资源
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回该Student对象
        return student;
    }

    public Collection<Student> findAllByGrade(Integer grade_id) throws SQLException {
        //创建Student类型的集合对象
        Set<Student> students = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM STUDENT WHERE GRADE_ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setInt(1, grade_id);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Grade grade = GradeService.getInstance().find(resultSet.getInt("grade_id"));
            //创建Student对象，根据遍历结果中的id,name,no,phone值
            Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("no"), resultSet.getString("phone"), grade);
            //向departemnts集合中添加Student对象
            students.add(student);
        }
        //若结果集仍然有下一条记录，则执行循环体
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回students集合
        return students;
    }

    public Collection<Student> findAllByNo(String no) throws SQLException {
        //创建Student类型的集合对象
        Set<Student> students = new TreeSet<>();
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String str = "SELECT * FROM STUDENT WHERE no = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(str);
        //为预编译的语句参数赋值
        pstmt.setString(1, no);
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = pstmt.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()) {
            Grade grade = GradeService.getInstance().find(resultSet.getInt("grade_id"));
            //创建Student对象，根据遍历结果中的id,name,no,phone值
            Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("no"), resultSet.getString("phone"), grade);
            //向departemnts集合中添加Student对象
            students.add(student);
        }
        //若结果集仍然有下一条记录，则执行循环体
        JdbcHelper.close(resultSet, pstmt, connection);
        //返回students集合
        return students;
    }

    public boolean add(Student student) {
        //创建Connection类型变量
        Connection connection = null;
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        int affectUserRows = 0;
        int affectStudentRows = 0;
        int student_id = 0;
        try {
            //加载驱动程序
            //获得连接对象
            connection = JdbcHelper.getConn();
            //事务开始
            connection.setAutoCommit(false);

            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO STUDENT (NO,NAME,PHONE,GRADE_ID) VALUES" +
                            "(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getNo());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setInt(4, student.getGrade().getId());
            //执行预编译对象的executeUpdate()方法，获取增加记录的行数
            affectStudentRows = preparedStatement.executeUpdate();
            System.out.println("Student Table 增加了 " + affectStudentRows + " 行。");

            //查询刚刚插入数据的主键并获得结果集对象（游标指向结果集的开头）
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            //若结果集仍然有下一条记录，则执行循环体
            if (resultSet.next()) {
                //将查询到的主键值赋值给teacher_id
                student_id = resultSet.getInt(1);
            }

            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO STUDENTUSER (USERNAME,PASSWORD,STUDENT_ID) VALUES" +
                            "(?,?,?)");
            preparedStatement.setString(1, student.getNo());
            preparedStatement.setString(2, student.getNo());
            preparedStatement.setInt(3, student_id);
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
        return affectStudentRows > 0 && affectUserRows > 0;
    }

    public boolean delete(Integer id) {
        //创建Connection类型变量
        Connection connection = null;
        //创建PreparedStatement接口变量
        PreparedStatement preparedStatement = null;
        int affectUserRows = 0;
        int affectStudentRows = 0;
        try {
            //加载驱动程序
            //获得连接对象
            connection = JdbcHelper.getConn();
            //事务开始
            connection.setAutoCommit(false);
            String deleteUser_sql = "DELETE FROM STUDENTUSER WHERE STUDENT_ID = ?";
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(deleteUser_sql);
            //为预编译的语句参数赋值
            preparedStatement.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectUserRows = preparedStatement.executeUpdate();
            System.out.println("User Table 删除了 " + affectUserRows + " 行");

            //创建sql语句，“？”作为占位符
            String deleteStudent_sql = "DELETE FROM STUDENT WHERE ID = ?";
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            preparedStatement = connection.prepareStatement(deleteStudent_sql);
            //为预编译的语句参数赋值
            preparedStatement.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectStudentRows = preparedStatement.executeUpdate();
            System.out.println("Student Table 删除了 " + affectStudentRows + " 行");

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
        return affectStudentRows > 0 && affectUserRows > 0;
    }

    public boolean update(Student student) throws SQLException {
        //加载驱动程序
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String updateStudent_sql = "UPDATE STUDENT SET NAME = ?, NO = ? ,PHONE = ?, GRADE_ID = ? WHERE ID = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(updateStudent_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1, student.getName());
        pstmt.setString(2, student.getNo());
        pstmt.setString(3, student.getPhone());
        pstmt.setInt(4, student.getGrade().getId());
        pstmt.setInt(5, student.getId());
        //执行预编译对象的executeUpdate()方法，获取更新记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 " + affectedRowNum + " 行");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        return affectedRowNum > 0;
    }

}

