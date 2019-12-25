package service;

import dao.DepartmentDao;
import domain.Department;

import java.sql.SQLException;
import java.util.Collection;

public final class DepartmentService {
    //DepartmentDao类的一个对象引用，保存DepartmentDao对象
    private static DepartmentDao departmentDao= DepartmentDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static DepartmentService departmentService=new DepartmentService();

    //私有的构造方法，防止其它类创建它的对象
    private DepartmentService(){}

    //静态方法，返回本类的惟一对象
    public static DepartmentService getInstance(){
        return departmentService;
    }

    public Collection<Department> findAll() throws SQLException {
        //调用DepartmentDao类里的findAll方法返回查找结果
        return departmentDao.findAll();
    }

    public Collection<Department> findAllBySchool(Integer school_id) throws SQLException{
        //调用DepartmentDao类里的findAllBySchool方法返回查找结果
        return departmentDao.findAllBySchool(school_id);
    }

    public Department find(Integer id) throws SQLException {
        //调用DepartmentDao类里的find方法返回查找结果
        return departmentDao.find(id);
    }

    public boolean update(Department department) throws SQLException {
        //调用DepartmentDao类里的update方法修改Department对象
        return departmentDao.update(department);
    }

    public boolean add(Department department) throws SQLException {
        //调用DepartmentDao类里的add方法增加Department对象
        return departmentDao.add(department);
    }

    public boolean delete(Integer id) throws SQLException {
        //调用DepartmentDao类里的delete方法删除Department对象
        return departmentDao.delete(id);
    }

}

