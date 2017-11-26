package model;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;

import java.io.IOException;

/**
 * Classe responsavel pela leitura e processamento do pdf.
 *
 */
public class PdfManager {

    private String path;

    public PdfManager(String path) {
        this.path = path;
    }

    public String read() {
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));
            String pagesContent = new String();
            for (int page = 1; page <= pdfDoc.getNumberOfPages(); page++) {
                ITextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
                pagesContent += PdfTextExtractor.getTextFromPage(pdfDoc.getPage(page), strategy);
            }

            pdfDoc.close();
            return pagesContent;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error on reading pdf.";
    }
    public void processPdf(String pagesContent){
        AcademicTranscript academicTranscript = new AcademicTranscript();
        String [] lines = pagesContent.split("\\n");
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

            }
            System.out.println(line);
        }
        System.out.println(academicTranscript.toString());
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
        return ( line.contains("APV") || line.contains("DIS") || line.contains("REP"));
    }


}
