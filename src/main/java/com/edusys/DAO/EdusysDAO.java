/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import java.util.List;

/**
 *
 * @author Minh Huy
 */
abstract public  class EdusysDAO<T,M> {
    public abstract  void insert(T e);
    public abstract void update(T e);
    public abstract void delete(M key);
    public abstract List<T> selectAll();
    public abstract T selectByID(M key);
    protected abstract List<T> selectBySQL(String sql,Object...args);
}
