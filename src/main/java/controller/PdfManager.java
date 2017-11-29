package controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import model.AcademicTranscript;
import model.CourseStatus;

import java.io.IOException;

/**
 * Classe responsavel pela leitura e processamento do pdf.
 *
 */
 class PdfManager {

    private static boolean hasRegistrationNumber(String line){
        return ( line.contains("Matrícula:"));
    }

    private static boolean hasCurrentSemester(String line){
        return ( line.contains("Período Atual:"));
    }

    private static boolean hasGradePointAverage(String line){
        return ( line.contains("Coeficiente de Rendimento Geral:"));
    }

    private static boolean hasGrade(String line){
        return ( line.contains("ASC - Matrícula")
                || line.contains("APV")
                || line.contains("REF")
                || line.contains("REP "));
    }

    private static void processGradeLine(AcademicTranscript academicTranscript, String line){
        String [] words = line.split("\\s+");
        CourseStatus status;
        if (line.contains("ASC - Matrícula")){
            status = CourseStatus.ASC;
        }
        else if (line.contains("APV")){
            status = CourseStatus.APV;
        }
        else if (line.contains("REF")){
            status = CourseStatus.REF;
        }
        else {
            status = CourseStatus.REP;
        }
        academicTranscript.addCourse(words[0], status);
    }

    public AcademicTranscript initializeReadingAndProcessingPdf(String academicTranscriptPath) {
        AcademicTranscript academicTranscript = new AcademicTranscript();
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(academicTranscriptPath));
            for (int page = 1; page <= pdfDoc.getNumberOfPages(); page++) {
                ITextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
                processPage(academicTranscript, PdfTextExtractor.getTextFromPage(pdfDoc.getPage(page), strategy));
            }
            pdfDoc.close();
            return academicTranscript;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void processPage(AcademicTranscript academicTranscript, String pageContent){
        String [] lines = pageContent.split("\\n");
        boolean hasRegistrationNumber = false;
        boolean hasCurrentSemester = false;
        boolean hasGradePointAverage = false;
        for (String line : lines) {
            if (!hasRegistrationNumber && hasRegistrationNumber(line)) {
                hasRegistrationNumber = true;
                String[] words = line.split("\\s+");
                academicTranscript.setRegistrationNumber(words[0]);
            }
            if (!hasCurrentSemester && hasCurrentSemester(line)) {
                hasCurrentSemester = true;
                String[] words = line.split("\\s+");
                academicTranscript.setCurrentSemester(
                        Integer.parseInt(words[2].substring(0, Math.min(2, words[2].length()))));
            }
            if (!hasGradePointAverage && hasGradePointAverage(line)) {
                hasGradePointAverage = true;
                String[] words = line.split("\\s+");
                academicTranscript.setGradePointAverage(Double.parseDouble(words[4].replaceAll(",", "\\.")));
            }
            if (hasGrade(line)) {
                processGradeLine(academicTranscript, line);
            }
        }
    }

}
