package com.investocks.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.investocks.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

    public Optional<User> findByUserNameAndPassword(String userName , String password);

    public Optional<User> findByUserName(String userName);

    public Optional<User> findByEmail(String email);

    @Modifying
    @Query(value="update user_info set account_balance = account_balance + ?2 where id = ?1;", nativeQuery=true)
    public void updateUserBalance(int userId, int amount);
    
}
