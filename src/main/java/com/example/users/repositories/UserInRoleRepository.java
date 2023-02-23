package com.example.users.repositories;

import com.example.users.entities.UserInRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInRoleRepository  extends JpaRepository<UserInRole, Integer> {
}
