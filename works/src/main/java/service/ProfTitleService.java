package service;

import dao.ProfTitleDao;
import domain.ProfTitle;

import java.sql.SQLException;
import java.util.Collection;

public final class ProfTitleService {
    //ProfTitleDao类的一个对象引用，保存ProfTitleDao对象
    private static ProfTitleDao profTitleDao= ProfTitleDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static ProfTitleService profTitleService=new ProfTitleService();

    //私有的构造方法，防止其它类创建它的对象
    private ProfTitleService(){}

    //静态方法，返回本类的惟一对象
    public static ProfTitleService getInstance(){
        return profTitleService;
    }

    public Collection<ProfTitle> findAll() throws SQLException {
        //调用ProfTitleDao类里的findAll方法返回查找结果
        return profTitleDao.findAll();
    }

    public ProfTitle find(Integer id) throws SQLException {
        //调用ProfTitleDao类里的find方法返回查找结果
        return profTitleDao.find(id);
    }

    public boolean update(ProfTitle profTitle) throws SQLException {
        //调用ProfTitleDao类里的update方法修改ProfTitle对象
        return profTitleDao.update(profTitle);
    }

    public boolean add(ProfTitle profTitle) throws SQLException {
        //调用ProfTitleDao类里的add方法增加ProfTitle对象
        return profTitleDao.add(profTitle);
    }

    public boolean delete(Integer id) throws SQLException {
        //调用ProfTitleDao类里的delete方法删除ProfTitle对象
        return profTitleDao.delete(id);
    }

}

