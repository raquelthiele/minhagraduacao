package controller;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import model.AcademicTranscript;
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

    public void generateHtmlFile(String degreeSchedulePath, AcademicTranscript academicTranscript) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("src\\main\\resources\\DegreeSchedule.html");
            bw = new BufferedWriter(fw);
            bw.write(createHtmlCode(degreeSchedulePath, academicTranscript));
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

    public String createHtmlCode(String degreeSchedulePath, AcademicTranscript academicTranscript){
        String printBuilder = HTML_HEADER +
                academicTranscript.toString() +
                LINE_BREAK +
                academicTranscript.toString() +
                LINE_BREAK +
                LINE_BREAK +
                answerQuestions(academicTranscript) +
                LINE_BREAK +
                readSvg(degreeSchedulePath) +
                HTML_FOOTER;
        return printBuilder;
    }

    private String answerQuestions(AcademicTranscript academicTranscript){
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
            if (pathId.matches("[A-Z]{3}[0-9]{4}")){
                Node pathStyle = pathElement.getAttributes().getNamedItem("style");
                String replacedStyleValue = pathStyle.getNodeValue().replaceAll("#ffffff", "#00ff00");
                pathStyle.setNodeValue(replacedStyleValue);
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
}
