package controller;
import model.PdfManager;

/**
 *
 */
public class ExecutionPlanner {

    private static ExecutionPlanner instance = null;
    private static String degreeSchedulePath;
    private static String academicTranscriptPath;

    protected ExecutionPlanner() {
        // Exists only to defeat instantiation.
    }

    public static ExecutionPlanner getInstance() {
        if(instance == null) {
            instance = new ExecutionPlanner();
        }
        return instance;
    }

    public static String getDegreeSchedulePath() {
        return degreeSchedulePath;
    }

    public static void setDegreeSchedulePath(String degreeSchedulePath) {
        ExecutionPlanner.degreeSchedulePath = degreeSchedulePath;
    }

    public static String getAcademicTranscriptPath() {
        return academicTranscriptPath;
    }

    public static void setAcademicTranscriptPath(String academicTranscriptPath) {
        ExecutionPlanner.academicTranscriptPath = academicTranscriptPath;
    }

    public static void run(){
        PdfManager manager = new PdfManager(academicTranscriptPath);
        manager.processPdf(manager.read());

        SvgPrinter svgPrinter = new SvgPrinter();
        svgPrinter.printSvg();
    }
}
