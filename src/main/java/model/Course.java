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
        courseType = evaluateCourseType();
        flunksQuantity = 0;
        statusList = new ArrayList<>();
        statusList.add(courseStatus);
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
        if(status == CourseStatus.ASC){
            return;
        }
        else if(status == CourseStatus.REF || status == CourseStatus.REP){
            flunksQuantity++;
        }
        statusList.add(status);
    }

    public CourseStatus lastStatus(){
        return statusList.get(statusList.size()-1);
    }

    private CourseType evaluateCourseType() {
        if (Arrays.asList(Major.MANDATORY_COURSES).contains(code)) return CourseType.MANDATORY;
        else if (Arrays.asList(Major.OPTINAL_COURSES).contains(code)) return CourseType.OPTIONAL;
        else return CourseType.ELECTIVE;
    }

    @Override
    public String toString() {
        StringBuilder printBuilder = new StringBuilder("--------------");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append(" Course Code: ");
        printBuilder.append(code);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append(" Course Type: ");
        printBuilder.append(courseType);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append(" Status: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(CourseStatus status : statusList){
            printBuilder.append("    ").append(status);
            printBuilder.append(HtmlGenerator.LINE_BREAK);
        }

        return printBuilder.toString();

    }
}
