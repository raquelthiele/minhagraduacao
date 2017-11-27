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
public class PdfManager {

    public AcademicTranscript initializeReadingAndProcessingPdf(String academicTranscriptPath) {
        AcademicTranscript academicTranscript = new AcademicTranscript();
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(academicTranscriptPath));
            for (int page = 1; page <= pdfDoc.getNumberOfPages(); page++) {
                ITextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
                processPage(academicTranscript, PdfTextExtractor.getTextFromPage(pdfDoc.getPage(page), strategy));
            }
            System.out.println(academicTranscript.toString());
            pdfDoc.close();
            return academicTranscript;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void processPage(AcademicTranscript academicTranscript, String pageContent){
        String [] lines = pageContent.split("\\n");
        boolean hasRegistrationNumber = false;
        boolean hasCurrentSemester = false;
        boolean hasGradePointAverage = false;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!hasRegistrationNumber && hasRegistrationNumber(line)){
                hasRegistrationNumber = true;
                String [] words = line.split("\\s+");
                academicTranscript.setRegistrationNumber(words[0]);
            }
            if (!hasCurrentSemester && hasCurrentSemester(line)){
                hasCurrentSemester = true;
                String [] words = line.split("\\s+");
                academicTranscript.setCurrentSemester(words[2]);
            }
            if (!hasGradePointAverage && hasGradePointAverage(line)){
                hasGradePointAverage = true;
                String [] words = line.split("\\s+");
                academicTranscript.setGradePointAverage(Double.parseDouble(words[4].replaceAll(",","\\.")));
            }
            if (hasGrade(line)){
                processGradeLine(academicTranscript, line);
            }
        }
    }

    public static boolean hasRegistrationNumber(String line){
        return ( line.contains("Matrícula:"));
    }

    public static boolean hasCurrentSemester(String line){
        return ( line.contains("Período Atual:"));
    }

    public static boolean hasGradePointAverage(String line){
        return ( line.contains("Coeficiente de Rendimento Geral:"));
    }

    public static boolean hasGrade(String line){
        return ( line.contains("APV") || line.contains("REF") || line.contains("REP "));
    }

    private static void processGradeLine(AcademicTranscript academicTranscript, String line){
        String [] words = line.split("\\s+");
        CourseStatus status;
        if (line.contains("APV")){
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

}
