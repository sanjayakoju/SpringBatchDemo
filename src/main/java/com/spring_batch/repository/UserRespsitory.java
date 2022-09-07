package com.spring_batch.repository;

import com.spring_batch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespsitory extends JpaRepository<User,Long> {
}
