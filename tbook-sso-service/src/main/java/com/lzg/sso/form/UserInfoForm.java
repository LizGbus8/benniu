package com.lzg.sso.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 作者：LizG on 2018/9/30 12:55
 * 描述：
 */
@Data
public class UserInfoForm {
    @NotNull
    private String nickname;
    @NotNull
    private Integer sex;
    @NotNull
    private Integer[] interest;
    @NotNull
    private String contact_type;
    @NotNull
    private String contact_value;
    @NotNull
    private String schoolname;

}
