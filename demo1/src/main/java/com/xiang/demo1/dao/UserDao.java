package com.xiang.demo1.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {
    private final static List<UserDetails> APPLICATION_USERS = List.of(
            new User(
                    "xiangyin",
                    "111111",
                    Collections.singletonList(new SimpleGrantedAuthority("admin"))
            )
    );
    public UserDetails findUserByUsername(String username) {
        return APPLICATION_USERS
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("no such user!"));
    }
}
