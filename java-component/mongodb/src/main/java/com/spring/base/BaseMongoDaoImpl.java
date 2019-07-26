package com.spring.base;

import com.spring.util.SqlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class BaseMongoDaoImpl<T> implements BaseMongoDao<T> {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    public List<T> findAll() {
        return this.mongoTemplate.find(new Query(), getTClass());
    }

    @Override
    public List<T> findList(int skip, int limit) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));
        query.skip(skip).limit(limit);
        return this.mongoTemplate.find(query,  getTClass());
    }

    @Override
    public void store(T t) {
        mongoTemplate.save(t);
    }

    @Override
    public T findOne(String id) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query,  getTClass());
    }

    @Override
    public List<T> findByParam(T t) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<Criteria>();
        Map<String, String> params = SqlBuilder.getFiledValues(t);
        Criteria[] list = new Criteria[params.size()];
        Set<Map.Entry<String, String>> setEntry = params.entrySet();
        for(Map.Entry<String, String> entry : setEntry){
            if(StringUtils.isNotBlank(entry.getValue())) {
                Criteria criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                criteriaList.add(criteria);
            }
        }
        Criteria cr = new Criteria().andOperator(criteriaList.toArray(list));
        query.addCriteria(cr);
        return this.mongoTemplate.find(query,  getTClass());
    }

    @Override
    public void updateFirst(T t) {
        String id = "";
        Update update = new Update();
        Query query = new Query();
        Map<String, String> params = SqlBuilder.getFiledValues(t);
        Set<Map.Entry<String, String>> setEntry = params.entrySet();
        for(Map.Entry<String, String> entry : setEntry){
            if(StringUtils.isNotBlank(entry.getValue())) {
                if("id".equals(entry.getKey())) id = entry.getValue();
                update.set(entry.getKey(), entry.getKey());
            }
        }
        this.mongoTemplate.updateFirst(query.addCriteria(Criteria.where("_id").is(id)), update, getTClass());
    }

    @Override
    public void delete(String... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        for (String id : ids) {
            Query query = new Query(Criteria.where("_id").is(id));
            this.mongoTemplate.remove(query, getTClass());
        }
    }
}
