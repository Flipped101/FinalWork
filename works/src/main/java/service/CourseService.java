package service;

import dao.CourseDao;
import domain.Course;

import java.sql.SQLException;
import java.util.Collection;

public final class CourseService {
    //CourseDao类的一个对象引用，保存CourseDao对象
    private static CourseDao courseDao= CourseDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static CourseService courseService=new CourseService();

    //私有的构造方法，防止其它类创建它的对象
    private CourseService(){}

    //静态方法，返回本类的惟一对象
    public static CourseService getInstance(){
        return courseService;
    }

    public Collection<Course> findAll() throws SQLException {
        //调用CourseDao类里的findAll方法返回查找结果
        return courseDao.findAll();
    }

    public Collection<Course> findAllByTeacher(Integer teacher_id) throws SQLException{
        //调用CourseDao类里的findAllByTeacher方法返回查找结果
        return courseDao.findAllByTeacher(teacher_id);
    }

    public Course find(Integer id) throws SQLException {
        //调用CourseDao类里的find方法返回查找结果
        return courseDao.find(id);
    }

    public boolean update(Course course) throws SQLException {
        //调用CourseDao类里的update方法修改Course对象
        return courseDao.update(course);
    }

    public boolean add(Course course) throws SQLException {
        //调用CourseDao类里的add方法增加Course对象
        return courseDao.add(course);
    }

    public boolean delete(Integer id) throws SQLException {
        //调用CourseDao类里的delete方法删除Course对象
        return courseDao.delete(id);
    }

}

