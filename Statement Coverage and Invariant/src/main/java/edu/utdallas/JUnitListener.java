/**
 * @author Mihir Hindocha
 * @author Mansi Sethia
 * @author Shruti Jaiswal
 * <p>
 * This class listens to the events like beginning of test case execution, termination of test case execution,
 * beginning of all test case execution and termination of all test case executions.
 */
package edu.utdallas;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import it.unimi.dsi.fastutil.ints.IntSet;

public class JUnitListener extends RunListener {

    /**
     * This method is called before the execution of all test cases.
     *
     * @param description
     * @throws Exception
     */
    public void testRunStarted(Description description) throws Exception {
        if (null == CodeCoverageCollect.testSuiteCoverage) {
            CodeCoverageCollect.testSuiteCoverage = new HashMap<String, HashMap<String, IntSet>>();
        }
    }

    /**
     * This method is called before the execution of each @Test annotation i.e before each test case execution.
     *
     * @param description
     */
    public void testStarted(Description description) {
        CodeCoverageCollect.testCaseName = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
        CodeCoverageCollect.testCaseCoverage = new HashMap<String, IntSet>();
    }

    /**
     * This method is called after the execution of each @Test annotation i.e after each test case execution.
     *
     * @param description
     */
    public void testFinished(Description description) {
        CodeCoverageCollect.testSuiteCoverage.put(CodeCoverageCollect.testCaseName, CodeCoverageCollect.testCaseCoverage);
    }

    /**
     * This method is called after the execution of all test cases.
     *
     * @param result
     * @throws Exception
     */
    public void testRunFinished(Result result) throws IOException {
        // Write all the data to stmt-cov.txt file.
        File fout = new File("stmt-cov.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        StringBuilder builder = new StringBuilder();
        for (String testCaseName : CodeCoverageCollect.testSuiteCoverage.keySet()) {
            builder.append(testCaseName + "\n");

            HashMap<String, IntSet> codeCoverage =
                    CodeCoverageCollect.testSuiteCoverage.get(testCaseName);

            for (String className : codeCoverage.keySet()) {
                int[] lines = codeCoverage.get(className).toIntArray();

                Arrays.sort(lines);
                for (int i = 0; i < lines.length; i++) {
                    builder.append(className + ":" + lines[i] + "\n");
                }
            }
        }
        bw.write(builder.toString());
        bw.close();
    }
}
