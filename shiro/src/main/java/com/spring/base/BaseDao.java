package com.spring.base;

import java.util.List;

public interface BaseDao<T> {
    int insertOne(T t);

    int updateOne(T t);

    T selectById(String id);

    List<T> findByParam(T t);

    void batchInsert(List<T> list);

    void batchDelete(List<String> ids);
}
