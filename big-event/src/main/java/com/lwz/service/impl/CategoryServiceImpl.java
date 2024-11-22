package com.lwz.service.impl;

import com.lwz.mapper.CategoryMapper;
import com.lwz.pojo.Category;
import com.lwz.service.CategoryService;
import com.lwz.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    protected CategoryMapper categoryMapper;

    //添加分类
    @Override
    public void add(Category category) {
        //补充属性值
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }
    //查询所有分类
    @Override
    public List<Category> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }
    //根据id查询分类
    @Override
    public Category findById(Integer id) {
        Category c = categoryMapper.findById(id);
        return c;
    }
    //更新分类
    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }
    //根据分类名查询分类
    @Override
    public Category findByName(String categoryName) {
        Category c = categoryMapper.findByName(categoryName);
        return c;
    }
    //根据id删除分类
    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
