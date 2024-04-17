package org.confluence.mod.util;

import net.minecraftforge.fml.loading.FMLPaths;
import org.confluence.mod.Confluence;

import javax.imageio.ImageIO;
import javax.tools.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
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
                String uniStr = hexR + hexG;
                if (rgba[3] == 127) uniStr = "00" + hexR;
                if (uniStr.equals("0a00")) {
                    text.append("\n");
                } else if (!uniStr.equals("000") && !uniStr.equals("0000")) {
                    text.append((char) Integer.parseInt(uniStr, 16));
                }
            }
        }
        return text.toString();
    }

    private static class StringObject extends SimpleJavaFileObject {
        private final String content;

        StringObject(String className, String contents) throws URISyntaxException {
            super(new URI(className), Kind.SOURCE);
            this.content = contents;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }

    private static class GameDirClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] bytes = Files.readAllBytes(FMLPaths.GAMEDIR.get().resolve(name + ".class"));
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
        }
    }
}
