package com.example.ss6.entity;

import com.example.ss6.model.enums.AccountState;
import com.example.ss6.model.enums.CloudRole;
import com.example.ss6.model.enums.UserType;
import com.google.common.base.Strings;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "security_user")
@Data
public class SecurityUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;


    @Column(length = 200, unique = true)
    private String username;
    private String password;



    @Column(name = "tenant_id")
    private int tenantId;

    @ElementCollection(targetClass = CloudRole.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "security_role",
            joinColumns = {@JoinColumn(name="security_user_id")}
    )
    @Column(name = "role", nullable = false)
    private Set<CloudRole> roles = new HashSet<>();

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType usertype;


    @Column(name = "account_state")
    @Enumerated(EnumType.STRING)
    private AccountState accountState;


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.roles.stream()
                .map(CloudRole::getAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    @Transient
    public String getPassword() {
        return null;
    }

    @Override
    @Transient
    public String getUsername() {
        return null;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return AccountState.ACTIVE.equals(accountState);
    }

    @Transient
    public void setEnabled(boolean enabled) {
        accountState = enabled ? AccountState.ACTIVE : AccountState.DISABLED;
    }

    @Transient
    public boolean isStoreManager() {
        return getRoles().stream().anyMatch(role -> role.equals(CloudRole.STORE_MANAGER));
    }

    @Transient
    public void addRole(CloudRole role) {
        this.roles.add(role);
    }

    @Transient
    public void removeRole(CloudRole role) {
        this.roles.remove(role);
    }

    @Transient
    public boolean hasEmail() {
        return !Strings.isNullOrEmpty(email);
    }

    @Nonnull
    @Transient
    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this, getPassword(), getAuthorities());
    }
}
