package Utils.packageclasscode;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Try {

    public static void main(String[] args) {

        List<Class<?>> classes = getClassesInPackage("Utils.packageclassdata.data.anotherpackage");
        for (Class<?> c: classes){
            Object o = null;
            try {
                o = c.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            try {
                Method method = c.getMethod("run");
                System.out.println(c);
                try {
                    method.invoke(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
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
        for (String classpathEntry : classPathEntries) {
            if (classpathEntry.endsWith(".jar")) {
                File jar = new File(classpathEntry);
                try {
                    JarInputStream is = new JarInputStream(new FileInputStream(jar));
                    JarEntry entry;
                    while((entry = is.getNextJarEntry()) != null) {
                        name = entry.getName();
                        if (name.endsWith(".class")) {
                            if (name.contains(path) && name.endsWith(".class")) {
                                String classPath = name.substring(0, entry.getName().length() - 6);
                                classPath = classPath.replaceAll("[\\|/]", ".");
                                classes.add(Class.forName(classPath));
                            }
                        }
                    }
                } catch (Exception ex) {
                    // Silence is gold
                }
            } else {
                try {
                    File base = new File(classpathEntry + File.separatorChar + path);
                    for (File file : base.listFiles()) {
                        name = file.getName();
                        if (name.endsWith(".class")) {
                            name = name.substring(0, name.length() - 6);
                            classes.add(Class.forName(packageName + "." + name));
                        }
                    }
                } catch (Exception ex) {
                    // Silence is gold
                }
            }
        }

        return classes;
    }

}
