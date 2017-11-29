package model;

/**
 *
 */
public enum CourseType {
    MANDATORY("Obrigatória"),
    OPTIONAL("Opcional"),
    ELECTIVE("Eletiva");

    private final String courseType;

    CourseType(String courseType){
        this.courseType = courseType;
    }

    @Override
    public String toString() {
        return courseType;
    }
}
