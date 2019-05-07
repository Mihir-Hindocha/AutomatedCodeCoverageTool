/**
 * @author Mihir Hindocha
 * @author Mansi Sethia
 * @author Shruti Jaiswal
 * <p>
 * This class has the implementation of the Agent Class. The premain method is executed at the start of the JVM.
 */
package edu.utdallas;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {

        inst.addTransformer(new MyClassTransform());
    }
}
