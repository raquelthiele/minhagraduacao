package model;

/**
 *
 */
public enum CourseStatus {
    REF("Reprovado Por Faltas"),
    REP("Reprovado por Nota"),
    APV("Aprovado"),
    ASC("Matrícula");

    private final String courseStatus;

    CourseStatus(String courseStatus){
        this.courseStatus = courseStatus;
    }

    @Override
    public String toString() {
        return this.courseStatus;
    }
}