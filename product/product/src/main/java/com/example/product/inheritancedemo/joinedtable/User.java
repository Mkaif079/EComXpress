package com.example.product.inheritancedemo.joinedtable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name="jt_user")

public class User {

    @Id
    private Long id;
    private String name;
    private String email;
    private String password;

}
