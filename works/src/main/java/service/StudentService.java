package service;

import dao.StudentDao;
import domain.Student;

import java.sql.SQLException;
import java.util.Collection;

public final class StudentService {
    //StudentDao类的一个对象引用，保存StudentDao对象
    private static StudentDao studentDao= StudentDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static StudentService studentService=new StudentService();

    //私有的构造方法，防止其它类创建它的对象
    private StudentService(){}

    //静态方法，返回本类的惟一对象
    public static StudentService getInstance(){
        return studentService;
    }

    public Collection<Student> findAll() throws SQLException {
        //调用StudentDao类里的findAll方法返回查找结果
        return studentDao.findAll();
    }

    public Collection<Student> findAllByGrade(Integer grade_id) throws SQLException {
        //调用StudentDao类里的findAllByGrade方法返回查找结果
        return studentDao.findAllByGrade(grade_id);
    }

    public Collection<Student> findAllByNo(String no) throws SQLException {
        //调用StudentDao类里的findAllByGrade方法返回查找结果
        return studentDao.findAllByNo(no);
    }

    public Student find(Integer id) throws SQLException {
        //调用StudentDao类里的find方法返回查找结果
        return studentDao.find(id);
    }

    public boolean update(Student student) throws SQLException {
        //调用StudentDao类里的update方法修改Student对象
        return studentDao.update(student);
    }

    public boolean add(Student student) {
        //调用StudentDao类里的add方法增加Student对象
        return studentDao.add(student);
    }

    public boolean delete(Integer id) {
        //调用StudentDao类里的delete方法删除Student对象
        return studentDao.delete(id);
    }

}

