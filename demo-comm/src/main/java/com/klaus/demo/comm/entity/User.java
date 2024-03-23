package com.klaus.demo.comm.entity;


import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Klaus
 */


@Data
@RequiredArgsConstructor
public class User {

    private Long id;

    private String userCode;

    private String username;

    private String password;

    private int age;

}
