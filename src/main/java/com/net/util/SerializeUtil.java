package com.net.util;

import io.protostuff.*;
import io.protostuff.runtime.RuntimeSchema;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @AUTHOR BieFeNg
 * @SINCE 2019/2/25 21:04
 * @DESC
 */

public class SerializeUtil {

    private static final Map<Class, Schema> CACHED_SCHEMA = new ConcurrentHashMap<>();

    public static <T> byte[] serialize(T t) {

        Schema<T> schema = (Schema<T>) getSchema(t.getClass());

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        ProtostuffIOUtil.writeTo(buffer, t, schema);

        return ProtostuffIOUtil.toByteArray(t,schema,buffer);

    }

    public static <T> T unSerialize(){
        return null;
    }

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = CACHED_SCHEMA.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clazz);
            if (schema != null) {
                CACHED_SCHEMA.put(clazz, schema);
            }
        }
        return schema;
    }
}
