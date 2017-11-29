import controller.ExecutionPlanner;

/**
 * Classe principal utilizada para a inicialização do sistema.
 * O sistema deve receber como 2 parametros como argumento:
 *
 *   O argumento 1 passado para o sistema deve ser o caminho do Fluxograma do BSI em formato SVG.
 *
 *   O argumento 2 passado para o sistema deve ser o caminho para o Historico do aluno em formato PDF.
 *
 */
public class Main {

    /**
     * Construtor da classe com os argumentos.
     *
     * @param args argumentos passados quando se inicia o sistema.
     */
    public static void main(String[] args) {

        /**
         * Verifica se foram passados 2 argumentos
         * caso contrario o sistema é finalizado.
         */
        if (args.length != 2){
            System.out.println("Error: Two arguments are required to initialize this program.");
            System.exit(1);
        }

        /**
         * A classe que chama o leitor de pdf e o escritor de Html é chamada e seus argumentos inicializados.
         *
         * O argumento 1 passado para o sistema deve ser o caminho do Fluxograma do BSI em formato SVG.
         */
        ExecutionPlanner.getInstance().setDegreeSchedulePath(args[0]);

        /**
         * O argumento 2 passado para o sistema deve ser o caminho para o Historico do aluno em formato PDF.
         */
        ExecutionPlanner.getInstance().setAcademicTranscriptPath(args[1]);

        /**
         * Este metodo é chamado para dar unicio ao sistema.
         */
        ExecutionPlanner.getInstance().run();
    }
}
