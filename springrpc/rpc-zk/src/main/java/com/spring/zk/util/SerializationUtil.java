package com.spring.zk.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用Protostuff序列化工具
 */
public class SerializationUtil {
    // TODO: 学习，protostuff的使用
    private static Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(cls);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] data = null;
        try {
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T deserialize(byte[] data, Class<T> cls) {
        Schema<T> schema = (Schema<T>) schemaCache.get(cls);
        T obj = schema.newMessage();
        try {
            ProtostuffIOUtil.mergeFrom(data, obj, schema);
        } catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    private static <T> Schema<T> getSchema(Class<?> cls){
        Schema<T> schema = (Schema<T>) schemaCache.get(cls);
        if(Objects.isNull(schema)){
            schema = (Schema<T>) RuntimeSchema.getSchema(cls);
            if(Objects.nonNull(schema)){
                schemaCache.put(cls, schema);
            }
        }
        return schema;
    }
}
