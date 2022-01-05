package com.bijgepast.locationhunter.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.bijgepast.locationhunter.database.entities.BaseEntity;


public interface BaseDao<TEntity extends BaseEntity> {
    @Insert
    void insert(TEntity entity);

    @Insert
    void insertAll(TEntity... entities);

    @Update
    void update(TEntity entity);

    @Delete
    void delete(TEntity entity);
}
