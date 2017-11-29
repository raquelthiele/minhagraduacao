package controller;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import model.AcademicTranscript;
import model.Course;
import model.CourseStatus;
import model.CourseType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 * Classe do controle responsavel pela geração do HTML do Fluxograma com as cores baseadas no
 * Fluxograma em SVG lido e no modelo historico.
 */
public class HtmlGenerator {

    /**
     * Tag em HTML que pula uma linha.
     */
    public static final String LINE_BREAK = "<br/>";

    /**
     * Sim.
     */
    private static final String YES = "Yes.";

    /**
     * Não.
     */
    private static final String NO = "No.";

    /**
     * Maior.
     */
    private static final String HIGHER = "higher";

    /**
     * Menor.
     */
    private static final String LOWER = "lower";

    /**
     * Inicio do código HTML.
     */
    private static final String HTML_HEADER = "<!DOCTYPE html>\n<html>\n<body>\n<h1>Degree Schedule</h1>\n" ;

    /**
     * Final do código HTML.
     */
    private static final String HTML_FOOTER = "</body>\n</html>";

    /**
     * Branco.
     */
    private static final String WHITE = "#ffffff";

    /**
     * Vermelho.
     */
    private static final String RED = "#ff0000";

    /**
     * Verde.
     */
    private static final String GREEN = "#00ff00";

    /**
     * Coeficiente de Rendimento Mínimo.
     */
    private static final Double MIN_GPA = 4.0;

    /**
     * Histórico do aluno.
     */
    private final AcademicTranscript academicTranscript;

    /**
     * Construtor da classe com a especificação do historico.
     *
     * @param academicTranscript Histórico do aluno.
     */
    public HtmlGenerator(AcademicTranscript academicTranscript) {
        this.academicTranscript = academicTranscript;
    }

    /**
     * Escreve o aquivo HTML criado na pasta resources do sistema.
     *
     * @param degreeSchedulePath Caminho para o Historico do aluno em formato PDF.
     */
    public void generateHtmlFile(String degreeSchedulePath) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("src\\main\\resources\\DegreeSchedule.html");
            bw = new BufferedWriter(fw);
            bw.write(createHtmlCode(degreeSchedulePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
                if (fw != null) fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Cria o código HTML com o inicio, o historico, as perguntas e suas respostas e o fluxograma pintado.
     * @param degreeSchedulePath Caminho para o Historico do aluno em formato PDF.
     * @return Código HTML com o historico, as perguntas e o fluxograma.
     */
    private String createHtmlCode(String degreeSchedulePath) {
        return HTML_HEADER +
                academicTranscript +
                LINE_BREAK +
                answerQuestions() +
                LINE_BREAK +
                readAndPaintSvg(degreeSchedulePath) +
                HTML_FOOTER;
    }

    /**
     * Responde as perguntas fundamentais do sistema.
     * @return Perguntas do sistema e suas respostas.
     */
    private String answerQuestions() {
        return "Does the student need to be expelled? " +
                translateYesOrNo(((academicTranscript.getGradePointAverage() <= MIN_GPA)
                        && academicTranscript.hasMoreThanFourFlunksInTheSameCourse())) +
                LINE_BREAK +
                "Does the student need to submit a plan? " +
                translateYesOrNo(academicTranscript.hasToShowPlan()) +
                LINE_BREAK +
                "Is the student enrolled in enough courses? " +
                translateYesOrNo(academicTranscript.isEnrolledAtEnoughCourses()) +
                LINE_BREAK +
                "Is the student able to graduate on time? " +
                translateYesOrNo(academicTranscript.canGraduateOnTime()) +
                LINE_BREAK +
                "The student's Grade Point Average (GPA) is " +
                translateHigherOrLower(academicTranscript.isGpaHigherThanAverage()) +
                " than 7.0." +
                LINE_BREAK;
    }

    /**
     * Transforma um boolean em Sim ou Não.
     * @param trueOrFalse Verdadeiro ou Falso.
     * @return Sim caso verdadeiro e Não caso falso.
     */
    private String translateYesOrNo(boolean trueOrFalse){
        return (trueOrFalse ? YES : NO);
    }

    /**
     * Transforma um boolean em maior ou menor.
     * @param trueOrFalse Verdadeiro ou Falso.
     * @return Maior caso verdadeiro e Menor caso falso.
     */
    private String translateHigherOrLower(boolean trueOrFalse){
        return (trueOrFalse ? HIGHER : LOWER);
    }

    /**
     * Le o arquivo SVG, o transforma em um Document, pinta este e o retorna como String.
     *
     * @param degreeSchedulePath Caminho para o Historico do aluno em formato PDF.
     * @return Código SVG com fluxograma e os cursos feitos pintados.
     */
    private String readAndPaintSvg(String degreeSchedulePath){
        String svgFile = null;
        try {
            File fXmlFile = new File(degreeSchedulePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            doc = paintSvg(doc);

            StringWriter stringOut = new StringWriter();
            OutputFormat format = new OutputFormat(doc);
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.serialize(doc);
            svgFile = stringOut.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return svgFile;
    }

    /**
     * Pinta o SVG de acordo com a situação do curso mais recente.
     * @param document Arquivo SVG com o fluxograma.
     * @return Arquivo SVG com o fluxograma com os cursos feitos pintaos.
     */
    private Document paintSvg(Document document){
        NodeList pathsNodesList = document.getElementsByTagName("path");
        for (int i = 0; i < pathsNodesList.getLength(); i++) {
            Element pathElement = (Element) pathsNodesList.item(i);
            String pathId = pathElement.getAttributes().getNamedItem("id").getNodeValue();
            if (pathId.matches("[A-Z]{3}[0-9]{4}")){
                selectCourseAndPaint(pathElement, pathId, CourseType.MANDATORY);
            } else if (pathId.contains("OPTATIVA")){
                selectCourseAndPaint(pathElement, pathId, CourseType.OPTIONAL);
            } else if (pathId.contains("ELETIVA")){
                selectCourseAndPaint(pathElement, pathId, CourseType.ELECTIVE);
            }
        }
        return document;
    }

    /**
     *  Seleciona o curso no SVG e o pinta caso já tenho sido feito.
     * @param pathElement Elemento path do SVG.
     * @param pathId Código do curso.
     * @param courseType Tipo do curso.
     */
    private void selectCourseAndPaint(Element pathElement, String pathId, CourseType courseType) {
        String redOrGreen;
        Course course = academicTranscript.getCourse(pathId, courseType);
        if (course != null) {
            if (course.lastStatus() == CourseStatus.APV) redOrGreen = GREEN;
            else redOrGreen = RED;
            paintNode(pathElement, redOrGreen);
        }
    }

    /**
     *  Pinta o curso já feito de acordo com a situação mais recente.
     * @param pathElement Elemento path do SVG.
     * @param redOrGreen Cor que o curso deve ser pintado.
     */
    private void paintNode(Element pathElement, String redOrGreen) {
        Node pathStyle = pathElement.getAttributes().getNamedItem("style");
        String replacedStyleValue = pathStyle.getNodeValue().replaceAll(WHITE, redOrGreen);
        pathStyle.setNodeValue(replacedStyleValue);
    }
}
