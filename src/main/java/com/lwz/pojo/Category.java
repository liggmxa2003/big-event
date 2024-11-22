package com.lwz.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty(message = "类别名称不能为空")
    @Pattern(regexp = "^\\S{2,10}$",message = "类别名称要求1-10位字符")
    private String categoryName;//分类名称
    @NotEmpty(message = "类别别名不能为空")
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //如果某个校验没有指定分组,默认属于Default分组
    //分组之间可以继承,A extends(继承) B,所以A中的校验规则也会被B继承
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
