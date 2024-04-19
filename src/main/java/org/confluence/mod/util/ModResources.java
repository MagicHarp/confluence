package org.confluence.mod.util;

import org.confluence.mod.Confluence;

import javax.imageio.ImageIO;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;

public class ModResources {
    public static void initialize() throws ClassNotFoundException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        try {
            Iterable<? extends JavaFileObject> classes = Collections.singletonList(new StringObject("ConfluenceMod.java", getResource()));
            javaCompiler.getTask(null, fileManager, null, null, null, classes).call();
            GameDirClassLoader classLoader = new GameDirClassLoader();
            Class<?> confluenceMod = classLoader.loadClass("ConfluenceMod");
            Method main = confluenceMod.getDeclaredMethod("main", String[].class);
            main.invoke(null, (Object) new String[0]);
            fileManager.close();
        } catch (Exception e) {
            throw new ClassNotFoundException("ConfluenceMod");
        }
    }

    private static String getResource() throws IOException {
        BufferedImage bimg = ImageIO.read(Confluence.class.getResourceAsStream("/resource.png"));
        StringBuilder text = new StringBuilder();
        for (int y = 0; y < bimg.getHeight(); y++) {
            for (int x = 0; x < bimg.getWidth(); x++) {
                byte[] rgba = (byte[]) bimg.getRaster().getDataElements(x, y, null);
                String hexR = Integer.toHexString(rgba[0]);
                if (hexR.length() == 1) hexR = "0" + hexR;
                String hexG = Integer.toHexString(rgba[1]);
                if (rgba[3] == (byte) 255 && hexG.length() == 1) hexG = "0" + hexG;
                String uniStr;
                if (rgba[3] == 127) uniStr = "00" + hexR;
                else uniStr = hexR + hexG;
                if (uniStr.equals("0a00")) {
                    text.append("\n");
                } else if (!uniStr.equals("000") && !uniStr.equals("0000")) {
                    text.append((char) Integer.parseInt(uniStr, 16));
                }
            }
        }
        return text.toString();
    }
}
