package domain;

import java.io.Serializable;
import java.util.Date;

//实现Comparable 接口进行排序、实现Serializable 接口保证类是可序列化的，便于存储和传输
public class User implements Comparable<User>, Serializable {
    //定义私有字段
    private Integer id;
    private String username;
    private String password;
    private Date loginTime;
    private Teacher teacher;
    private Student student;
    private Administrator administrator;

    //定义含有外键字段的构造器
    public User(Integer id, String username, String password, Teacher teacher,
                Date loginTime) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.teacher = teacher;
        this.loginTime = loginTime;
    }

    //定义含有外键字段的构造器
    public User(Integer id, String username, String password, Student student,
                Date loginTime) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.student = student;
        this.loginTime = loginTime;
    }

    //定义含有外键字段的构造器
    public User(Integer id, String username, String password, Administrator administrator,
                Date loginTime) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.administrator = administrator;
        this.loginTime = loginTime;
    }

    //定义不含有loginTime字段的构造器
    public User(Integer id, String username, String password) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }

    //定义getId方法返回id值
    public Integer getId() {
        return id;
    }

    //定义setId设置id值
    public void setId(Integer id) {
        this.id = id;
    }

    //定义getUsername方法返回username值
    public String getUsername() {
        return username;
    }

    //定义setUsername设置username值
    public void setUsername(String username) {
        this.username = username;
    }

    //定义getPassword方法返回password值
    public String getPassword() {
        return password;
    }

    //定义setPassword设置password值
    public void setPassword(String password) {
        this.password = password;
    }

    //定义getLoginTime方法返回loginTime值
    public Date getLoginTime() {
        return loginTime;
    }

    //定义setLoginTime设置loginTime值
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    //定义getTeacher方法返回Teacher对象
    public Teacher getTeacher() {
        return teacher;
    }

    //定义setTeacher设置Teacher对象属性
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    //定义getStudent方法返回Student对象
    public Student getStudent() {
        return student;
    }

    //定义setStudent设置Student对象属性
    public void setStudent(Student student) {
        this.student = student;
    }

    //定义getAdministrator方法返回Administrator对象
    public Administrator getAdministrator() {
        return administrator;
    }

    //定义setAdministrator设置Administrator对象属性
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }


    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation
     * of this object.
     */
    public String toString() {
        final String TAB = "    ";

        String retValue = "";

        retValue = "User ( "
                + super.toString() + TAB
                + "id = " + this.id + TAB
                + "username = " + this.username + TAB
                + "password = " + this.password + TAB
                + "loginTime = " + this.loginTime + TAB
                + "teacher = " + this.teacher + TAB
                + "student = " + this.student + TAB
                + "administrator = " + this.administrator + TAB
                + " )";

        return retValue;
    }

    //实现接口中的方法compareTo对类对象进行排序
    @Override
    public int compareTo(User o) {
        // TODO Auto-generated method stub
        return this.id - o.id;
    }

}
