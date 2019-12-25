package service;

import dao.UserDao;
import domain.User;

import java.sql.SQLException;

public final class UserService {
    //UserDao类的一个对象引用，保存UserDao对象
    private UserDao userDao = UserDao.getInstance();

    //本类的一个对象引用，保存自身对象
    private static UserService userService = new UserService();

    //私有的构造方法，防止其它类创建它的对象
    public UserService() {
    }

    //静态方法，返回本类的惟一对象
    public static UserService getInstance(){
        return UserService.userService;
    }

    /*-----------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------*/

    public User findUserByTeacherId(Integer id) throws SQLException {
        //调用UserDao类里的findUserByTeacherId方法返回查找结果
        return userDao.findUserByTeacherId(id);
    }

    public User findUserByStudentId(Integer id) throws SQLException {
        //调用UserDao类里的findUserByStudentId方法返回查找结果
        return userDao.findUserByStudentId(id);
    }

    public User findUserByAdministratorId(Integer id) throws SQLException {
        //调用UserDao类里的findUserByAdministratorId方法返回查找结果
        return userDao.findUserByAdministratorId(id);
    }

    /*-----------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------*/

    public User findUserByTeacherUserName(String userName) throws SQLException {
        //调用UserDao类里的findUserByTeacherUserName方法返回查找结果
        return userDao.findUserByTeacherUserName(userName);
    }

    public User findUserByStudentUserName(String userName) throws SQLException {
        //调用UserDao类里的findUserByStudentUserName方法返回查找结果
        return userDao.findUserByStudentUserName(userName);
    }

    public User findUserByAdministratorUserName(String userName) throws SQLException {
        //调用UserDao类里的findUserByAdministratorUserName方法返回查找结果
        return userDao.findUserByAdministratorUserName(userName);
    }

    /*-----------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------*/

    public boolean updateTeacherDate(String username) throws SQLException {
        //调用UserDao类里的updateTeacherDate方法修改User对象
        return userDao.updateTeacherDate(username);
    }

    public boolean updateStudentDate(String username) throws SQLException {
        //调用UserDao类里的updateStudentDate方法修改User对象
        return userDao.updateStudentDate(username);
    }

    public boolean updateAdministratorDate(String username) throws SQLException {
        //调用UserDao类里的updateAdministratorDate方法修改User对象
        return userDao.updateAdministratorDate(username);
    }

    /*-----------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------*/

    public boolean changeTeacherPassword(User user) throws SQLException {
        //调用UserDao类里的changeTeacherPassword方法修改User对象
        return userDao.changeTeacherPassword(user);
    }

    public boolean changeStudentPassword(User user) throws SQLException {
        //调用UserDao类里的changeStudentPassword方法修改User对象
        return userDao.changeStudentPassword(user);
    }

    public boolean changeAdministratorPassword(User user) throws SQLException {
        //调用UserDao类里的changeAdministratorPassword方法修改User对象
        return userDao.changeAdministratorPassword(user);
    }

    /*-----------------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------------*/

    public User loginTeacher(String username, String password) throws SQLException {
        //调用userDao里的loginTeacher方法检验登录
        return userDao.loginTeacher(username,password);
    }

    public User loginStudent(String username, String password) throws SQLException {
        //调用userDao里的loginStudent方法检验登录
        return userDao.loginStudent(username,password);
    }

    public User loginAdministrator(String username, String password) throws SQLException {
        //调用userDao里的loginAdministrator方法检验登录
        return userDao.loginAdministrator(username,password);
    }
}
