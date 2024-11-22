package com.lwz.controller;

import com.lwz.pojo.Article;
import com.lwz.pojo.PageBean;
import com.lwz.pojo.Result;
import com.lwz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//文章接口
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //添加文章列表
    @PostMapping()
    public Result add(@RequestBody @Validated Article article){
        //判断文章标题是否重复
        Article a = articleService.findByTitle(article.getTitle());
        if (a != null){
            return Result.error("文章标题重复");
        }
        /*if (articleService.findByTitle(article.getTitle())!=null){
            return Result.error("文章标题重复");
        }*/
        articleService.add(article);
        return Result.success();
    }

    //文章条件分页列表查询
    @GetMapping()
     public Result<PageBean<Article>> list(
             Integer pageNum,//当前页码
             Integer pageSize,//每页条数
             @RequestParam (required = false) String categoryId,//文章分类id
             @RequestParam (required = false) String state//发布状态
     ){
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
     }

     //获取文章详情
    @GetMapping("detail")
    public Result<Article> detail(@Validated Integer id){
        //检验文章是否存在
        Article a = articleService.findById(id);
        if (a == null){
            return Result.error("文章不存在");
        }
        return Result.success(a);
    }

    //更新文章
    @PutMapping()
    public Result<Article> update(@RequestBody @Validated Article article){
        //判断文章是否存在
        Article a = articleService.findById(article.getId());
        if (a == null){
            return Result.error("文章不存在");
        }
        //判断文章标题是否重复
        Article b = articleService.findByTitle(article.getTitle());
        if (b != null){
            return Result.error("文章标题重复");
        }
        articleService.update(article);
        return Result.success();
    }

    //删除文章
    @DeleteMapping()
    public Result delete(Integer id){
        Article a = articleService.findById(id);
        if (a == null){
            return Result.error("删除文章不存在");
        }
        articleService.deleteById(id);
        return Result.success();
    }
}
