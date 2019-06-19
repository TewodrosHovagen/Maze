package Utils.packageclasscode;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class Try {

    public static void main(String[] args) {

        List<Class<?>> classes = getClassesInPackage("Utils.packageclassdata.data.anotherpackage");
        for (Class<?> c: classes){
            Object o = null;
            try {
                o = c.getDeclaredConstructor().newInstance();
            } catch (InstantiationException |IllegalAccessException |NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            try {
                Method method = c.getMethod("run");
                System.out.println(c);
                try {
                    method.invoke(o);
                } catch (IllegalAccessException |InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }


        }
    }

    public static final List<Class<?>> getClassesInPackage(String packageName) {
        String path = packageName.replace(".", File.separator);
        List<Class<?>> classes = new ArrayList<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );

        String name;
        System.out.println("classPathEntries length: "+ classPathEntries.length);
        for (String classpathEntry : classPathEntries) {
                try {
                    System.out.println("classpathEntry: "+ classpathEntry);
                    File base = new File(classpathEntry + File.separatorChar + path);
                    System.out.println("base.listFiles(): "+ base.listFiles());
                    for (File file : base.listFiles()) {
                        name = file.getName();
                        if (name.endsWith(".class")
                                && name.startsWith("Player")
                                && !name.equals("Player.class")
                                && !name.equals("PlayerInterface.class")
                                && !name.contains("$")) {
                            System.out.println("class name before substring: "+ name);
                            name = name.substring(0, name.length() - 6);
                            System.out.println("class name: "+ name);
                            classes.add(Class.forName(packageName + "." + name));
                        }
                    }
                } catch (Exception ex) {
                    // Silence is gold
                }

        }

        return classes;
    }

}
