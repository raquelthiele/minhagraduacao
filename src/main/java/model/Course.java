package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class Course {
    private String code;
    private String name;
    private int creditPoints;
    private List<CourseStatus> status;
    private HashMap<Enum<CourseStatus>,List<Double>> statusGradeMap;

    public Course(String code) {
        this.code = code;
        this.status = new ArrayList<CourseStatus>();
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

    public List<CourseStatus> getStatus() {
        return status;
    }

    public void addStatus(CourseStatus status) {
        this.status.add(status);
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

    @Override
    public String toString() {
        StringBuilder printBuilder = new StringBuilder("--------------");
        String lineBreak = "<br />";
        printBuilder.append(lineBreak);
        printBuilder.append(" Course Code: ");
        printBuilder.append(this.code);
        printBuilder.append(lineBreak);
//        printBuilder.append(" Course Name :");
//        printBuilder.append(this.name);
//        printBuilder.append(lineBreak);
//        printBuilder.append(" Credit Points :");
//        printBuilder.append(this.creditPoints);
//        printBuilder.append(lineBreak);
        printBuilder.append(" Status: ");
        printBuilder.append(lineBreak);
        for(CourseStatus stat : status){
            printBuilder.append("    " + stat.toString());
            printBuilder.append(lineBreak);
        }

        return printBuilder.toString();

    }
}
