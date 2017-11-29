package model;

import controller.HtmlGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe do modelo que representa o historico do aluno.
 *
 * É responsável pelo armazenamento da matrícula, do ano e semestre de entrada na graduação, semestre atual,
 * coeficiente de rendimento (CR), quantidade de cursos em que foi aprovado,  quantidade de cursos
 * matriculado atualmente e as listas de cursos feitos, separados por tipo de curso (obrigatórios,
 * opcionais e eletivos).
 *
 * Esta classe contém 5 métodos booleanos que respondem as perguntas fundamentais feitas pelo sistema.
 *
 * Contém também o método getCourse() que recebe um código de curso e um tipo de curso e retorna se ele esta contido
 * em na lista de cursos feitos relativo ao seu tipo.
 */
public class AcademicTranscript {

    /**
     * String com o ano e semestre em que o novo regulamento da graduação passou a valer.
     */
    private static final Integer SECOND_REGULATION = 20141;

    /**
     * Quantidade de cursos que precisam ser feitos para se formar.
     */
    private static final Integer COURSES_QUANTITY_TO_GRADUATE = 47;

    /**
     * Nota média da graduação.
     */
    private static final Double AVERAGE_GRADE = 7.0;

    /**
     * Mínimo de cursos que um aluno pode estar matriculado em um semestre.
     */
    private static final Integer MIN_ENROlLED_COURSES = 3;

    /**
     * Média de cursos que um aluno esta matriculado em um semestre.
     */
    private static final Double MAX_COURSES_PER_SEMESTER = 7.0;

    /**
     * Número máximo de semestre que um aluno pode cursar de acordo com
     * o novo regulamento da graduação.
     */
    private static final Integer MAX_SEMESTERS_TO_GRADUATE_NEW = 12;

    /**
     * Número máximo de semestre que um aluno pode cursar de acordo com
     * o antigo regulamento da graduação.
     */
    private static final Integer MAX_SEMESTERS_TO_GRADUATE_OLD = 14;

    /**
     * Primeiro item da lista.
     */
    private static final int FIRST_INDEX = 0;


    /**
     * Número de matrícula do aluno.
     */
    private String registrationNumber;

    /**
     * Ano e semestre de entrada na graduação.
     */
    private Integer registrationDate;

    /**
     * Quantidade de semestres cursados até agora.
     */
    private Integer currentSemester;

    /**
     * Coeficiente de rendimento (CR)
     */
    private Double gradePointAverage;

    /**
     * Quantidade de cursos em que foi aprovado.
     */
    private int approvedOnCoursesQuantity;

    /**
     * Quantidade de cursos que está matriculado.
     */
    private int enrolledCoursesQuantity;

    /**
     * Lista de cursos já feitos do tipo obrigatório.
     */
    private List<Course> mandatoryCoursers;

    /**
     * Lista de cursos já feitos do tipo optativo.
     */
    private List<Course> optionalCoursers;

    /**
     * Lista de cursos já feitos do tipo eletivo.
     */
    private List<Course> electiveCoursers;


    /**
     * Construtor da classe.
     */
    public AcademicTranscript() {
        mandatoryCoursers = new ArrayList<>();
        optionalCoursers = new ArrayList<>();
        electiveCoursers = new ArrayList<>();
        approvedOnCoursesQuantity = 0;
        enrolledCoursesQuantity = 0;
    }

    /**
     * Coloca a matrícula do aluno.
     *
     * @param registrationNumber matrícula do aluno.
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        setRegistrationDate(this.registrationNumber);
    }

    /**
     * Coloca o ano e o semestre de entrada na graduação.
     * @param registrationNumber o ano e o semestre de entrada na graduação.
     */
    private void setRegistrationDate(String registrationNumber) {
        registrationDate = Integer.parseInt(registrationNumber.
                substring(0, Math.min(registrationNumber.length(), 5)));
    }

