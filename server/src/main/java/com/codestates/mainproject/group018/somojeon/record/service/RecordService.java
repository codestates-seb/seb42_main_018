package com.codestates.mainproject.group018.somojeon.record.service;

import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.repository.RecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record updateRecord(Record record) {
        Record findRecord = findVerifiedRecord(record.getRecordId());

        Optional.ofNullable(record.getDate())
                .ifPresent(findRecord::setDate);
        Optional.ofNullable(record.getPlace())
                .ifPresent(findRecord::setPlace);

        return recordRepository.save(findRecord);
    }

    public Record findRecord(long recordId) {
        Record findRecord = findVerifiedRecord(recordId);

        return findRecord;
    }

    public Page<Record> findRecords(int page, int size) {
        return recordRepository.findAll(PageRequest.of(page, size, Sort.by("recordId").descending()));
    }

    public void deleteRecord(long recordId) {
        Record record = findRecord(recordId);

        recordRepository.delete(record);
    }

    public Record findVerifiedRecord(long recordId) {
        Optional<Record> optionalRecord =
                recordRepository.findById(recordId);
        Record findRecord = optionalRecord.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.RECORD_NOT_FOUND));

        return findRecord;
    }
}
