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
    private HashMap<Enum<CourseStatus>,List<Double>> statusGradeMap;

    public Course(String code, String name, int creditPoints) {
        this.code = code;
        this.name = name;
        this.creditPoints = creditPoints;
        this.statusGradeMap = new HashMap<Enum<CourseStatus>,List<Double>>();
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
}
