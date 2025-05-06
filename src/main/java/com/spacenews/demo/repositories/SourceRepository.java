package com.spacenews.demo.repositories;

import com.spacenews.demo.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, Long> {
    Source findByUrl(String url);
}
