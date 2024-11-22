package com.lwz.service;

import com.lwz.pojo.Category;

import java.util.List;

public interface CategoryService {

    //添加分类
    void add(Category category);
    //查询所有分类
    List<Category> list();
    //根据id查询分类
    Category findById(Integer id);
    //修改分类
    void update(Category category);
    //根据分类名查询分类
    Category findByName(String categoryName);
    //根据id删除分类
    void deleteById(Integer id);
}
