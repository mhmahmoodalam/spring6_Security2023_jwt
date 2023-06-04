package com.example.ss6.repository;

import com.example.ss6.entity.SecurityUser;
import com.example.ss6.model.enums.AccountState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SecurityUserRespository extends JpaRepository<SecurityUser,Long> {
    @Query("select distinct s from SecurityUser s where upper(s.username) = upper(?1)")
    Optional<SecurityUser> findOneByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM SecurityUser u WHERE upper(u.username) = upper(?1)")
    boolean existsByUsername(String username);

    @Modifying
    @Query("update SecurityUser su set su.accountState = ?2 where su.tenantId = ?1 ")
    void setAccountStateForAllMembers(long tenantId, AccountState state);

    Page<SecurityUser> findAllByTenantId(long tenantId, Pageable pageable);
}
