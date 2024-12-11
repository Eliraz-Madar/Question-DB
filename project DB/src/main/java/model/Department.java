package model;
import java.util.ArrayList;
import java.util.Objects;

public class Department {
    private String name;
    private int numberOfLecturers;
    private int numberOfStudents;

    private ArrayList<Lecturer> lecturerList;

    public Department(){
        this.lecturerList = new ArrayList<Lecturer>();
        this.name = "";
        this.numberOfLecturers = 0;
        this.numberOfStudents = 0;
    }

    String getLecturerNameViaId(int lecturerID){
        for(Lecturer lecturer : lecturerList){
            if(lecturer.getLecturerID() == lecturerID)
                return lecturer.getName().toString();
        }
        return "Not a lecturer";
    }

    public Department(String name, int numberOfLecturers, int numberOfStudents){
        this.lecturerList = new ArrayList<Lecturer>();
        this.name = name;
        this.numberOfLecturers = numberOfLecturers;
        this.numberOfStudents = numberOfStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfLecturers() {
        return numberOfLecturers;
    }


    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public boolean addLecturer(Lecturer lecturer){
        if(this.lecturerList.add(lecturer))
        {
            this.numberOfLecturers++;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return numberOfLecturers == that.numberOfLecturers && numberOfStudents == that.numberOfStudents && name.equals(that.name) && lecturerList.equals(that.lecturerList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfLecturers, numberOfStudents, lecturerList);
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", numberOfLecturers = "+ numberOfLecturers +
                ", numberOfStudents = " + numberOfStudents +
                ", lecturer List = "    + lecturerList.toString() +
                '}';
    }
}
