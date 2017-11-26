package model;

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

    public void setCoursers(List<Course> coursers) {
        this.coursers = coursers;
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
        return "Student Registration Number: " + this.registrationNumber + "\n"
                + "Student Registration Date: " + this.registrationDate + "\n"
                + "Student Current Semester: " + this.currentSemester  + "\n"
                + "Student GPA: " + this.gradePointAverage + "\n";
    }
}
