package fr.istic.vv;

import java.nio.file.Path;

/**
 * Make a report of a java project
 */
public class ReportMaker{

    private Path p;
    //private ????? report

    /**
     * @param p path of the project
     */
    public ReportMaker(Path p) {
        this.p = p;
    }

    /**
     * Make the report in a file
     * @return the report
     */
    public void makeReport(){
        //TODO
    }
    
    /**
     * write report in a file
     * @param dest destination path for the report
     */
    public void writeReport(Path dest){
        //TODO
    }
    
    /**
     * write report in a file
     * @param src source path of a report
     */
    public void loadReport(Path src){
        //TODO
    }

    /**
     * Display the histogram of the report on STDOUT 
     */
    public void histogram() {
        //TODO
    }

}