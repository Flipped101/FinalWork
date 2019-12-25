package service;

import dao.GradeDao;
import domain.Grade;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public final class GradeService {
    //GradeDao类的一个对象引用，保存GradeDao对象
    private static GradeDao gradeDao= GradeDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static GradeService gradeService=new GradeService();

    //私有的构造方法，防止其它类创建它的对象
    private GradeService(){}

    //静态方法，返回本类的惟一对象
    public static GradeService getInstance(){
        return gradeService;
    }

    public Collection<Grade> findAll() throws SQLException {
        //调用GradeDao类里的findAll方法返回查找结果
        return gradeDao.findAll();
    }

    public Collection<Grade> findAllByDepartment(Integer department_id)
            throws SQLException{
        //调用GradeDao类里的findAllByDepartment方法返回查找结果
        return gradeDao.findAllByDepartment(department_id);
    }

    public Grade find(Integer id) throws SQLException {
        //调用GradeDao类里的find方法返回查找结果
        return gradeDao.find(id);
    }

    public boolean update(Grade grade) throws SQLException {
        //调用GradeDao类里的update方法修改Grade对象
        return gradeDao.update(grade);
    }

    public boolean add(Grade grade) throws SQLException {
        //调用GradeDao类里的add方法增加Grade对象
        return gradeDao.add(grade);
    }

    public boolean delete(Integer id) throws SQLException {
        //调用GradeDao类里的delete方法删除Grade对象
        return gradeDao.delete(id);
    }

}

