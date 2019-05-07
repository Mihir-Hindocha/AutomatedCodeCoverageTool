/**
 * @author Mihir Hindocha
 * @author Mansi Sethia
 * @author Shruti Jaiswal
 * <p>
 * This is the class defined to store all the data regarding the statement coverage and then data trace for all the methods.
 */
package edu.utdallas;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

import java.util.HashMap;

public class CodeCoverageCollect {

    public static HashMap<String,HashMap<String, IntSet>> testSuiteCoverage;
    public static HashMap<String, IntSet> testCaseCoverage;
    public static String testCaseName;

    /**
     * This method receives the values from visitLineNumber() and visitLabel() when they are invoked.
     * @param className
     * @param line
     */
    public static void addLineDetails(String className, Integer line){
        if (testCaseCoverage == null) {
            return;
        }

        IntSet lines = testCaseCoverage.get(className);
        if (lines != null) {
            lines.add(line);
        }
        else {
            lines = new IntOpenHashSet(new int[]{line});
            testCaseCoverage.put(className, lines);
        }
    }

    /**
     * This method receives the values from visitCode() when any method is invoked.
     * @param className
     * @param methodName
     * @param objects
     */
    /*public static void getParameterValues(String className, String methodName, Object[] objects){
        System.out.println("\n\nEntered getParameterValues Mehthod\n\n");

        System.out.println("\ngetParameterValues class name "+ className);
        System.out.println("\ngetParameterValues method name "+ methodName);
        for (int i = 0; i < objects.length; i++){
            System.out.println("\ngetParameterValues objectArray " + i + "  "+ objects[i]);
        }

    }*/
}
