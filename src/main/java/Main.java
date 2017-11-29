import controller.ExecutionPlanner;

/**
 * Classe principal utilizada para inicialização do programa.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Error: Two arguments are required to initialize this program.");
            System.exit(1);
        }

        ExecutionPlanner executionPlanner = ExecutionPlanner.getInstance();
        ExecutionPlanner.setDegreeSchedulePath(args[0]);
        ExecutionPlanner.setAcademicTranscriptPath(args[1]);
        ExecutionPlanner.run();
    }
}
