package com.wgllss.music.plugin;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.V1_8;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;


public class ASMTransform3 extends Transform {
    static File destJarFile;
    static File destClassFile;

    @Override
    public String getName() {
        return "wgllss";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
//        ---------------------jar---------------------------------------
        for (TransformInput input : inputs) {
            for (JarInput directoryInput : input.getJarInputs()) {
                File dest = outputProvider.getContentLocation(
                        directoryInput.getName(), directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.JAR
                );
                scanJar(directoryInput.getFile(), dest);
                FileUtils.copyFile(directoryInput.getFile(), dest);
            }

//            ------------------------------class------------------------------------
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                String dirName = directoryInput.getName();
                File dest = outputProvider.getContentLocation(
                        dirName, directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY
                );
                FileUtils.copyDirectory(directoryInput.getFile(), dest);
            }
        }

        if (destJarFile != null) {
            createClassInToJar(destJarFile);
        }
    }


    private void scanJar(File jarSrc, File dest) throws IOException {
        JarFile file = new JarFile(jarSrc);
        Enumeration<JarEntry> enumeration = file.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement();
            String entryName = jarEntry.getName();
            if (shouldTargetJarClass(entryName)) {
                destJarFile = dest;
            }
        }
    }

    private static boolean shouldTargetJarClass(String entryName) {
        //com.wgllss.dynamic.host.library
        return entryName != null && entryName.equals("com/wgllss/dynamic/host/library/SampleDownLoaderFace.class");
    }

    private static byte[] writeBytesToJar(InputStream inputStream) throws IOException {
        ClassWriter cw = new ClassWriter(0);
        genClass(cw);
        return cw.toByteArray();
    }

    private static void genClass(ClassWriter classWriter) {
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;
        classWriter.visit(V1_8, ACC_PUBLIC | Opcodes.ACC_FINAL | ACC_SUPER, "com/wgllss/music/lib/MusicDemo", null, "java/lang/Object", null);
        classWriter.visitSource("MusicDemo.kt", null);

        {
            annotationVisitor0 = classWriter.visitAnnotation("Lkotlin/Metadata;", true);
            annotationVisitor0.visit("mv", new int[]{1, 7, 1});
            annotationVisitor0.visit("k", new Integer(1));
            annotationVisitor0.visit("xi", new Integer(48));
            {
                AnnotationVisitor annotationVisitor1 = annotationVisitor0.visitArray("d1");
                annotationVisitor1.visit(null, "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0009\u0010\u0003\u001a\u00020\u0004H\u0086 \u00a8\u0006\u0005");
                annotationVisitor1.visitEnd();
            }
            {
                AnnotationVisitor annotationVisitor1 = annotationVisitor0.visitArray("d2");
                annotationVisitor1.visit(null, "Lcom/wgllss/music/lib/MusicDemo;");
                annotationVisitor1.visit(null, "");
                annotationVisitor1.visit(null, "()V");
                annotationVisitor1.visit(null, "getDemoUrl");
                annotationVisitor1.visit(null, "");
                annotationVisitor1.visit(null, "Dynamic-Host-Library_debug");
                annotationVisitor1.visitEnd();
            }
            annotationVisitor0.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(3, label0);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(5, label1);
            methodVisitor.visitInsn(Opcodes.NOP);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(6, label2);
            methodVisitor.visitLdcInsn("hello2-jni");
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "loadLibrary", "(Ljava/lang/String;)V", false);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(7, label3);
            methodVisitor.visitInsn(Opcodes.NOP);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(3, label4);
            methodVisitor.visitInsn(Opcodes.RETURN);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLocalVariable("this", "Lcom/wgllss/music/lib/MusicDemo;", null, label0, label5, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_NATIVE, "getDemoUrl", "()Ljava/lang/String;", null, null);
            {
                annotationVisitor0 = methodVisitor.visitAnnotation("Lorg/jetbrains/annotations/NotNull;", false);
                annotationVisitor0.visitEnd();
            }
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();
    }

    static class ScanClassVisitor extends ClassVisitor {
        public ScanClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        /**
         * 当有一个方法执行了，就执行这个API一次，类中有多个方法，这里就会执行多次
         */
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("getDownUrl")) {
                mv = new MyWMethodVisitor(Opcodes.ASM7, access, desc, mv);
            }
            return mv;
        }
    }

    static class MyWMethodVisitor extends LocalVariablesSorter {

        public MyWMethodVisitor(final int api, final int access, final String des, final MethodVisitor mv) {
            super(api, access, des, mv);
        }


        @Override
        public void visitLineNumber(int line, Label start) {
            super.visitLineNumber(line, start);
            if (line == 8) {
                mv.visitLineNumber(line, start);
                mv.visitTypeInsn(Opcodes.NEW, "com/wgllss/music/lib/MusicDemo");
                mv.visitInsn(Opcodes.DUP);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/wgllss/music/lib/MusicDemo", "<init>", "()V", false);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/wgllss/music/lib/MusicDemo", "getDemoUrl", "()Ljava/lang/String;", false);
                mv.visitInsn(Opcodes.ARETURN);
            }
        }
    }

    private static void createClassInToJar(File jarFile) throws IOException {
        File optJar = new File(jarFile.getParent(), jarFile.getName() + ".opt");
        if (optJar.exists()) {
            optJar.delete();
        }


        JarFile file = new JarFile(jarFile);
        Enumeration<JarEntry> enumeration = file.entries();
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar));
        ZipEntry zipEntry0 = new ZipEntry("com/wgllss/music/lib/MusicDemo.class");
        jarOutputStream.putNextEntry(zipEntry0);
        jarOutputStream.write(writeBytesToJar(null));
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement();
            String entryName = jarEntry.getName();
            ZipEntry zipEntry = new ZipEntry(entryName);
            InputStream inputStream = file.getInputStream(jarEntry);
            jarOutputStream.putNextEntry(zipEntry);
            if (shouldTargetJarClass(entryName)) {
                byte[] bytes = wirteBytesToJarClass(inputStream);
                jarOutputStream.write(bytes);
            } else {
                jarOutputStream.write(IOUtils.toByteArray(inputStream));
            }
            inputStream.close();
            jarOutputStream.closeEntry();
        }

        jarOutputStream.close();
        file.close();
        if (jarFile.exists()) {
            jarFile.delete();
        }
        optJar.renameTo(jarFile);
    }

    private static byte[] wirteBytesToJarClass(InputStream inputStream) throws IOException {
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new ScanClassVisitor(Opcodes.ASM5, cw);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }

}