    /**
     * Coloca a quantidade de semestres cursados até agora.
     * @param currentSemester quantidade de semestres cursados
     */
    public void setCurrentSemester(Integer currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     * Adiciona o curso à lista de cursos.
     *
     * Chama o método que verifica se vai atualizar as quantidades de cursos matriculados e aprovados de acordo com a
     * situação.
     * Chama o método que verifica se o código possui um erro conhecido e o trata.
     * Chama o método que verifica se o curso já existe na lista de cursos.
     * Caso não exista, este é criado.
     * @param courseCode String com o código do curso.
     * @param status Situação do curso em um semestre.
     */
    public void addCourse(String courseCode, CourseStatus status) {
        if (increaseCoursesCount(status)) return;
        courseCode = recastCourceCode(courseCode);
        boolean hasCourse = checkIfHasCourseAndAddStatus(courseCode, status);
        if (!hasCourse) createNewCourseAndAddToRespectiveList(courseCode, status);
    }

    /**
     * Atualiza as quantidades de cursos matriculados e aprovados de acordo com a
     * situação.
     * @param status Situação do curso em um dos semestres.
     * @return Verdadeiro caso seja um curso com situação matricula pois este não é adicionado na lista de cursos.
     * Falso caso seja aprovado ou reprovado.
     */
    private boolean increaseCoursesCount(CourseStatus status) {
        if (status == CourseStatus.ASC) {
            enrolledCoursesQuantity++;
            return true;
        } else if (status == CourseStatus.APV) approvedOnCoursesQuantity++;
        return false;
    }

    /**
     * Verifica se o código possui um erro conhecido e o trata.
     * Caso o código seja "3", este é modificado pois este é um erro conhecido de leitura do histórico.
     * Caso o código seja "TIN0110", este é modificado porque esta errado no código do fluxograma lido.
     * @param courseCode Código do curso.
     * @return O mesmo código caso este nao possua erros conhecidos ou o código tratado caso seja um erro conhecido.
     */
    private String recastCourceCode(String courseCode) {
        if (courseCode.equals("3")) return "HTD0058";
        else if (courseCode.equals("TIN0110")) return "TIN0010";
        return courseCode;
    }

    /**
     * Verifica se o curso já existe na lista de cursos de acordo com o seu tipo.
     * <p>
     * Este método itera pela lista do tipo de cursos já cadastrados anteriormente comparando o seu código.
     * Caso o curso seja achado, a nova situação é adicionada e é retornado verdadeiro.
     * Mas caso o curso não seja achado, é retornado falso.
     *
     * @param courseCode Código do curso.
     * @param status     Situação do curso.
     * @return Verdadeiro se o curso foi achado e falso se o curso não foi achado.
     */
    private boolean checkIfHasCourseAndAddStatus(String courseCode, CourseStatus status) {
        boolean hasCourse = false;
        for(Course course : mandatoryCoursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        for(Course course : optionalCoursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        for(Course course : electiveCoursers){
            if(course.getCode().equals(courseCode)){
                course.addStatus(status);
                hasCourse = true;
                break;
            }
        }
        return hasCourse;
    }

    /**
     * Cria um novo curso com o código e situação e o adiciona na lista de cursos de acordo com o seu tipo.
     *
     * @param courseCode Stringo com o código do curso.
     * @param status     Situação do curso em um semestre.
     */
    private void createNewCourseAndAddToRespectiveList(String courseCode, CourseStatus status) {
        Course course = new Course(courseCode, status);
        if (course.getCourseType() == CourseType.MANDATORY) mandatoryCoursers.add(course);
        if (course.getCourseType() == CourseType.OPTIONAL) optionalCoursers.add(course);
        if (course.getCourseType() == CourseType.ELECTIVE) electiveCoursers.add(course);
    }

    /**
     * Retorna o coeficiente de rendimento (CR) do aluno.
     * @return Coeficiente de rendimento (CR) do aluno.
     */
    public Double getGradePointAverage() {
        return gradePointAverage;
    }

    /**
     * Insere o coeficiente de rendimento (CR) do aluno.
     * @param gradePointAverage Coeficiente de rendimento (CR) do aluno.
     */
    public void setGradePointAverage(Double gradePointAverage) {
        this.gradePointAverage = gradePointAverage;
    }


    /**
     * Verifica se o aluno possui mais de 4 reprovações em um mesmo curso.
     * Este método itera por todas as listas de cursos feitos e confere se o valor de reprovações é maior ou igual a 4.
     * @return Verdadeiro caso o aluno possui mais de 4 reprovações em um mesmo curso e falso caso contrario.
     */
    public boolean hasMoreThanFourFlunksInTheSameCourse(){
        for (Course course : mandatoryCoursers){
            if (course.getFlunksQuantity() >= 4){
                return true;
            }
        }
        for (Course course : optionalCoursers){
            if (course.getFlunksQuantity() >= 4){
                return true;
            }
        }
        for (Course course : electiveCoursers){
            if (course.getFlunksQuantity() >= 4){
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o aluno precisa apresentar um plano de integralização.
     * Como existem 2 regulamentos em vigor no BSI e cada um possui uma regra para a obrigatoriedade de entrega
     * do plano de integralização, primeiro é verificado em qual dos regulamentos o aluno se encontra.
     * Caso tenha entrado na graduação antes do primeiro semestre de 2014 ele precisa entregar o plano depois
     * de 10 semestres concluidos.
     * Caso tenha entrado na graduação a partir do primeiro semestre de 2014 ele precisa entregar o plano depois
     * de 6 semestres concluidos.
     * @return Verdadeiro se o aluno precisa entregar o plano de integralização e falso caso não precise.
     */
    public boolean hasToShowPlan(){
        int compareTo = registrationDate.compareTo(SECOND_REGULATION);
        if(compareTo >= 0 ){
            if (currentSemester > 6) return true;
        } else {
            if (currentSemester > 10) return true;
        }
        return false;
    }

    /**
     * Verifica se o aluno esta matriculado na quantidade minima de cursos em um semestre.
     * É verificado se o aluno esta matriculado em 3 cursos ou se esta matriculado na quantidade de materias que
     * faltam para ele se formar (2 ou 1).
     * @return Verdadeiro se o aluno esta matriculado na quantidade minima de cursos em um semestre
     * e falso caso contrario
     */
    public boolean isEnrolledAtEnoughCourses(){
        if (enrolledCoursesQuantity >= MIN_ENROlLED_COURSES) return true;
        int coursersToDo = COURSES_QUANTITY_TO_GRADUATE - approvedOnCoursesQuantity;
        return coursersToDo == enrolledCoursesQuantity;
    }

    /**
     * Verifica se o aluno consegue se formar antes de acabar o prazo maximo de semestres que este pode cursar.
     *
     * Como existem 2 regulamentos em vigor no BSI e cada um possui um prazo de semestres maximo,
     * primeiro é verificado em qual dos regulamentos o aluno se encontra.
     * Caso tenha entrado na graduação antes do primeiro semestre de 2014 ele precisa se formar em
     * no máximo 14 semestes.
     * Caso tenha entrado na graduação a partir do primeiro semestre de 2014 ele precisa se formar em
     * no máximo 12 semestes.
     *
     * Apos, é calculado a quantidade de cursos que faltam para ele se formar de acordo com o numero total de
     * cursos necessario no BSI e é subtraido o numero de materias que ja foram feitas. Este numero é dividido
     * pelo numero médio de cursos que um aluno faz em um semestre (7) e assim se tem a quantidade de semestres que
     * ainda faltam.
     *
     * Este numero é adicionado ao numero do semestre atual (semestres cursados) menos um pois o atual ainda
     * nao possui materias feitas. E o resultado é comparado com com o numero maximo de acordo com o regulamente
     * regente.
     *
     * @return Verdadeiro se o aluno consegue se formar e falso caso não consiga.
     */
    public boolean canGraduateOnTime(){
        int coursersToDo = COURSES_QUANTITY_TO_GRADUATE - approvedOnCoursesQuantity;
        int semestersNeededToGraduate = ((int) Math.ceil(coursersToDo / MAX_COURSES_PER_SEMESTER));
        int compareTo = registrationDate.compareTo(SECOND_REGULATION);
        if(compareTo >= 0 ){
            if ((currentSemester + semestersNeededToGraduate - 1) >= MAX_SEMESTERS_TO_GRADUATE_NEW) return false;
        } else {
            if ((currentSemester + semestersNeededToGraduate -1) >= MAX_SEMESTERS_TO_GRADUATE_OLD) return false;
        }
        return true;
    }

    /**
     * Verifica se o coeficiente de rendimento (CR) é maior ou igual à média (7.0).
     *
     * @return Verdadeiro se o CR é maior ou igual e falso se for menor.
     */
    public boolean isGpaHigherThanAverage(){
        return (gradePointAverage >= AVERAGE_GRADE);
    }


    /**
     * Retorna o curso de acordo com o código e o tipo do curso.
     *
     * Neste método é verificado se um código de curso e um tipo correnpondem á um curso que tenha sifo feito
     * por um aluno.
     * No caso de um curso do tipo obrigatório, a lista de cursos obrigatorios já feitos é iterada e caso haja
     * correlação o curso é retornado.
     * Já no caso do curso ser do tipo optativo ou eletivo, o curso só é retornado se a sua situação mais recente é
     * aprovado.
     * @param courseCode Código do curso.
     * @param courseType Tipo do curso.
     * @return O curso caso ele tenha sido feito ou null caso nao.
     */
    public Course getCourse(String courseCode, CourseType courseType) {
        if (courseType == CourseType.MANDATORY) {
            for (Course mandatoryCourse : mandatoryCoursers) {
                if (mandatoryCourse.getCode().equals(courseCode)) return mandatoryCourse;
            }
        }
        if ((courseType == CourseType.OPTIONAL) && (optionalCoursers.size() > 0)) {
            if (removeCoursesUntilApv(CourseType.OPTIONAL)) return null;
            return optionalCoursers.remove(FIRST_INDEX);

        }
        if (courseType == CourseType.ELECTIVE && (electiveCoursers.size() > 0)) {
            if (removeCoursesUntilApv(CourseType.ELECTIVE)) return null;
            return electiveCoursers.remove(FIRST_INDEX);
        }
        return null;
    }

    /**
     * Remove os cursos da lista de cursos feitos até que haja um com situação aprovado.
     * @param courseType Tipo do curso.
     * @return Verdadeiro caso tenha sido encontrado um curso aprovado e falso caso a lista esteja vazia.
     */
    private boolean removeCoursesUntilApv(CourseType courseType) {
        List<Course> courses;
        if (courseType == CourseType.OPTIONAL) courses = optionalCoursers;
        else courses = electiveCoursers;
        while (courses.get(FIRST_INDEX).lastStatus() != CourseStatus.APV) {
            courses.remove(FIRST_INDEX);
            if (courses.size() < 1) return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder printBuilder = new StringBuilder();
        printBuilder.append("Student Registration Number: ");
        printBuilder.append(registrationNumber);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Student Registration Date: ");
        printBuilder.append(registrationDate);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Student Current Semester: ");
        printBuilder.append(currentSemester);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Student Grade Point Average: ");
        printBuilder.append(gradePointAverage);
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Mandatory Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : mandatoryCoursers){
            printBuilder.append(course);
        }
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Optional Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : optionalCoursers){
            printBuilder.append(course);
        }
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        printBuilder.append("Elective Courses: ");
        printBuilder.append(HtmlGenerator.LINE_BREAK);
        for(Course course : electiveCoursers){
            printBuilder.append(course);
        }
        return  printBuilder.toString();

    }
}
