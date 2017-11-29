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
 * Classe do controle responsavel pela leitura e processamento do histórico em formato pdf.
 *
 * Esta classe captura as informações do historico e as armazena no modelo.
 *
 */
class PdfManager {

    /**
     * Verifica se uma linha contém os dados de matricua do aluno.
     *
     * @param line String com a linha do historico.
     * @return Verdadeiro se a linha contém a matricula e falso caso contrario.
     */
    private static boolean hasRegistrationNumber(String line){
        return ( line.contains("Matrícula:"));
    }

    /**
     * Verifica se uma linha contém o semestre atual.
     *
     * @param line String com a linha do historico.
     * @return Verdadeiro se a linha contém semestre atual e falso caso contrario.
     */
    private static boolean hasCurrentSemester(String line){
        return ( line.contains("Período Atual:"));
    }

    /**
     * Verifica se uma linha contém o coeficiente de rendimento (CR) do aluno.
     * @param line String com a linha do historico.
     * @return Verdadeiro se a linha contém o coeficiente de rendimento (CR) e falso caso contrario.
     */
    private static boolean hasGradePointAverage(String line){
        return ( line.contains("Coeficiente de Rendimento Geral:"));
    }

    /**
     * Verifica se uma linha contém uma situação de curso.
     * @param line String com a linha do historico.
     * @return Verdadeiro se a linha contém uma situação de curso e falso caso contrario.
     */
    private static boolean hasGrade(String line){
        return ( line.contains("ASC - Matrícula")
                || line.contains("APV")
                || line.contains("REF")
                || line.contains("REP "));
    }

    /**
     * A linha do historico em pdf com um as informações de um curso é processada.
     * Primeiro a linha é separada em palavras.
     * Uma nova situação de curso é declarada de acordo com a informação da linha.
     * Esta situação e o código do curso, retirado da primeira palavra da linha, são passados para o modelo de
     * historico para que o curso seja adicionado.
     * @param academicTranscript Historico do aluno.
     * @param line Linha do historico em pdf com as informações de curso.
     */
    private static void processGradeLine(AcademicTranscript academicTranscript, String line){
        String [] words = line.split("\\s+");
        CourseStatus status;
        if (line.contains("ASC - Matrícula")){
            status = CourseStatus.ASC;
        } else if (line.contains("APV")){
            status = CourseStatus.APV;
        } else if (line.contains("REF")){
            status = CourseStatus.REF;
        } else {
            status = CourseStatus.REP;
        }
        academicTranscript.addCourse(words[0], status);
    }

    /**
     * Inicia a leitura e processamento do Historico em formato pdf.
     * Uma instancia do modelo historico é iniciada.
     * Com o auxilio do PdfReader, uma instancia do PdfDocument é iniciada contendo o historico.
     * As paginas deste documento são iteradas e o método de processsamento de pagina é chamado.
     * @param academicTranscriptPath caminho para o Historico do aluno em formato PDF.
     * @return modelo historico com os dados capturados do pdf ou null caso haja falha.
     */
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

    /**
     * Processa uma página do historico.
     * Primeiro a pagina é separada por linhas.
     * E cada linha é iterada e  processada por vez.
     * É feita a verificação de se a linha contem uma matricula, se sim esta é colocada no modelo.
     * É feita a verificação de se a linha contem o numero do semestre atual, se sim este é colocado no modelo.
     * É feita a verificação de se a linha contem o CR, se sim este é colocado no modelo.
     * É feita a verificação de se a linha contem um curso, se sim este é colocado no modelo.
     *
     * @param academicTranscript Historico do aluno.
     * @param pageContent Pagina do historico em PDF a ser processada.
     */
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
