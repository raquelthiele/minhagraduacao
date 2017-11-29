package controller;

import model.AcademicTranscript;

/**
 *
 */
public class ExecutionPlanner {

    private static ExecutionPlanner instance = null;
    private static String degreeSchedulePath;
    private static String academicTranscriptPath;

    private ExecutionPlanner() {
        // Exists only to defeat instantiation.
    }

    public static ExecutionPlanner getInstance() {
        if(instance == null) {
            instance = new ExecutionPlanner();
        }
        return instance;
    }

    public void setDegreeSchedulePath(String degreeSchedulePath) {
        ExecutionPlanner.degreeSchedulePath = degreeSchedulePath;
    }

    public void setAcademicTranscriptPath(String academicTranscriptPath) {
        ExecutionPlanner.academicTranscriptPath = academicTranscriptPath;
    }

    public void run(){
        PdfManager manager = new PdfManager();
        AcademicTranscript academicTranscript = manager.initializeReadingAndProcessingPdf(academicTranscriptPath);

        if (academicTranscript != null) {
            HtmlGenerator htmlGenerator = new HtmlGenerator(academicTranscript);
            htmlGenerator.generateHtmlFile(degreeSchedulePath);
        }
    }
}
