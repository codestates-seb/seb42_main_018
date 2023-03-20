package com.codestates.mainproject.group018.somojeon.record.service;

import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.repository.RecordRepository;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
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
    private final ScheduleService scheduleService;

    public RecordService(RecordRepository recordRepository, ScheduleService scheduleService) {
        this.recordRepository = recordRepository;
        this.scheduleService = scheduleService;
    }

    public Record createRecord(Record record) {
        scheduleService.findVerifiedSchedule(record.getSchedule().getScheduleId());

        return recordRepository.save(record);
    }

    public Record updateRecord(Record record) {
        Record findRecord = findVerifiedRecord(record.getRecordId());

        Optional.ofNullable(record.getFirstTeam())
                .ifPresent(findRecord::setFirstTeam);
        Optional.ofNullable(record.getFirstTeamScore())
                .ifPresent(findRecord::setFirstTeamScore);
        Optional.ofNullable(record.getSecondTeam())
                .ifPresent(findRecord::setSecondTeam);
        Optional.ofNullable(record.getSecondTeamScore())
                .ifPresent(findRecord::setSecondTeamScore);

        return recordRepository.save(findRecord);
    }

    public Record findRecord(long recordId) {
        Record findRecord = findVerifiedRecord(recordId);

        return findRecord;
    }

    public Page<Record> findRecords(int page, int size) {
        return recordRepository.findAll(PageRequest.of(page, size, Sort.by("recordId").ascending()));
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
