package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe do modelo que representa o curso feito pelo aluno.
 *
 * É responsável pelo armazenamento do código, tipo, quantidade de reprovações e lista de situaçãos que este curso
 *  se encontrou ou se encontra.
 *
 *  Contém o metodo lastStatus() que retorna a ultima situação daquele curso.
 *  Contém o método evaluateCourseType() que de acordo com o código do curso retorna o tipo dele.
 */
public class Course {

    /**
     * Contém o código do curso.
     */
    private final String code;

    /**
     * Tipo do curso (obrigatório, optativo ou eletivo).
     */
    private final CourseType courseType;

    /**
     * Quantidade de reprovações.
     */
    private int flunksQuantity;

    /**
     * Lista com todas as situações do curso.
     */
    private List<CourseStatus> statusList;

    /**
     * Contrutor da classe especificando o código e a situação.
     * O tipo de curso é avaliado de acordo com o código.
     * A quantidade de reprovações é inicializada com zero.
     *
     * @param code         Código do curso.
     * @param courseStatus Situação inicial do curso.
     */
    public Course(String code, CourseStatus courseStatus) {
        this.code = code;
        courseType = evaluateCourseType();
        flunksQuantity = 0;
        statusList = new ArrayList<>();
        statusList.add(courseStatus);
    }

    /**
     * Retorna o código do curso.
     * @return Código do curso.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retorna o tipo do curso.
     * @return Tipo do curso.
     */
    public CourseType getCourseType() {
        return courseType;
    }

    /**
     * Retorna a quantidade de reprovações neste curso.
     * @return Quantidade de reprovações.
     */
    public int getFlunksQuantity() {
        return flunksQuantity;
    }

    /**
     * Recebe uma situação e caso esta seja:
     *  matrícula: não é adicionado a lista de situações pois ainda não é considerado uma aprovação ou reprovação.
     *  reprovação por falta ou por nota: acrescenta um na quantidade de reprovações.
     *  Caso seja uma reprovação ou aprovação esta é adicionado na lista de situações.
     * @param status Situação do curso em um semestre.
     */
    public void addStatus(CourseStatus status) {
        if(status == CourseStatus.ASC){
            return;
        } else if(status == CourseStatus.REF || status == CourseStatus.REP){
            flunksQuantity++;
        }
        statusList.add(status);
    }

    /**
     * Retorna a ultima situação daquele curso, ou seja, a mais recente.
     * @return Situação mais recente deste curso.
     */
    public CourseStatus lastStatus(){
        return statusList.get(statusList.size() - 1);
    }

    /**
     *  Retorna o tipo de curso de acordo com o código do curso.
     *  Este método consulta a lista códigos de cursos obrigatórios da graduação, caso o código esteja presente este
     *  curso é obrigatório.
     *  Caso não seja, a lista de códigos de cursos optativos é consultada, caso o código esteja presente este
     *  curso é optativo.
     *  Caso não seja também, o curso não é ofertado pela escola de informatica e assim é eletivo.
     * @return Tipo do curso.
     */
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
