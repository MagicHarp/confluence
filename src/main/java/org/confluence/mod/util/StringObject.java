package org.confluence.mod.util;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;
import java.net.URISyntaxException;

class StringObject extends SimpleJavaFileObject {
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
