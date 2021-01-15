package jvm.homework;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * xlass类加载作业
 * Created by Daiwei on 2021/1/10
 */
public class HelloXlassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object hello = new HelloXlassLoader().findClass("Hello").newInstance();
            Method method = hello.getClass().getMethod("hello");
            method.invoke(hello);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getBytesFromFile();
        handleBytes(bytes);
        if (bytes.length == 0) {
            System.out.println("file load error");
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] getBytesFromFile() {
        try {
            File file = new File("src/jvm/homework/resource/Hello.xlass");
            FileInputStream is = new FileInputStream(file);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private void handleBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]  = (byte)~bytes[i];
        }
    }
}
