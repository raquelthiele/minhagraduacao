package model;

/**
 * Enum do modelo contendo as possíveis situações em que um curso pode se encontrar.
 *   Reprovado Por Faltas quando o aluno não passou na matéria por falta de frequencia.
 *   Reprovado por Nota quando o aluno não atinge uma nota suficiente para passar.
 *   Aprovado quando o aluno passa no curso.
 *   Matrícula quando o aluno está inscrito no curso.
 */
public enum CourseStatus {
    REF("Reprovado Por Faltas"),
    REP("Reprovado por Nota"),
    APV("Aprovado"),
    ASC("Matrícula");

    /**
     * Situação do curso
     */
    private final String courseStatus;

    /**
     * Construtor da classe especificando a situação.
     *
     * @param courseStatus String com a situação.
     */
    CourseStatus(String courseStatus){
        this.courseStatus = courseStatus;
    }

    @Override
    public String toString() {
        return courseStatus;
    }
}