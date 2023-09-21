package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId")
    List<CartItem> findCartItemsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE  ci.product.id = :Id AND ci.cart.id = :userId")
    @Transactional
    int removeItem(@Param("Id") Long Id, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity WHERE  ci.product.id = :Id AND ci.cart.id = :userId")
    @Transactional
    int updateItem(@Param("quantity") int quantity,@Param("Id") Long Id, @Param("userId") Long userId);
}
