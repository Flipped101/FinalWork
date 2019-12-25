package domain;

import java.io.Serializable;

//实现Comparable 接口进行排序、实现Serializable 接口保证类是可序列化的，便于存储和传输
public final class Student implements Comparable<Student>, Serializable {
    //定义私有字段
    private Integer id;
    private String name;
    private String no;
    private String phone;
    private Grade grade;

    //定义含有全部字段的构造器
    public Student(Integer id, String name, String no,
                   String phone, Grade grade) {
        this(name, no, phone, grade);
        this.id = id;
    }

    //定义不含有id字段的构造器
    public Student(String name, String no,
                   String phone, Grade grade) {
        super();
        this.name = name;
        this.no = no;
        this.phone = phone;
        this.grade = grade;
    }

    //定义getId方法返回id值
    public Integer getId() {
        return id;
    }

    //定义setId设置id值
    public void setId(Integer id) {
        this.id = id;
    }

    //定义getName方法返回name值
    public String getName() {
        return name;
    }

    //定义setName设置name值
    public void setName(String name) {
        this.name = name;
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

    //定义getGrade方法返回Grade对象
    public Grade getGrade() {
        return grade;
    }

    //定义setGrade设置Grade对象属性
    public void setGrade(Grade grade) {
        this.grade = grade;
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

        retValue = "Student ( "
                + super.toString() + TAB
                + "id = " + this.id + TAB
                + "name = " + this.name + TAB
                + "no = " + this.no + TAB
                + "phone = " + this.phone + TAB + "\n"
                + "grade_json = " + this.grade + TAB
                + " )";

        return retValue;
    }

    //实现接口中的方法compareTo对类对象进行排序
    @Override
    public int compareTo(Student o) {
        // TODO Auto-generated method stub
        return this.id - o.id;
    }

}
