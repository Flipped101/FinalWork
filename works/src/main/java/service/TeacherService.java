package service;

import dao.TeacherDao;
import domain.Teacher;

import java.sql.SQLException;
import java.util.Collection;

public final class TeacherService {
    //TeacherDao类的一个对象引用，保存TeacherDao对象
    private static TeacherDao teacherDao= TeacherDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static TeacherService teacherService=new TeacherService();

    //私有的构造方法，防止其它类创建它的对象
    private TeacherService(){}

    //静态方法，返回本类的惟一对象
    public static TeacherService getInstance() {
        return teacherService;
    }

    public Collection<Teacher> findAll() throws SQLException {
        //调用TeacherDao类里的findAll方法返回查找结果
        return teacherDao.findAll();
    }

    public Collection<Teacher> findAllByNo(String no) throws SQLException {
        //调用TeacherDao类里的findAll方法返回查找结果
        return teacherDao.findAllByNo(no);
    }

    public Teacher find(Integer id) throws SQLException {
        //调用TeacherDao类里的find方法返回查找结果
        return teacherDao.find(id);
    }

    public boolean add(Teacher teacher) {
        //调用TeacherDao类里的add方法增加Teacher对象
        return teacherDao.add(teacher);
    }

    public boolean delete(Integer id) {
        //调用TeacherDao类里的delete方法删除Teacher对象
        return teacherDao.delete(id);
    }

    public boolean update(Teacher teacher) throws SQLException {
        //调用TeacherDao类里的update方法修改Teacher对象
        return teacherDao.update(teacher);
    }

}
