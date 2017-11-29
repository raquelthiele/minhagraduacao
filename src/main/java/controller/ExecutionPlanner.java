package controller;

import model.AcademicTranscript;

/**
 * Classe do controle do design pattern Singleton responsavel por chamar o leitor de pdf
 * e o escritor de Html.
 */
public class ExecutionPlanner {

    /**
     * Instancia da própria classe que será inicializada somente uma vez.
     */
    private static ExecutionPlanner instance = null;

    /**
     * Caminho do Fluxograma do BSI em formato SVG.
     */
    private static String degreeSchedulePath;

    /**
     * Caminho para o Historico do aluno em formato PDF.
     */
    private static String academicTranscriptPath;

    /**
     * Construtor da classe.
     * Existe para impedir que um objeto seja instanciado de fora da classe, já que por ser private só pode ser chamado
     * de dentro da classe.
     */
    private ExecutionPlanner() {
    }

    /**
     * Retorna uma instancia da classe.
     * Caso uma instancia já tenha sido criada, ela é retornada, caso seja o primeiro acesso, uma nova instancia
     * é criada.
     * @return Instancia da classe.
     */
    public static ExecutionPlanner getInstance() {
        if(instance == null) {
            instance = new ExecutionPlanner();
        }
        return instance;
    }

    /**
     * Coloca o caminho do Fluxograma do BSI em formato SVG.
     *
     * @param degreeSchedulePath Caminho do Fluxograma do BSI em formato SVG.
     */
    public void setDegreeSchedulePath(String degreeSchedulePath) {
        ExecutionPlanner.degreeSchedulePath = degreeSchedulePath;
    }

    /**
     * Coloca o caminho para o Historico do aluno em formato PDF.
     *
     * @param academicTranscriptPath Caminho para o Historico do aluno em formato PDF.
     */
    public void setAcademicTranscriptPath(String academicTranscriptPath) {
        ExecutionPlanner.academicTranscriptPath = academicTranscriptPath;
    }

    /**
     * Inicia a chamada ao leitor de pdf e ao leitor de SVG e escritor de Html.
     * Uma instancia do controlador PdfManager é declarada e seu metodo de leitura e processamento do pdf é chamado.
     * Após, caso o controlador PdfManager tenha conseguido criar uma instancia do modelo Historico com sucesso, o
     * outro controlador HtmlGenerator é declarado e seu metodo geração de HTML do Fluxograma com as cores baseado no
     * Fluxograma em SVG é chamado.
     */
    public void run(){
        PdfManager manager = new PdfManager();
        AcademicTranscript academicTranscript = manager.initializeReadingAndProcessingPdf(academicTranscriptPath);

        if (academicTranscript != null) {
            HtmlGenerator htmlGenerator = new HtmlGenerator(academicTranscript);
            htmlGenerator.generateHtmlFile(degreeSchedulePath);
        }
    }
}
