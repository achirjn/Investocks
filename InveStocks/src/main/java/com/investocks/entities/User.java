package com.investocks.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name="user_info")
@ToString
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="username",nullable=false)
    private String userName;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    private String country;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="account_balance")
    private int accountBalance=1000000;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Providers provider = Providers.Self;

    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    private List<Bid> bid;
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    private List<Ask> ask;
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    private List<ShareBalance> shareBalance;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public User() {
    }

    public User(String userName, String email, String password, String country, String firstName, String lastName,
            int accountBalance, String phoneNumber, Providers provider) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
    }
    @Override
    public String getUsername() {
        return this.email; //since we using email for identification of the user logging in
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public int getId() {
        return id;
    }
}
