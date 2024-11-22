package com.lwz.mapper;

import com.lwz.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //添加文章列表
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},NOW(),now())")
    void add(Article article);
    //根据文章标题查询文章
    @Select("select * from article where title=#{title}")
    Article findByTitle(String title);
    //分页列表查询
    List<Article> list(Integer userId, String categoryId, String state);
    //根据文章id查询文章
    Article findById(Integer id);
    //更新文章
    void update(Article article);
    //根据id删除文章
    void delete(Integer id);
}
