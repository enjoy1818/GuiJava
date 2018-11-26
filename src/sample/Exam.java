package sample;

public class Exam {
    private String examSolution;
    private String examName;
    private String examId;
    public Exam(){}
    public Exam(String examId, String examName){
        this.examId = examId;
        this.examName = examName;
    }
    public String getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }

    public String getExamSolution() {
        return examSolution;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setExamSolution(String examSolution) {
        this.examSolution = examSolution;
    }
}
