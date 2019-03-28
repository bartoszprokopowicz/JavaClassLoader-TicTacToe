package TicTacToe;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;

public class ClassReader {

    private ArrayList<Class> arrayClass;


    public ArrayList<Class> getArrayClass(){
        return arrayClass;
    }
    public void setArrayClass(ArrayList<Class> arrayClass){
        this.arrayClass = arrayClass;
    }

    public ClassReader(){
        arrayClass = new ArrayList<>();
    }

    public Class loadClass(Path file){

        URL temp = null;
        Class c = null;
        URLClassLoader loader = null;
        URL[] urlsClass = null;

        Path link = file.getParent().getParent();

        try {
            temp = link.toUri().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String fileName = file.getFileName().toString();
        fileName = fileName.replace(".class","");
        fileName = file.getParent().getFileName().toString() + "." + fileName;

        try {
            urlsClass = new URL[] { temp };
            loader = new URLClassLoader(urlsClass);
            c = Class.forName(fileName,true, loader);
            arrayClass.add(c);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void unloadClass(Path selectedFile){

        String fileName = selectedFile.toString().replace(".class","");
        arrayClass.removeIf(c -> c.getName().contains(fileName));
        System.gc();
        System.out.println("Class unloaded");
    }

    public void unloadClasses(){
        arrayClass.clear();
    }
}
