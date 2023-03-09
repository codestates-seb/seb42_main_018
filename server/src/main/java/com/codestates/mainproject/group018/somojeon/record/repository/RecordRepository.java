package com.codestates.mainproject.group018.somojeon.record.repository;

import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
