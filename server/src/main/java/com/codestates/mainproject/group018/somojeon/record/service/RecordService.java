package com.codestates.mainproject.group018.somojeon.record.service;

import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.repository.RecordRepository;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final ScheduleService scheduleService;
    private final ClubService clubService;

    public Record createRecord(Record record, long clubId, long scheduleId) {
        clubService.findVerifiedClub(clubId);
        scheduleService.findVerifiedSchedule(scheduleId);

//        UserClub userClub = new UserClub();
//        Team team = new Team();
//        userClub.setPlayCount(userClub.getPlayCount() + 1);
//        switch (team.getWinLoseDraw()) {
//            case "win":
//                userClub.setWinCount(userClub.getWinCount() + 1);
//                break;
//            case "lose":
//                userClub.setLoseCount(userClub.getLoseCount() + 1);
//                break;
//            case "draw":
//                userClub.setDrawCount(userClub.getDrawCount() + 1);
//                break;
//        }
//        userClub.setWinRate(userClub.getWinCount() / userClub.getPlayCount() * 100);

        return recordRepository.save(record);
    }

    public Record updateRecord(Record record) {
        Record findRecord = findVerifiedRecord(record.getRecordId());

        Optional.ofNullable(record.getFirstTeamNumber())
                .ifPresent(findRecord::setFirstTeamNumber);
        Optional.ofNullable(record.getFirstTeamScore())
                .ifPresent(findRecord::setFirstTeamScore);
        Optional.ofNullable(record.getSecondTeamNumber())
                .ifPresent(findRecord::setSecondTeamNumber);
        Optional.ofNullable(record.getSecondTeamScore())
                .ifPresent(findRecord::setSecondTeamScore);

        return recordRepository.save(findRecord);
    }

    public Record findRecord(long recordId) {
        Record findRecord = findVerifiedRecord(recordId);

        return findRecord;
    }

    public Page<Record> findRecords(long clubId, long scheduleId, int page, int size) {
        clubService.findVerifiedClub(clubId);
        scheduleService.findVerifiedSchedule(scheduleId);
        return recordRepository.findAll(PageRequest.of(page, size, Sort.by("recordId")));
    }

    public void deleteRecord(long recordId, long clubId, long scheduleId) {
        clubService.findVerifiedClub(clubId);
        scheduleService.findVerifiedSchedule(scheduleId);
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
