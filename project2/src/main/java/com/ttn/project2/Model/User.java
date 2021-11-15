package com.ttn.project2.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.ttn.project2.Annotations.ValidPassword;
import com.ttn.project2.Auditing.AuditModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Component
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)

//@PasswordMatches

public  class User extends AuditModel implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Email
    @NotNull
    private String email;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @ValidPassword
    @NotEmpty
    private String password;

    @ValidPassword
    @NotEmpty
    private String confirm_password;

    //@JsonIgnore
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "is_active")
    private boolean isActive;

    //@JsonIgnore
    @Column(name = "is_expired")
    private boolean isExpired;

    //@JsonIgnore
    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "invalid_attempt_count")
    private int invalidAttemptCount;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "access_token")
    private String accessToken;

    //@JsonView(Views.MyResponseViews.class)
    @OneToMany(targetEntity = UserAddress.class,cascade =CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName="id")
    private List<UserAddress> address;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id"))
    List<Role> role;


    public User(long id, String email,
                String firstName, String middleName,
                String lastName, String password,
                String confirm_password,
                boolean isDeleted, boolean isActive,
                boolean isExpired, boolean isLocked,
                String resetToken,
                String accessToken,
                int invalidAttemptCount,
                List<UserAddress>address, List<Role> role) {
       //super();
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.confirm_password = confirm_password;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.resetToken = resetToken;
        this.accessToken=accessToken;
        this.invalidAttemptCount = invalidAttemptCount;
        this.address = address;
        this.role = role;
    }


    public User(String email, String firstName, String lastName, String password,String confirm_password) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        HashSet<SimpleGrantedAuthority> set =new HashSet<>();
//
//        set.add(new SimpleGrantedAuthority(String.valueOf(this.getRole())));
//
//        return set;
//    }

    @Override
    public  String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", confirm_password='" + confirm_password + '\'' +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                ", isExpired=" + isExpired +
                ", isLocked=" + isLocked +
                ", invalidAttemptCount=" + invalidAttemptCount +
                ", resetToken='" + resetToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", address=" + address +
                ", role=" + role +
                '}';
    }
}