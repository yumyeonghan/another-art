package com.imagine.another_arts.web.login.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FindIdForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;
}
