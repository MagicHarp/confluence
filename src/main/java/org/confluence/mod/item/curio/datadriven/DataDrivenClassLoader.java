package org.confluence.mod.item.curio.datadriven;

final class DataDrivenClassLoader extends ClassLoader {
    DataDrivenClassLoader(ClassLoader parent) {
        super(parent);
    }

    Class<?> defineClass(String name, byte[] bytes) {
        return super.defineClass(name, bytes, 0, bytes.length);
    }
}
