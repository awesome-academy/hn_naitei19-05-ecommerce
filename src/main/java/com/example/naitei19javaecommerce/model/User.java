package com.example.naitei19javaecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Integer role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_detail", referencedColumnName = "id")
    private UserDetail userDetail;

    @Column(name = "is_active")
    private Byte isActive;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}