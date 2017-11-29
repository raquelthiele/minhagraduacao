package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class Course {
    private final String code;
    private final CourseType courseType;
    private String name;
    private int creditPoints;
    private int flunksQuantity;
    private boolean isEnrolled;
    private List<CourseStatus> statusList;
    private HashMap<Enum<CourseStatus>,List<Double>> statusGradeMap;

    public Course(String code, CourseStatus courseStatus) {
        this.code = code;
        this.courseType = evaluateCourseType();
        this.flunksQuantity = 0;
        this.isEnrolled = false;
        this.statusList = new ArrayList<CourseStatus>();
        this.statusList.add(courseStatus);
    }

    public String getCode() {
        return code;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public String getName() {
        return name;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public int getFlunksQuantity() {
        return flunksQuantity;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public List<CourseStatus> getStatusList() {
        return statusList;
    }

    public void addStatus(CourseStatus status) {
        if(status.equals(CourseStatus.ASC)){
            isEnrolled = true;
            return;
        }
        else if(status.equals(CourseStatus.REF) || status.equals(CourseStatus.REP)){
            flunksQuantity++;
        }
        this.statusList.add(status);
    }

    public HashMap<Enum<CourseStatus>,List<Double>> getStatusGradeMap() {
        return statusGradeMap;
    }

    public void addToStatusGradeMap(Enum<CourseStatus> courseStatus, Double grade) {
        if(!this.statusGradeMap.containsKey(courseStatus)){
            List<Double> grades = new ArrayList<Double>();
            grades.add(grade);
            this.statusGradeMap.put(courseStatus, grades);
        }
        else{
            this.statusGradeMap.get(courseStatus).add(grade);
        }
    }

    public CourseStatus lastStatus(){
        return statusList.get(statusList.size()-1);
    }

    private CourseType evaluateCourseType() {
        if (Arrays.asList(Major.MANDATORY_COURSES).contains(this.code)) return CourseType.MANDATORY;
        else if (Arrays.asList(Major.OPTINAL_COURSES).contains(this.code)) return CourseType.OPTIONAL;
        else return CourseType.ELECTIVE;
    }

    @Override
    public String toString() {
        StringBuilder printBuilder = new StringBuilder("--------------");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append(" Course Code: ");
        printBuilder.append(this.code);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append(" Course Type: ");
        printBuilder.append(this.courseType);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
//        printBuilder.append(" Course Name :");
//        printBuilder.append(this.name);
//        printBuilder.append(lineBreak);
//        printBuilder.append(" Credit Points :");
//        printBuilder.append(this.creditPoints);
//        printBuilder.append(lineBreak);
        printBuilder.append(" Status: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(CourseStatus status : statusList){
            printBuilder.append("    ").append(status.toString());
            printBuilder.append(HtmlGenerator.LINE_BREAK);
        }

        return printBuilder.toString();

    }
}
