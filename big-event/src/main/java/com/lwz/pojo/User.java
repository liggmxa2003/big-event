package com.lwz.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore//忽略password字段
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{5,10}$",message = "昵称要求5-10位字符")
    private String nickname;//昵称
    @Email
    @NotEmpty
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
