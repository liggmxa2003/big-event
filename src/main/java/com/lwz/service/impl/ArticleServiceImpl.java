package com.lwz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwz.mapper.ArticleMapper;
import com.lwz.pojo.Article;
import com.lwz.pojo.PageBean;
import com.lwz.service.ArticleService;
import com.lwz.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充属性值
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);
    }
    // 根据标题查询文章
    @Override
    public Article findByTitle(String title) {
        Article a = articleMapper.findByTitle(title);
        return a;
    }
    // 分页查询
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        //1.创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper方法查询
        Map <String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId,categoryId,state);
        //Page中提供了方法，可获得PageHelper分页查询后,获得的总记录数和当前页数据.
        Page<Article> pa = (Page<Article>) as;

        //把当前数据填充到PageBean对象中
        pb.setTotal(pa.getTotal());
        pb.setItems(pa.getResult());
        return pb;
    }
    //根据id查询文章
    @Override
    public Article findById(Integer id) {
        Article a = articleMapper.findById(id);
        return a;
    }
    //修改文章
    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }
    //根据id删除文章
    @Override
    public void deleteById(Integer id) {
        articleMapper.delete(id);
    }
}
