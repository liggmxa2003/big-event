package com.lwz.service;

import com.lwz.pojo.Article;
import com.lwz.pojo.PageBean;

public interface ArticleService {
    //添加文章列表
    void add(Article article);
    //根据标题查询文章
    Article findByTitle(String title);
    //条件列表分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);
    //根据id查询文章
    Article findById(Integer id);
    //修改文章
    void update(Article article);
    //根据id删除文章
    void deleteById(Integer id);
}
