package controller;

import model.AcademicTranscript;

import java.io.*;

/**
 *
 */
public class HtmlGenerator {
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
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Degree Schedule </h1>\n" +
                academicTranscript.toString() +
                "\n" +
                readSvg(degreeSchedulePath) +
                "\n" +
                "</body>\n" +
                "</html>";
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

    private String paintSvg(){
        return "";
    }

    private boolean hasSvgTag(String currentLine){
        return (currentLine.contains("<svg"));
    }
}
