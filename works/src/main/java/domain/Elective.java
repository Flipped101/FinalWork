package domain;

import java.io.Serializable;

//实现Comparable 接口进行排序、实现Serializable 接口保证类是可序列化的，便于存储和传输
public final class Elective implements Comparable<Elective>, Serializable {
    //定义私有字段
    private Student student;
    private Course course;

    //定义含有全部字段的构造器
    public Elective(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    //定义getStudent方法返回id值
    public Student getStudent() {
        return student;
    }

    //定义setId设置id值
    public void setStudent(Student student) {
        this.student = student;
    }

    //定义getCourse方法返回Course对象
    public Course getCourse() {
        return course;
    }

    //定义setCourse设置Course对象属性
    public void setCourse(Course course) {
        this.course = course;
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

        retValue = "Elective ( "
                + super.toString() + TAB
                + "course_json = " + this.course + TAB
                + "student_json = " + this.student + TAB
                + " )";

        return retValue;
    }

    //实现接口中的方法compareTo对类对象进行排序
    @Override
    public int compareTo(Elective o) {
        // TODO Auto-generated method stub
        return this.student.getId() - o.student.getId();
    }

}
