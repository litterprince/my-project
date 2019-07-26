package com.spring.base;

import java.util.List;

public interface BaseMongoDao<T> {
    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> findAll();

    /**
     * 用于分页查询
     *
     * @param skip(第一个坐标为0)
     * @param limit
     * @return
     */
    List<T> findList(int skip, int limit);

    /**
     * 保存用户
     *
     * @param T
     */
    void store(T T);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    T findOne(String id);

    /**
     * 根据用户名查询
     *
     * @param T
     * @return
     */
    List<T> findByParam(T t);

    /**
     * 更新
     *
     * @param T
     */
    void updateFirst(T T);

    /**
     * 删除
     *
     * @param ids
     */
    void delete(String... ids);
}
