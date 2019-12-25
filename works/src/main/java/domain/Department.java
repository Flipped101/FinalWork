package domain;

import java.io.Serializable;

//实现Comparable 接口进行排序、实现Serializable 接口保证类是可序列化的，便于存储和传输
public final class Department implements Comparable<Department>, Serializable {
    //定义私有字段
    private Integer id;
    private String description;
    private String no;
    private String remarks;
    private School school;

    //定义含有全部字段的构造器
    public Department(Integer id, String description, String no,
                      String remarks, School school) {
        this(description, no, remarks, school);
        this.id = id;
    }

    //定义不含有id字段的构造器
    public Department(String description, String no,
                      String remarks, School school) {
        super();
        this.description = description;
        this.no = no;
        this.remarks = remarks;
        this.school = school;
    }

    //定义getId方法返回id值
    public Integer getId() {
        return id;
    }

    //定义setId设置id值
    public void setId(Integer id) {
        this.id = id;
    }

    //定义getDescription方法返回description值
    public String getDescription() {
        return description;
    }

    //定义setDescription设置description值
    public void setDescription(String description) {
        this.description = description;
    }

    //定义getNo方法返回no值
    public String getNo() {
        return no;
    }

    //定义setNo设置no值
    public void setNo(String no) {
        this.no = no;
    }

    //定义getRemarks方法返回remarks值
    public String getRemarks() {
        return remarks;
    }

    //定义setRemarks设置remarks值
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    //定义getSchool方法返回School对象
    public School getSchool() {
        return school;
    }

    //定义setSchool设置School对象属性
    public void setSchool(School school) {
        this.school = school;
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

        retValue = "Department ( "
                + super.toString() + TAB
                + "id = " + this.id + TAB
                + "description = " + this.description + TAB
                + "no = " + this.no + TAB
                + "remarks = " + this.remarks + TAB + "\n"
                + "school_json = " + this.school + TAB
                + " )";

        return retValue;
    }

    //实现接口中的方法compareTo对类对象进行排序
    @Override
    public int compareTo(Department o) {
        // TODO Auto-generated method stub
        return this.id - o.id;
    }

}
