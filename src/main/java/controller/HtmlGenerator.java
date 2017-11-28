package controller;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import model.AcademicTranscript;

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
        StringBuilder printBuilder = new StringBuilder();
        printBuilder.append(HTML_HEADER);
        printBuilder.append(academicTranscript.toString());
        printBuilder.append(LINE_BREAK);
        printBuilder.append(academicTranscript.toString());
        printBuilder.append(LINE_BREAK);
        printBuilder.append(LINE_BREAK);
        printBuilder.append(answerQuestions(academicTranscript));
        printBuilder.append(LINE_BREAK);
        printBuilder.append(readSvg(degreeSchedulePath));
        printBuilder.append(HTML_FOOTER);
        return printBuilder.toString();
    }

    private String answerQuestions(AcademicTranscript academicTranscript){
        StringBuilder printBuilder = new StringBuilder();
        printBuilder.append("Does the student need to be expelled? ");
        printBuilder.append(translateYesOrNo(((academicTranscript.getGradePointAverage() <= 4.0)
                && academicTranscript.hasMoreThanFourFlunksInTheSameCourse())));
        printBuilder.append(LINE_BREAK);
        printBuilder.append("Does the student need to submit a plan? ");
        printBuilder.append(translateYesOrNo(academicTranscript.hasToShowPlan()));
        printBuilder.append(LINE_BREAK);
        printBuilder.append("Is the student enrolled in enough courses? ");
        printBuilder.append(translateYesOrNo(academicTranscript.isEnrolledAtEnoughCourses()));
        printBuilder.append(LINE_BREAK);
        printBuilder.append("Is the student able to graduate on time? ");
        printBuilder.append(translateYesOrNo(academicTranscript.canGraduateOnTime()));
        printBuilder.append(LINE_BREAK);
        printBuilder.append("The student's Grade Point Average (GPA) is ");
        printBuilder.append(translateHigherOrLower(academicTranscript.isGpaHigherOrLowerThanAverage()));
        printBuilder.append(" than 7.0.");
        printBuilder.append(LINE_BREAK);
        return printBuilder.toString();
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
        }
        return document;
    }
}
