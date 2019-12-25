package domain;

import java.io.Serializable;

//实现Comparable 接口进行排序、实现Serializable 接口保证类是可序列化的，便于存储和传输
public final class Course implements Comparable<Course>, Serializable {
    //定义私有字段
    private Integer id;
    private String description;
    private String no;
    private Integer credit;
    private Teacher teacher;
    private String category;
    private String time;
    private String place;
    private Integer countNum;

    //定义含有全部字段的构造器
    public Course(Integer id, String description, String no,
                  Integer credit, Teacher teacher, String category,
                  String time, String place, Integer countNum) {
        this(description, no, credit, teacher, category, time, place, countNum);
        this.id = id;
    }

    //定义不含有id字段的构造器
    public Course(String description, String no,
                  Integer credit, Teacher teacher, String category,
                  String time, String place, Integer countNum) {
        super();
        this.description = description;
        this.no = no;
        this.credit = credit;
        this.teacher = teacher;
        this.category = category;
        this.time = time;
        this.place = place;
        this.countNum = countNum;
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

    //定义getPlace方法返回place值
    public String getPlace() {
        return place;
    }

    //定义setPlace设置place值
    public void setPlace(String place) {
        this.place = place;
    }

    //定义getCredit方法返回credit值
    public Integer getCredit() {
        return credit;
    }

    //定义setCredit设置credit值
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    //定义getTeacher方法返回Teacher对象
    public Teacher getTeacher() {
        return teacher;
    }

    //定义setTeacher设置Teacher对象属性
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    //定义getCategory方法返回category值
    public String getCategory() {
        return category;
    }

    //定义setCategory设置category值
    public void setCategory(String category) {
        this.category = category;
    }

    //定义getTime方法返回time值
    public String getTime() {
        return time;
    }

    //定义setTime设置time值
    public void setTime(String time) {
        this.time = time;
    }

    //定义getNo方法返回no值
    public String getNo() {
        return no;
    }

    //定义setNo设置no值
    public void setNo(String no) {
        this.no = no;
    }

    //定义getCountNum方法返回countNum值
    public Integer getCountNum() {
        return countNum;
    }

    //定义setCountNum设置countNum值
    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
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

        retValue = "Course ( "
                + super.toString() + TAB
                + "id = " + this.id + TAB
                + "description = " + this.description + TAB
                + "category = " + this.category + TAB
                + "time = " + this.time + TAB
                + "place = " + this.place + TAB
                + "no = " + this.no + TAB
                + "credit = " + this.credit + TAB + "\n"
                + "teacher_json = " + this.teacher + TAB
                + " )";

        return retValue;
    }

    //实现接口中的方法compareTo对类对象进行排序
    @Override
    public int compareTo(Course o) {
        // TODO Auto-generated method stub
        return this.id - o.id;
    }

}
