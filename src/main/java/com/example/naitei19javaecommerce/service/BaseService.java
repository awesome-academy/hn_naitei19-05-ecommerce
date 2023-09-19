package com.example.naitei19javaecommerce.service;

import java.util.List;

public interface BaseService <PK, T>{
    List<T> findAll();
    T findById(PK id);
}
