package model;

import java.util.ArrayList;
import java.util.Objects;

public class Lecturer {
    private String      name;
    private int         id = 0;
    private int     lecturerID;
    private int         numOfQuestionCreated;
    private Department  department;

    private ArrayList<Question> questionArr ;

    public Lecturer() {
        this.name = "";
        this.lecturerID = this.id++;
        this.numOfQuestionCreated = 0;
        this.department = null;
        this.questionArr = new ArrayList<Question>();
    }

    public Lecturer(String name, int seniority, int numOfQuestionCreated, Department department,ArrayList<Question> questionArr) {
        this.name = name;
        this.lecturerID = this.id++;
        this.numOfQuestionCreated = numOfQuestionCreated;
        this.department = department;
        this.questionArr = questionArr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int seniority) {
        this.lecturerID = seniority;
    }

    public int getNumOfQuestionCreated() {
        return numOfQuestionCreated;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean addQuestion(Question question){
        if(this.questionArr.add(question))
        {
            this.numOfQuestionCreated++;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return lecturerID == lecturer.lecturerID && numOfQuestionCreated == lecturer.numOfQuestionCreated && Objects.equals(name, lecturer.name) && Objects.equals(department, lecturer.department) && Objects.equals(questionArr, lecturer.questionArr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lecturerID, numOfQuestionCreated, department, questionArr);
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "name = '" + name + '\'' +
                ", lecturer ID=" + lecturerID +
                ", numOfQuestionCreated = " + numOfQuestionCreated +
                ", department = " + department.getName() +
                ", questionArr = \n" + questionArr +
                '}';
    }
}
