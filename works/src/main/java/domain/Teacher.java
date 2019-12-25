package domain;

import java.io.Serializable;

//实现Comparable 接口进行排序、实现Serializable 接口保证类是可序列化的，便于存储和传输
public final class Teacher implements Comparable<Teacher>, Serializable {
    //定义私有字段
    private Integer id;
    private String no;
    private String name;
    private ProfTitle profTitle;
    private Degree degree;
    private Department department;
    private String phone;

    //定义含有全部字段的构造器
    public Teacher(Integer id,
                   String no,
                   String name,
                   ProfTitle title,
                   Degree degree,
                   Department department,
                   String phone) {
        this(no, name, title, degree, department,phone);
        this.id = id;
    }

    //定义不含有id字段的构造器
    public Teacher(
            String no,
            String name,
            ProfTitle title,
            Degree degree,
            Department department,
            String phone) {
        super();
        this.no = no;
        this.name = name;
        this.profTitle = title;
        this.degree = degree;
        this.department = department;
        this.phone = phone;
    }

    //定义getId方法返回id值
    public Integer getId() {
        return id;
    }

    //定义setId设置id值
    public void setId(Integer id) {
        this.id = id;
    }

    //定义getNo方法返回no值
    public String getNo() {
        return no;
    }

    //定义setNo设置no值
    public void setNo(String no) {
        this.no = no;
    }

    //定义getPhone方法返回phone值
    public String getPhone() {
        return phone;
    }

    //定义setPhone设置phone值
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //定义getName方法返回name值
    public String getName() {
        return name;
    }

    //定义setName设置name值
    public void setName(String name) {
        this.name = name;
    }

    //定义getTitle方法返回ProfTitle对象
    public ProfTitle getTitle() {
        return this.profTitle;
    }

    //定义setTitle设置ProfTitle对象属性
    public void setTitle(ProfTitle title) {
        this.profTitle = title;
    }

    //定义getDegree方法返回Degree对象
    public Degree getDegree() {
        return degree;
    }

    //定义setDegree设置Degree对象属性
    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    //定义getDepartment方法返回Department对象
    public Department getDepartment() {
        return department;
    }

    //定义setDepartment设置Department对象属性
    public void setDepartment(Department department) {
        this.department = department;
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

        retValue = "Teacher ( "
                + super.toString() + TAB
                + "id = " + this.id + TAB
                + "name = " + this.name + TAB
                + "title = " + this.profTitle + TAB
                + "degree = " + this.degree + TAB
                + "department = " + this.department + TAB
                + "phone = " + this.phone + TAB
                + " )";

        return retValue;
    }

    //实现接口中的方法compareTo对类对象进行排序
    @Override
    public int compareTo(Teacher o) {
        // TODO Auto-generated method stub
        return this.id - o.id;
    }

}
