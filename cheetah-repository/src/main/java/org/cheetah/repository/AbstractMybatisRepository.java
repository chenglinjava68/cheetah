package org.cheetah.repository;

import org.cheetah.commons.utils.Assert;
import org.cheetah.domain.Entity;
import org.cheetah.domain.Repository;
import org.cheetah.domain.TrackingId;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/4/27.
 */
public abstract class AbstractMybatisRepository<E extends Entity, PK extends TrackingId> implements Repository<E, PK> {
    @Autowired
    private SqlSessionTemplate sqlSession;
    private Class<E> entityClass;
    private String baseStatement;

    public AbstractMybatisRepository() {
        Class<?> c = getClass();

        Type type = c.getGenericSuperclass();

        if (type instanceof ParameterizedType) {

            Type[] parameterizedType = ((ParameterizedType) type)

                    .getActualTypeArguments();

            entityClass = (Class<E>) parameterizedType[0];

            String replaceValue = "mapper." + entityClass.getSimpleName()
                    + "Mapper.";
            baseStatement = entityClass.getName().replace(
                    entityClass.getSimpleName(), replaceValue);
        }
    }

    @Override
    public E get(PK id) {
        Assert.notNull(id, "id must not be null");
        String statement = baseStatement() + "get";
        return getSqlSession().selectOne(statement, id);
    }

    @Override
    public void put(E entity) {
        Assert.notNull(entity, "entity must not be null");
        String statement = baseStatement() + "insert";
        getSqlSession().insert(statement, entity);
    }

    @Override
    public int putAll(List<E> entities) {
        Assert.notNull(entities, "entities must not be null");
        String statement = baseStatement() + "putAll";
        return getSqlSession().insert(statement, entities);
    }

    @Override
    public void refresh(E entity) {
        Assert.notNull(entity, "entity must not be null");
        String statement = baseStatement() + "updateByPrimaryKey";
        getSqlSession().update(statement, entity);
    }

    @Override
    public void refreshAll(List<E> entities) {
        Assert.notNull(entities, "entities must not be null");
        String statement = baseStatement() + "refreshAll";
        getSqlSession().update(statement, entities);
    }

    @Override
    public void remove(E entity) {
        Assert.notNull(entity, "entity must not be null");
        String statement = baseStatement() + "deleteByPrimaryKey";
        getSqlSession().delete(statement, entity);
    }

    @Override
    public void removeAll(List<PK> ids) {
        Assert.notNull(ids, "ids must not be null");
        String statement = baseStatement() + "removeAll";
        getSqlSession().delete(statement, ids);
    }

    @Override
    public int size() {
        String statement = baseStatement() + "count";
        return getSqlSession().selectOne(statement);
    }

    @Override
    public int size(Map<String, Object> params) {
        Assert.notEmpty(params, "params must not be null");
        String statement = baseStatement() + "count";
        return getSqlSession().selectOne(statement, params);
    }

    @Override
    public List<E> toList() {
        String statement = baseStatement() + "toList";
        return getSqlSession().selectOne(statement);
    }

    @Override
    public List<E> toList(E entity) {
        Assert.notNull(entity, "entity must not be null");
        String statement = baseStatement() + "toList";
        return getSqlSession().selectOne(statement, entity);
    }

    @Override
    public List<E> toList(String property, Object value) {
        Assert.notNull(property, "property must not be null");
        Assert.notNull(value, "value must not be null");

        String statement = baseStatement() + "toList";
        Map<String, Object> params = new HashMap<>();
        params.put("property", property);
        params.put("value", value);
        return getSqlSession().selectOne(statement, params);
    }

    @Override
    public List<E> toList(Map<String, Object> params) {
        Assert.notEmpty(params, "params must not be null");
        String statement = baseStatement() + "toList";
        return getSqlSession().selectOne(statement, params);
    }

    public SqlSessionTemplate getSqlSession() {
        return sqlSession;
    }

    public String baseStatement() {
        return baseStatement;
    }
}
