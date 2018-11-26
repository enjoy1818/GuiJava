package sample;

public class Exam {
    private String examSolution;
    private String examName;
    private String examNumber;
    public Exam(){}
    public Exam(String examNumber, String examName, String examSolution){
        this.examName = examName;
        this.examNumber = examNumber;
        this.examSolution = examSolution;
    }
    public String getExamSolution() {
        return this.examSolution;
    }

    public String getExamName() {
        return this.examName;
    }

    public String getExamNumber() {
        return this.examNumber;
    }
}
