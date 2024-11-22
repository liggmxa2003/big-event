package com.lwz.mapper;

import com.lwz.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //添加分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) " +
            "values(#{categoryName},#{categoryAlias},#{createUser},NOW(),now())")
    void add(Category category);
    //查询所有
    @Select("select * from category where create_user=#{userId}")
    List<Category> list(Integer userId);
    //根据id查询分类
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);
    //更新分类
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
    void update(Category category);
    //根据分类名查询
    @Select("select category_name from category where category_name=#{categoryName}")
    Category findByName(String categoryName);
    //根据id删除分类
    @Delete("delete category from category where id=#{id}")
    void deleteById(Integer id);
}



