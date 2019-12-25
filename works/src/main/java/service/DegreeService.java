package service;

import dao.DegreeDao;
import domain.Degree;

import java.sql.SQLException;
import java.util.Collection;

public final class DegreeService {
    //DegreeDao类的一个对象引用，保存DegreeDao对象
    private static DegreeDao degreeDao
            = DegreeDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static DegreeService degreeService
            = new DegreeService();

    //私有的构造方法，防止其它类创建它的对象
    private DegreeService() {
    }

    //静态方法，返回本类的惟一对象
    public static DegreeService getInstance() {
        return degreeService;
    }

    public Collection<Degree> findAll() throws SQLException {
        //调用DegreeDao类里的findAll方法返回查找结果
        return degreeDao.findAll();
    }

    public Degree find(Integer id) throws SQLException {
        //调用DegreeDao类里的find方法返回查找结果
        return degreeDao.find(id);
    }

    public boolean add(Degree degree) throws SQLException {
        //调用DegreeDao类里的add方法增加Degree对象
        return degreeDao.add(degree);
    }

    public boolean delete(Integer id) throws SQLException {
        //调用DegreeDao类里的delete方法删除Degree对象
        return degreeDao.delete(id);
    }

    public boolean update(Degree degree) throws SQLException {
        //调用DegreeDao类里的update方法修改Degree对象
        return degreeDao.update(degree);
    }

}

