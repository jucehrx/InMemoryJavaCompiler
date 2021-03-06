package org.mdkt.compiler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trung on 5/3/15.
 */
public class DynamicClassLoader extends ClassLoader {

    private Map<String, CompiledCode> customCompiledCode = new HashMap<>();

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void setCode(CompiledCode cc) {
        customCompiledCode.put(cc.getName(), cc);
    }

    public CompiledCode getCompiledCode(String className) {
        return customCompiledCode.get(className);
    }

    public Map<String, CompiledCode> getCustomCompiledCode() {
        return customCompiledCode;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        CompiledCode cc = customCompiledCode.get(name);
        if (cc == null) {
            return super.findClass(name);
        }
        byte[] byteCode = cc.getByteCode();
        return defineClass(name, byteCode, 0, byteCode.length);
    }

    public Class<?> getClass(String name, byte[] data) throws Exception {
        return defineClass(name, data, 0, data.length);
    }
}
