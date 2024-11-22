package com.lwz.controller;


import com.lwz.pojo.Category;
import com.lwz.pojo.Result;
import com.lwz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //添加分类
    @PostMapping()
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        //查询分类
        Category c = categoryService.findByName(category.getCategoryName());
        if (c != null){
            return Result.error("分类名称已存在");
        }
        categoryService.add(category);
        return Result.success();
    }

    //查询所有分类
    @GetMapping()
    public Result<List<Category>> list(){
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    //根据id查询分类
    @GetMapping("detail")
    public Result<Category> detail(Integer id){
        Category c =  categoryService.findById(id);
        return Result.success(c);
    }
    //修改分类
    @PutMapping()
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
         categoryService.update(category);
         return Result.success();
    }

    //根据id删除分类
    @DeleteMapping()
    public Result delete(Integer id){
        //查询分类id是否存在
        Category c = categoryService.findById(id);
        if (c == null){
            return Result.error("分类不存在");
        }
        categoryService.deleteById(id);
        return Result.success();
    }

}
