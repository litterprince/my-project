package com.xiaoze.api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LdapUser implements Serializable {
    private String email;

    private String name;

    private String department;
}
