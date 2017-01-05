package org.handler.property.redis.persistence.dao;

import org.handler.property.redis.persistence.entity.TabDeveloper;

public interface TabDeveloperDao {
    int deleteByPrimaryKey(String id);

    int insert(TabDeveloper record);

    int insertSelective(TabDeveloper record);

    TabDeveloper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TabDeveloper record);

    int updateByPrimaryKey(TabDeveloper record);
}