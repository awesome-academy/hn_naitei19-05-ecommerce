package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Cart;
import com.example.naitei19javaecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findCartByUserId(@Param("userId") Long userId);

}
