package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o historico de um aluno
 */
public class AcademicTranscript {
    private String registrationNumber;
    private Integer registrationDate;
    private Integer currentSemester;
    private List<Course> coursers;
    private int totalCreditPoints;
    private Double gradePointAverage;
    private int approvedOnCoursesQuantity;
    private int enrolledCoursesQuantity;

    private static final Integer FIRST_REGULATION = 20132;
    private static final Integer SECOND_REGULATION = 20141;
    private static final Integer COURSES_QUANTITY_TO_GRADUATE = 47;
    private static final Double AVERAGE_GRADE = 7.0;
    private static final Integer MIN_ENROlLED_COURSES = 3;
    private static final Double MAX_COURSES_PER_SEMESTER = 7.0;

    public AcademicTranscript() {
        this.coursers = new ArrayList<Course>();
        this.approvedOnCoursesQuantity = 0;
        this.enrolledCoursesQuantity = 0;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        setRegistrationDate(this.registrationNumber);
    }

    public int getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationNumber) {
        this.registrationDate = Integer.parseInt(registrationNumber.substring(0, Math.min(registrationNumber.length(), 5)));
    }

    public Integer getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Integer currentSemester) {
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
        if (status.equals(CourseStatus.ASC)) enrolledCoursesQuantity++;
        else if (status.equals(CourseStatus.APV)) approvedOnCoursesQuantity++;
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

    public boolean hasMoreThanFourFlunksInTheSameCourse(){
        for (Course course : coursers){
            if (course.getFlunksQuantity() >= 4){
                return true;
            }
        }
        return false;
    }

    public boolean hasToShowPlan(){
        int compareTo = registrationDate.compareTo(SECOND_REGULATION);
        if(compareTo >= 0 ){
            if (this.currentSemester > 6) return true;
        } else {
            if (this.currentSemester > 10) return true;
        }
        return  false;
    }

    public boolean isEnrolledAtEnoughCourses(){
        if (enrolledCoursesQuantity >= MIN_ENROlLED_COURSES) return true;
        int coursersToDo = COURSES_QUANTITY_TO_GRADUATE - approvedOnCoursesQuantity;
        return coursersToDo == enrolledCoursesQuantity;
    }

    public boolean canGraduateOnTime(){
        int coursersToDo = COURSES_QUANTITY_TO_GRADUATE - approvedOnCoursesQuantity;
        int semestersNeededToGraduate = ((int) Math.ceil(coursersToDo / MAX_COURSES_PER_SEMESTER));
        int compareTo = registrationDate.compareTo(SECOND_REGULATION);
        if(compareTo >= 0 ){
            if ((currentSemester + semestersNeededToGraduate - 1) >= 12) return false;
        } else {
            if ((currentSemester + semestersNeededToGraduate -1) >= 14) return false;
        }
        return true;
    }

    public  boolean isGpaHigherOrLowerThanAverage(){
        return (this.gradePointAverage >= AVERAGE_GRADE);
    }

    @Override
    public String toString(){
        StringBuilder printBuilder = new StringBuilder();
        printBuilder.append("Student Registration Number: ");
        printBuilder.append(this.registrationNumber);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Student Registration Date: ");
        printBuilder.append(this.registrationDate.toString());
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Student Current Semester: ");
        printBuilder.append(this.currentSemester.toString());
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Student Grade Point Average: ");
        printBuilder.append(this.gradePointAverage);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : this.coursers){
            printBuilder.append(course);
        }
        return  printBuilder.toString();

    }
}
