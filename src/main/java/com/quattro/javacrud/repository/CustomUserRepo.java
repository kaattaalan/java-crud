package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.models.UserInfo;

import java.util.List;

public interface CustomUserRepo {

    public UserInfo getUserInfoOnly(String id);

}
