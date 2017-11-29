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
    public static final String YES = "Yes.";
    public static final String NO = "No.";
    public static final String HIGHER = "higher";
    public static final String LOWER = "lower";
    public static final String LINE_BREAK = "<br/>";
    private static final String HTML_HEADER = "<!DOCTYPE html>\n<html>\n<body>\n<h1>Degree Schedule</h1>\n" ;
    private static final String HTML_FOOTER = "</body>\n</html>";
    private static final String WHITE = "#ffffff";
    private static final String RED = "#ff0000";
    private static final String GREEN = "#00ff00";

    private AcademicTranscript academicTranscript;

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

    public String createHtmlCode(String degreeSchedulePath) {
        String printBuilder = HTML_HEADER +
                academicTranscript.toString() +
                LINE_BREAK +
                academicTranscript.toString() +
                LINE_BREAK +
                LINE_BREAK +
                answerQuestions() +
                LINE_BREAK +
                readSvg(degreeSchedulePath) +
                HTML_FOOTER;
        return printBuilder;
    }

    private String answerQuestions() {
        String printBuilder = "Does the student need to be expelled? " +
                translateYesOrNo(((academicTranscript.getGradePointAverage() <= 4.0)
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
        return printBuilder;
    }

    public String translateYesOrNo(boolean trueOrFalse){
        return (trueOrFalse ? YES : NO);
    }

    public String translateHigherOrLower(boolean trueOrFalse){
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

            paintSvg(doc);

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
            String redOrGreen = GREEN;
            if (pathId.matches("[A-Z]{3}[0-9]{4}")){
                selectCourseAndColor(pathElement, pathId, CourseType.MANDATORY);
            }
            else if (pathId.contains("OPTATIVA")){
                selectCourseAndColor(pathElement, pathId, CourseType.OPTIONAL);
            }
            else if (pathId.contains("ELETIVA")){
                selectCourseAndColor(pathElement, pathId, CourseType.ELECTIVE);
            }
            else if (pathId.contains("OPTATIVA")){
                Node pathStyle = pathElement.getAttributes().getNamedItem("style");
                String replacedStyleValue = pathStyle.getNodeValue().replaceAll("#ffffff", "#ff0000");
                pathStyle.setNodeValue(replacedStyleValue);
            }
            else if (pathId.contains("ELETIVA")){
                Node pathStyle = pathElement.getAttributes().getNamedItem("style");
                String replacedStyleValue = pathStyle.getNodeValue().replaceAll("#ffffff", "#0000ff");
                pathStyle.setNodeValue(replacedStyleValue);
            }
        }
        return document;
    }

    private void selectCourseAndColor(Element pathElement, String pathId, CourseType courseType) {
        String redOrGreen;
        Course course = academicTranscript.getCourse(pathId, courseType);
        if (course != null) {
            if (course.lastStatus().equals(CourseStatus.APV)) redOrGreen = GREEN;
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
