package com.perfectElegance.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isVerified;
    private boolean blocked;
    private String createProfileFor;
    private String name;
    private Long phoneNumber;
    private String gender;


    @Temporal(TemporalType.DATE)
    private Date DOB;

    private String relegion;
    private String nationality;

    private String email;
    private String password;
    private Integer height;
    private Integer weight;
    private String maritalStatus;
    private String skinTone;
    private String bodyType;
    private String physicaleStatus;
    private String education;
    private String profession;
    private String homeLocation;
    private String currentLocation;
    private String motherTongue;
    private String eatingHabits;
    private String financialStatus;
    private Long primaryNumber;
    private Long secondryNumber;
    private String preferredContactType;
    private String contactPersonAndRelationship;
    private String convenientTimeToCall;
    private String residentialStatus;

    private boolean isSubscribed = false;

    private Integer subscriptionId;

    private LocalDateTime subscriptionEndDate;

    private boolean isHidden = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }




    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user" ,cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Partner partner;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return !blocked;
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
                ", isVerified=" + isVerified +
                ", blocked=" + blocked +
                ", createProfileFor='" + createProfileFor + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", gender='" + gender + '\'' +
                ", DOB=" + DOB +
                ", relegion='" + relegion + '\'' +
                ", nationality='" + nationality + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", skinTone='" + skinTone + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", physicaleStatus='" + physicaleStatus + '\'' +
                ", education='" + education + '\'' +
                ", profession='" + profession + '\'' +
                ", homeLocation='" + homeLocation + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", motherTongue='" + motherTongue + '\'' +
                ", eatingHabits='" + eatingHabits + '\'' +
                ", financialStatus='" + financialStatus + '\'' +
                ", primaryNumber=" + primaryNumber +
                ", secondryNumber=" + secondryNumber +
                ", preferredContactType='" + preferredContactType + '\'' +
                ", contactPersonAndRelationship='" + contactPersonAndRelationship + '\'' +
                ", convenientTimeToCall='" + convenientTimeToCall + '\'' +
                ", residentialStatus='" + residentialStatus + '\'' +
                ", isSubscribed=" + isSubscribed +
                ", subscriptionId=" + subscriptionId +
                ", subscriptionEndDate=" + subscriptionEndDate +
                ", role=" + role +
                ", forgotPassword=" + forgotPassword +
                '}';
    }
}
