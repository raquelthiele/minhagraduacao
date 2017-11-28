package controller;

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
    public static final String HTML_HEADER = "<!DOCTYPE html>\n<html>\n<body>\n<h1>Degree Schedule</h1>\n" ;
    public static final String HTML_FOOTER = "</body>\n</html>";

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
        printBuilder.append(readSvg(degreeSchedulePath));
        printBuilder.append(LINE_BREAK);
        printBuilder.append(answerQuestions(academicTranscript));
        printBuilder.append(LINE_BREAK);
        printBuilder.append(HTML_FOOTER);
        return printBuilder.toString();
    }

    private String readSvg(String degreeSchedulePath){
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        FileReader fr = null;
        StringBuilder svgFile = new StringBuilder();

        try {
            fileInputStream = new FileInputStream(degreeSchedulePath);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF8" );
            fr = new FileReader(degreeSchedulePath);
            br = new BufferedReader(inputStreamReader);
            String currentLine;
            boolean hasSvgTag = false;
            String lineBreak = "\n";
            while ((currentLine = br.readLine()) != null) {
                if (hasSvgTag || hasSvgTag(currentLine)){
                    hasSvgTag = true;
                    svgFile.append(lineBreak);
                    svgFile.append(currentLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (fr != null) fr.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return svgFile.toString();
    }

    private boolean hasSvgTag(String currentLine){
        return (currentLine.contains("<svg"));
    }

    private String paintSvg(){
        return "";
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
}
