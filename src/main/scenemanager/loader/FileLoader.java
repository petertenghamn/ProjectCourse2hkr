package main.scenemanager.loader;

import javafx.fxml.FXMLLoader;
import main.scenemanager.SceneManager;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    private SceneManager manager;
    private String dir = "", ext;

    private FileLoader(String ext) {
        this.ext = ext;
        try {
            //Class clazz = sun.reflect.Reflection.getCallerClass(2);
            Class clazz = Class.forName(new Throwable().getStackTrace()[3].getClassName());
            dir = new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath()).toString().replace("%20"," ");
            dir += new File("/scenes");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //ext extension of the type of files needed to be loaded
    public FileLoader(String ext, SceneManager manager){
        this(ext);
        this.manager=manager;
    }

    //Collects the set of files with the given extension and inside the given directory for the FileLoader.
    //return the loaded available files.
    public List<File> collect(){
        List<File> files = new ArrayList<>();
        String list[];

        File folder = new File(dir);

        if(!folder.isDirectory()){
            System.out.println("Not a directory");
            return files;
        }

        if(ext.equalsIgnoreCase("fxml")) {
            list = folder.list(new FXMLExtensionFilter());
        }else {
            list = folder.list(new ExtensionFilter());
        }

        for(String name : list){
            files.add(new File(dir + File.separator + name));
        }

        return files;
    }

    private class FXMLExtensionFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            if(name.endsWith(ext)) {
                try {
                    if(manager!=null) {
                        FXMLLoader loader = new FXMLLoader(new File(dir.toString().concat(File.separator).concat(name)).toURI().toURL());
                        manager.addScene(name.replace("." + ext, ""), loader.load(), loader.getController());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    System.out.println("Exception caught! + " + e);
                }

                return true;
            }
            return false;
        }
    }

    private class ExtensionFilter implements FilenameFilter{
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(ext);
        }
    }
}
