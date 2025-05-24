package com.investocks.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    @NotBlank(message="UserName is required")
    @Size(min=5,message="Minimum 5 characters required")
    private String userName;
    @Email(message="Invalid Email")
    @NotBlank(message="Email is required")
    private String email;
    @NotBlank(message="Password is required")
    @Size(min=8,message="Atleast 8 characters required")
    private String password;
    @NotBlank(message="Country is required")
    private String country;
    private String firstName;
    private String lastName;
    @NotBlank(message="Phone Number is required")
    @Size(min=10,max=10,message="Enter valid phone number")
    private String phoneNumber;
}
