/**
 * @author Mihir Hindocha
 * @author Mansi Sethia
 * @author Shruti Jaiswal
 * <p>
 * This class defines the Visitor class in the Visitor pattern used by the Agent and Instrumentation API.
 */
package edu.utdallas;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassTransformVisitor extends ClassVisitor implements Opcodes {

    private String className;

    public ClassTransformVisitor(final ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        super.visit(version, access, className, signature, superName, interfaces);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, methodName, descriptor, signature, exceptions);
        Type methodType = Type.getMethodType(descriptor);
        return mv == null ? null : new MethodTransformVisitor(mv, className, access, methodName, methodType.getArgumentTypes());
    }
}
