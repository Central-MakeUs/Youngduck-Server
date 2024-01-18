package com.example.domains.quit.repository;

import com.example.domains.quit.domain.Quit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuitRepository extends JpaRepository<Quit, Long> {}
