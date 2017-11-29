package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class Course {
    private final String code;
    private String name;
    private int creditPoints;
    private int flunksQuantity;
    private List<CourseStatus> statusList;
    private HashMap<Enum<CourseStatus>,List<Double>> statusGradeMap;

    public Course(String code) {
        this.code = code;
        this.flunksQuantity = 0;
        this.statusList = new ArrayList<CourseStatus>();
    }

    public String getCode() {
        return code;
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

    public List<CourseStatus> getStatusList() {
        return statusList;
    }

    public void addStatus(CourseStatus status) {
        this.statusList.add(status);
        if(status.equals(CourseStatus.REF) || status.equals(CourseStatus.REP)){
            flunksQuantity++;
        }
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

    @Override
    public String toString() {
        StringBuilder printBuilder = new StringBuilder("--------------");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append(" Course Code: ");
        printBuilder.append(this.code);
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
