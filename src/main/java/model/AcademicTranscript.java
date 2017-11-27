package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o historico de um aluno
 */
public class AcademicTranscript {
    private String registrationNumber;
    private String registrationDate;
    private String currentSemester;
    private List<Course> coursers;
    private int totalCreditPoints;
    private Double gradePointAverage;

    public AcademicTranscript() {
        this.coursers = new ArrayList<Course>();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        setRegistrationDate(this.registrationNumber);
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationNumber) {
        this.registrationDate = registrationNumber.substring(0, Math.min(registrationNumber.length(), 5));
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public List<Course> getCoursers() {
        return coursers;
    }

    public void addCourse(String courseCode, CourseStatus status) {
        boolean hasCourse = false;
        for(Course course : coursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        if (!hasCourse){
            Course course = new Course(courseCode);
            course.addStatus(status);
            this.coursers.add(course);
        }
    }

    public int getTotalCreditPoints() {
        return totalCreditPoints;
    }

    public void setTotalCreditPoints(int totalCreditPoints) {
        this.totalCreditPoints = totalCreditPoints;
    }

    public Double getGradePointAverage() {
        return gradePointAverage;
    }

    public void setGradePointAverage(Double gradePointAverage) {
        this.gradePointAverage = gradePointAverage;
    }

    @Override
    public String toString(){
        StringBuilder printBuilder = new StringBuilder();
        String lineBreak = "\n";

        printBuilder.append("Student Registration Number: ");
        printBuilder.append(this.registrationNumber);
        printBuilder.append(lineBreak);
        printBuilder.append("Student Registration Date: ");
        printBuilder.append(this.registrationDate);
        printBuilder.append(lineBreak);
        printBuilder.append("Student Current Semester: ");
        printBuilder.append(this.currentSemester);
        printBuilder.append(lineBreak);
        printBuilder.append("Student GPA: ");
        printBuilder.append(this.gradePointAverage);
        printBuilder.append(lineBreak);
        printBuilder.append("Courses :");
        printBuilder.append(lineBreak);
        for(Course course : this.coursers){
            printBuilder.append(course);
            printBuilder.append(lineBreak);
        }
        return  printBuilder.toString();

    }
}
