package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Course {
    private final String code;
    private final CourseType courseType;
    private int flunksQuantity;
    private List<CourseStatus> statusList;

    public Course(String code, CourseStatus courseStatus) {
        this.code = code;
        this.courseType = evaluateCourseType();
        this.flunksQuantity = 0;
        this.statusList = new ArrayList<>();
        this.statusList.add(courseStatus);
    }

    public String getCode() {
        return code;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public int getFlunksQuantity() {
        return flunksQuantity;
    }

    public void addStatus(CourseStatus status) {
        if(status.equals(CourseStatus.ASC)){
            return;
        }
        else if(status.equals(CourseStatus.REF) || status.equals(CourseStatus.REP)){
            flunksQuantity++;
        }
        this.statusList.add(status);
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
        printBuilder.append(" Status: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(CourseStatus status : statusList){
            printBuilder.append("    ").append(status.toString());
            printBuilder.append(HtmlGenerator.LINE_BREAK);
        }

        return printBuilder.toString();

    }
}
