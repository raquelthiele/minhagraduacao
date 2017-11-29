package model;

/**
 * Enum do modelo que representa os tipos de cursos que existem no BSI.
 * Podendo ser:
 *  Obrigatória quando o aluno tem que fazer a matéria para se formar.
 *  Optativa quando o aluno tem que fazer uma matéria mas ela pode ser escolhida dentre as optativas
 *      oferecidas no semestre.
 *  Eletiva quando o aluno precisa fazer uma matéria que não é ofertada pela escola de informática.
 *
 */
public enum CourseType {
    MANDATORY("Obrigatória"),
    OPTIONAL("Optativa"),
    ELECTIVE("Eletiva");

    /**
     * Tipo de curso
     */
    private final String courseType;

    /**
     * Construtor da classe especificando o seu tipo.
     *
     * @param courseType String com o tipo
     */
    CourseType(String courseType){
        this.courseType = courseType;
    }

    @Override
    public String toString() {
        return courseType;
    }
}
