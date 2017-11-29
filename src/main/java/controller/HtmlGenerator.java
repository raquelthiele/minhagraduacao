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
 *
 */
public class HtmlGenerator {
    private static final String YES = "Yes.";
    private static final String NO = "No.";
    private static final String HIGHER = "higher";
    private static final String LOWER = "lower";
    public static final String LINE_BREAK = "<br/>";
    private static final String HTML_HEADER = "<!DOCTYPE html>\n<html>\n<body>\n<h1>Degree Schedule</h1>\n" ;
    private static final String HTML_FOOTER = "</body>\n</html>";
    private static final String WHITE = "#ffffff";
    private static final String RED = "#ff0000";
    private static final String GREEN = "#00ff00";
    private static final Double MIN_GPA = 4.0;

    private final AcademicTranscript academicTranscript;

    public HtmlGenerator(AcademicTranscript academicTranscript) {
        this.academicTranscript = academicTranscript;
    }

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

    private String createHtmlCode(String degreeSchedulePath) {
        return HTML_HEADER +
                academicTranscript +
                LINE_BREAK +
                academicTranscript +
                LINE_BREAK +
                LINE_BREAK +
                answerQuestions() +
                LINE_BREAK +
                readSvg(degreeSchedulePath) +
                HTML_FOOTER;
    }

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
                translateHigherOrLower(academicTranscript.isGpaHigherOrLowerThanAverage()) +
                " than 7.0." +
                LINE_BREAK;
    }

    private String translateYesOrNo(boolean trueOrFalse){
        return (trueOrFalse ? YES : NO);
    }

    private String translateHigherOrLower(boolean trueOrFalse){
        return (trueOrFalse ? HIGHER : LOWER);
    }

    private String readSvg(String degreeSchedulePath){
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

    private Document paintSvg(Document document){
        NodeList pathsNodesList = document.getElementsByTagName("path");
        for (int i = 0; i < pathsNodesList.getLength(); i++) {
            Element pathElement = (Element) pathsNodesList.item(i);
            String pathId = pathElement.getAttributes().getNamedItem("id").getNodeValue();
            if (pathId.matches("[A-Z]{3}[0-9]{4}")){
                selectCourseAndPaint(pathElement, pathId, CourseType.MANDATORY);
            }
            else if (pathId.contains("OPTATIVA")){
                selectCourseAndPaint(pathElement, pathId, CourseType.OPTIONAL);
            }
            else if (pathId.contains("ELETIVA")){
                selectCourseAndPaint(pathElement, pathId, CourseType.ELECTIVE);
            }
        }
        return document;
    }

    private void selectCourseAndPaint(Element pathElement, String pathId, CourseType courseType) {
        String redOrGreen;
        Course course = academicTranscript.getCourse(pathId, courseType);
        if (course != null) {
            if (course.lastStatus() == CourseStatus.APV) redOrGreen = GREEN;
            else redOrGreen = RED;
            paintNode(pathElement, redOrGreen);
        }
    }

    private void paintNode(Element pathElement, String redOrGreen) {
        Node pathStyle = pathElement.getAttributes().getNamedItem("style");
        String replacedStyleValue = pathStyle.getNodeValue().replaceAll(WHITE, redOrGreen);
        pathStyle.setNodeValue(replacedStyleValue);
    }
}
