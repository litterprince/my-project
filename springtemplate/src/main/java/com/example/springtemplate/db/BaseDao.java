package com.example.springtemplate.db;

import com.example.springtemplate.db.mapping.DbBeanProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> {
    @Resource
    public JdbcTemplate jdbcTemplate;

    public int update(T bean) throws Exception {
        SqlHolder sqlHolder = SqlBuilder.buildUpdate(bean);
        return this.update(sqlHolder.getSql(), sqlHolder.getParams());
    }

    public int update(String sql, Object[] param) throws Exception {
        return this.jdbcTemplate.update(sql, param);
    }

    public int save(T bean)throws Exception{
        SqlHolder localSqlHolder = SqlBuilder.buildInsert(bean);
        return update(localSqlHolder.getSql(), localSqlHolder.getParams());
    }

    /**
     * insert or update
     * */
    public int[] batch(String paramString, List<Object[]> paramList) throws Exception {
        return this.jdbcTemplate.batchUpdate(paramString, paramList);
    }

    public List<Map<String, Object>> find(String paramString, Object[] paramArrayOfObject) throws Exception {
        if (StringUtils.isNotBlank(paramString)) {
            paramString = paramString.toUpperCase();
        }
        return this.jdbcTemplate.queryForList(paramString, paramArrayOfObject);
    }

    /**
     * 可能会出现字段转换报错问题，慎用
     * */
    public List<T> find(Class<T> paramClass, String paramString, Object ...paramArrayOfObject) throws Exception {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(paramString, paramArrayOfObject);
        List<T> beanlist = new ArrayList<T>();
        for(Map<String, Object> map : list){
            T bean = (T) mapToObject(map, paramClass);
            beanlist.add(bean);
        }
        return beanlist;
    }

    public Map<String, Object> findMap(String paramString, Object[] paramArrayOfObject) {
        try {
            if (StringUtils.isNotBlank(paramString))
                paramString = paramString.toUpperCase();
            return this.jdbcTemplate.queryForMap(paramString, paramArrayOfObject);
        }
        catch (Exception localException) { }
        return null;
    }

    public T find(Class<T> paramClass, String id){
        T bean = null;
        try {
            SqlHolder localSqlHolder = SqlBuilder.buildGet(paramClass, id);
            bean = (T) this.jdbcTemplate.queryForObject(localSqlHolder.getSql(), localSqlHolder.getParams(), new BeanPropertyRowMapper(paramClass));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return bean;
    }

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            Object value = map.get(DbBeanProcessor.camel2Underline(field.getName()));
            field.set(obj, value);
        }

        return obj;
    }
}