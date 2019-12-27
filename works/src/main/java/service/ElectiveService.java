package service;

import dao.ElectiveDao;
import domain.Elective;

import java.sql.SQLException;
import java.util.Collection;

public final class ElectiveService {
    //ElectiveDao类的一个对象引用，保存ElectiveDao对象
    private static ElectiveDao electiveDao
            = ElectiveDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static ElectiveService electiveService
            = new ElectiveService();

    //私有的构造方法，防止其它类创建它的对象
    private ElectiveService() {
    }

    //静态方法，返回本类的惟一对象
    public static ElectiveService getInstance() {
        return electiveService;
    }

    public Collection<Elective> findAll(Integer student_id) throws SQLException {
        //调用ElectiveDao类里的findAll方法返回查找结果
        return electiveDao.findAllByStudent(student_id);
    }

    public Collection<Elective> findAllByCourse(Integer student_id) throws SQLException {
        //调用ElectiveDao类里的findAll方法返回查找结果
        return electiveDao.findAllByCourse(student_id);
    }

    public boolean add(Integer student_id, Integer course_id) throws SQLException {
        //调用ElectiveDao类里的add方法增加Elective对象
        return electiveDao.add(student_id, course_id);
    }

    public boolean delete(Integer student_id, Integer course_id) throws SQLException {
        //调用ElectiveDao类里的delete方法删除Elective对象
        return electiveDao.delete(student_id, course_id);
    }

}

