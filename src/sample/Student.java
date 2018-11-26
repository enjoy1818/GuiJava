package sample;

public class Student {
    private String name;
    private String studentID;
    private double score;
    public Student(String name, String studentID, double score){
        this.name = name;
        this.studentID = studentID;
        this.score = score;
    }
    public Student(String name, String studentID){
        this.name = name;
        this.studentID = studentID;
    }
    public Student(){
    }
    public String getName(){
        return this.name;
    }
    public String getStudentID(){
        return this.studentID;
    }
    public double getScore(){
        return this.score;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
