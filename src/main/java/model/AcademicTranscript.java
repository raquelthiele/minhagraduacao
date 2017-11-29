package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o historico de um aluno.
 */
public class AcademicTranscript {
    private static final Integer SECOND_REGULATION = 20141;
    private static final Integer COURSES_QUANTITY_TO_GRADUATE = 47;
    private static final Double AVERAGE_GRADE = 7.0;
    private static final Integer MIN_ENROlLED_COURSES = 3;
    private static final Double MAX_COURSES_PER_SEMESTER = 7.0;
    private static final int FIRST_INDEX = 0;
    private String registrationNumber;
    private Integer registrationDate;
    private Integer currentSemester;
    private List<Course> mandatoryCoursers;
    private List<Course> optionalCoursers;
    private List<Course> electiveCoursers;
    private Double gradePointAverage;
    private int approvedOnCoursesQuantity;
    private int enrolledCoursesQuantity;

    public AcademicTranscript() {
        this.mandatoryCoursers = new ArrayList<>();
        this.optionalCoursers = new ArrayList<>();
        this.electiveCoursers = new ArrayList<>();
        this.approvedOnCoursesQuantity = 0;
        this.enrolledCoursesQuantity = 0;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        setRegistrationDate(this.registrationNumber);
    }

    private void setRegistrationDate(String registrationNumber) {
        this.registrationDate = Integer.parseInt(registrationNumber.substring(0, Math.min(registrationNumber.length(), 5)));
    }

    public void setCurrentSemester(Integer currentSemester) {
        this.currentSemester = currentSemester;
    }

    public void addCourse(String courseCode, CourseStatus status) {
        if (increaseCoursesCount(status)) return;
        boolean hasCourse = false;
        courseCode = recastSourceCode(courseCode);
        hasCourse = checkIfHasCourseAndAddStatus(courseCode, status, hasCourse);
        if (!hasCourse) createNewCourseAndAddToRespectiveList(courseCode, status);
    }

    private boolean increaseCoursesCount(CourseStatus status) {
        if (status.equals(CourseStatus.ASC)) {
            enrolledCoursesQuantity++;
            return true;
        }
        else if (status.equals(CourseStatus.APV)) approvedOnCoursesQuantity++;
        return false;
    }

    private String recastSourceCode(String courseCode) {
        if (courseCode.equals("3")) return "HTD0058";
        else if (courseCode.equals("TIN0110")) return "TIN0010";
        return courseCode;
    }

    private void createNewCourseAndAddToRespectiveList(String courseCode, CourseStatus status) {
        Course course = new Course(courseCode, status);
        if (course.getCourseType().equals(CourseType.MANDATORY)) this.mandatoryCoursers.add(course);
        if (course.getCourseType().equals(CourseType.OPTIONAL)) this.optionalCoursers.add(course);
        if (course.getCourseType().equals(CourseType.ELECTIVE)) this.electiveCoursers.add(course);
    }

    private boolean checkIfHasCourseAndAddStatus(String courseCode, CourseStatus status, boolean hasCourse) {
        for(Course course : mandatoryCoursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        for(Course course : optionalCoursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        for(Course course : electiveCoursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        return hasCourse;
    }

    public Double getGradePointAverage() {
        return gradePointAverage;
    }

    public void setGradePointAverage(Double gradePointAverage) {
        this.gradePointAverage = gradePointAverage;
    }

    public boolean hasMoreThanFourFlunksInTheSameCourse(){
        for (Course course : mandatoryCoursers){
            if (course.getFlunksQuantity() >= 4){
                return true;
            }
        }
        for (Course course : optionalCoursers){
            if (course.getFlunksQuantity() >= 4){
                return true;
            }
        }
        for (Course course : electiveCoursers){
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

    public Course getCourse(String courseCode, CourseType courseType) {
        if (courseType.equals(CourseType.MANDATORY)) {
            for (Course mandatoryCourse : mandatoryCoursers) {
                if (mandatoryCourse.getCode().equals(courseCode)) return mandatoryCourse;
            }
        }
        if ((courseType.equals(CourseType.OPTIONAL)) && (optionalCoursers.size() > 0)) {
            if (removeCoursesUntilApv(CourseType.OPTIONAL)) return null;
            return optionalCoursers.remove(FIRST_INDEX);

        }
        if (courseType.equals(CourseType.ELECTIVE) && (electiveCoursers.size() > 0)) {
            if (removeCoursesUntilApv(CourseType.ELECTIVE)) return null;
            return electiveCoursers.remove(FIRST_INDEX);
        }
        return null;
    }

    private boolean removeCoursesUntilApv(CourseType courseType) {
        List<Course> courses;
        if (courseType.equals(CourseType.OPTIONAL)) courses = optionalCoursers;
        else courses = electiveCoursers;
        while (!courses.get(FIRST_INDEX).lastStatus().equals(CourseStatus.APV)) {
            courses.remove(FIRST_INDEX);
            if (courses.size() < 1) return true;
        }
        return false;
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
        printBuilder.append("Mandatory Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : this.mandatoryCoursers){
            printBuilder.append(course);
        }
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Optional Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : this.optionalCoursers){
            printBuilder.append(course);
        }
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Elective Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : this.electiveCoursers){
            printBuilder.append(course);
        }
        return  printBuilder.toString();

    }
}
