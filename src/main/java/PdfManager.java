import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.*;
import java.io.IOException;

/**
 * Classe responsavel pela leitura do pdf
 * Created by raque on 06/11/2017.
 */
public class PdfManager {

    private String path;


    public PdfManager(String path) {
        this.path = path;
    }


    public void parse() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(path));
       // LocationTextExtractionStrategy strategy = new LocationTextExtractionStrategy();
      //  Rectangle rect = new Rectangle(36, 750, 523, 56);

        for (int page = 1; page <= pdfDoc.getNumberOfPages(); page++) {
            ITextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String currentText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(page), strategy);
            System.out.println(currentText);
        }

        pdfDoc.close();
    }

}
