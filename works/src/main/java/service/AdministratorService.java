package service;

import dao.AdministratorDao;
import domain.Administrator;

import java.sql.SQLException;
import java.util.Collection;

public final class AdministratorService {
    //AdministratorDao类的一个对象引用，保存AdministratorDao对象
    private static AdministratorDao administratorDao
            = AdministratorDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static AdministratorService administratorService
            = new AdministratorService();

    //私有的构造方法，防止其它类创建它的对象
    private AdministratorService() {
    }

    //静态方法，返回本类的惟一对象
    public static AdministratorService getInstance() {
        return administratorService;
    }

    public Collection<Administrator> findAll() throws SQLException {
        //调用AdministratorDao类里的findAll方法返回查找结果
        return administratorDao.findAll();
    }

    public Collection<Administrator> findAllByNo(String no) throws SQLException {
        //调用AdministratorDao类里的findAll方法返回查找结果
        return administratorDao.findAllByNo(no);
    }

    public Administrator find(Integer id) throws SQLException {
        //调用AdministratorDao类里的find方法返回查找结果
        return administratorDao.find(id);
    }

    public boolean add(Administrator administrator) {
        //调用AdministratorDao类里的add方法增加Administrator对象
        return administratorDao.add(administrator);
    }

    public boolean delete(Integer id) {
        //调用AdministratorDao类里的delete方法删除Administrator对象
        return administratorDao.delete(id);
    }

    public boolean update(Administrator administrator) throws SQLException {
        //调用AdministratorDao类里的update方法修改Administrator对象
        return administratorDao.update(administrator);
    }

}

