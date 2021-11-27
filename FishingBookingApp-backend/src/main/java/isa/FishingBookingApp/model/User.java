package isa.FishingBookingApp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="USERS")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "userGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @Column(name="user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_mail_address", unique = true, nullable = false)
    private String mailAddress;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name="user_name", nullable = false)
    private String name;

    @Column(name="user_surname", nullable = false)
    private String surname;

    @Column(name="user_mobile_number")
    private String mobileNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role")
    private UserRole role;

    private boolean enabled;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> authorities = new ArrayList<UserRole>();
        authorities.add(this.role);
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
