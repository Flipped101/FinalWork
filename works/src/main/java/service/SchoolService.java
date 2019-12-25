package service;

import dao.SchoolDao;
import domain.School;

import java.sql.SQLException;
import java.util.Collection;

public final class SchoolService {
    //SchoolDao类的一个对象引用，保存SchoolDao对象
    private static SchoolDao schoolDao= SchoolDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static SchoolService schoolService=new SchoolService();

    //私有的构造方法，防止其它类创建它的对象
    private SchoolService(){}

    //静态方法，返回本类的惟一对象
    public static SchoolService getInstance(){
        return schoolService;
    }

    public Collection<School> findAll() throws SQLException {
        //调用SchoolDao类里的findAll方法返回查找结果
        return schoolDao.findAll();
    }

    public School find(Integer id) throws SQLException {
        //调用SchoolDao类里的find方法返回查找结果
        return schoolDao.find(id);
    }

    public boolean add(School school) throws SQLException {
        //调用SchoolDao类里的add方法增加School对象
        return schoolDao.add(school);
    }

    public boolean delete(Integer id) throws SQLException {
        //调用SchoolDao类里的delete方法删除School对象
        return schoolDao.delete(id);
    }

    public boolean update(School school) throws SQLException {
        //调用SchoolDao类里的update方法修改School对象
        return schoolDao.update(school);
    }

}
