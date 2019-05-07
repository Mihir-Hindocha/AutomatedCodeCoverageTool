/**
 * @author Mihir Hindocha
 * @author Mansi Sethia
 * @author Shruti Jaiswal
 * <p>
 * This is the class where we implement the main program logic to capture the required details from the bytecode.
 */
package edu.utdallas;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;

public class MethodTransformVisitor extends MethodVisitor implements Opcodes {

    private int lastVisitedLine;
    private String className;
    private int access;
    private String methodName;
    Type[] parameterTypes;


    public MethodTransformVisitor(MethodVisitor mv, String className, int access, String methodName, Type[] argumentTypes) {
        super(ASM5, mv);
        this.className = className;
        this.access = access;
        this.methodName = methodName;
        parameterTypes = argumentTypes;
    }

    /**
     * This method is used to trace the line numbers of the statements of code that are covered by a test case.
     *
     * @param line
     * @param start
     */
    @Override
    public void visitLineNumber(int line, Label start) {

        if (0 != line) {
            lastVisitedLine = line;

            mv.visitLdcInsn(className);
            mv.visitLdcInsn(new Integer(line));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addLineDetails", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        }
        super.visitLineNumber(line, start);
    }

    /**
     * This method is used to trace the line numbers of the statement of code covered by the test case.
     * This is useful in the case when there are two statements on the same line separated by a ; .
     *
     * @param label
     */
    @Override
    public void visitLabel(Label label) {

        mv.visitLdcInsn(className);
        mv.visitLdcInsn(new Integer(lastVisitedLine));
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addLineDetails", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        super.visitLabel(label);
    }

    /**
     * This method is used to trace the values of the parameters of the method invoking it.
     */
   /* @Override
    public void visitCode() {

        *//**
         * This variable stores the total number of parameters in the method.
         *//*
        int paramLength = parameterTypes.length;

        // System.out.println(className + "." + methodName + ": paramLength = " + paramLength);
        *//**
         * variableIndex stores the initial index for accessing the parameters of the method calling it.
         * For an instance method the first parameter is "this" as parameter and hence the other parameters start from 1.
         * For static methods the parameters start from index 0.
         *//*
        int variableIndex = Modifier.isStatic(access) ? 0 : 1;
        if (paramLength > 0) {

            *//**
             * Create array with length equal to number of parameters + 1
             *//*
            mv.visitIntInsn(Opcodes.BIPUSH, paramLength + 1);
            mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
            mv.visitVarInsn(Opcodes.ASTORE, paramLength + 1);

            for (Type tp : parameterTypes) {
                // System.out.println("tp.getClassName() = " + tp.getClassName());
                // System.out.println("tp.Descriptor() = " + tp.getDescriptor());

                mv.visitVarInsn(Opcodes.ALOAD, paramLength);
                mv.visitIntInsn(Opcodes.BIPUSH, variableIndex);

                *//**
                 * Checking the parameter type with all primitive types and then adding them to the array.
                 *//*
                if (tp.equals(Type.BOOLEAN_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                } else if (tp.equals(Type.BYTE_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                } else if (tp.equals(Type.CHAR_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                } else if (tp.equals(Type.SHORT_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                } else if (tp.equals(Type.INT_TYPE)) {
                    mv.visitVarInsn(Opcodes.ILOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                } else if (tp.equals(Type.LONG_TYPE)) {
                    mv.visitVarInsn(Opcodes.LLOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                    variableIndex++;
                } else if (tp.equals(Type.FLOAT_TYPE)) {
                    mv.visitVarInsn(Opcodes.FLOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                } else if (tp.equals(Type.DOUBLE_TYPE)) {
                    mv.visitVarInsn(Opcodes.DLOAD, variableIndex);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    variableIndex++;
                } else {
                    mv.visitVarInsn(Opcodes.ALOAD, variableIndex);
                }
                mv.visitInsn(Opcodes.AASTORE);
                variableIndex++;
            }

            *//**
             * Load class name and method name.
             *//*
            this.visitLdcInsn(className);
            this.visitLdcInsn(methodName);
            *//**
             * Load the array of parameters that we created.
             *//*
            this.visitVarInsn(Opcodes.ALOAD, variableIndex);

            *//**
             * Send the className, methodName and the object array to getaParameterValues function in CodeCoverageCollect class.
             *//*
            this.visitMethodInsn(Opcodes.INVOKESTATIC, "CodeCoverageCollect", "getParameterValues", "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V", false);
        }
        super.visitCode();
    }*/
}
