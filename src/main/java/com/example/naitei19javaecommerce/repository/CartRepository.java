package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
