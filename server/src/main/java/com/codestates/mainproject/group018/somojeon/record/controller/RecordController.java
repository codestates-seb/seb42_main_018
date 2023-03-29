package com.codestates.mainproject.group018.somojeon.record.controller;

import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.mapper.RecordMapper;
import com.codestates.mainproject.group018.somojeon.record.service.RecordService;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final RecordMapper recordMapper;
    private final UserMapper userMapper;

    @PostMapping("/clubs/{club-id}/schedules/{schedule-id}/records")
    public ResponseEntity postRecord(@PathVariable("club-id") @Positive long clubId,
                                     @PathVariable("schedule-id") @Positive long scheduleId,
                                     @Valid @RequestBody RecordDto.Post requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);

        Record record = recordMapper.recordPostDtoToRecord(requestBody);

        Record createdRecord = recordService.createRecord(record, clubId, scheduleId);
        URI location = UriCreator.createUri("/records", createdRecord.getRecordId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/clubs/{club-id}/schedules/{schedule-id}/records/{record-id}")
    public ResponseEntity patchRecord(@PathVariable("club-id") @Positive long clubId,
                                      @PathVariable("schedule-id") @Positive long scheduleId,
                                      @PathVariable("record-id") @Positive long recordId,
                                      @Valid @RequestBody RecordDto.Patch requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);
        requestBody.addRecordId(recordId);

        Record record = recordService.updateRecord(recordMapper.recordPatchDtoToRecord(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(recordMapper.recordToRecordResponseDto(record, userMapper)), HttpStatus.OK);
    }

    @GetMapping("/clubs/{club-id}/schedules/{schedule-id}/records")
    public ResponseEntity getRecords(@PathVariable("club-id") @Positive long clubId,
                                               @PathVariable("schedule-id") @Positive long scheduleId,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        Page<Record> recordPage = recordService.findRecords(clubId, scheduleId, page - 1, size);
        List<Record> records = recordPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(recordMapper.recordsToRecordResponseDtos(records), recordPage),
                HttpStatus.OK);
    }

    @DeleteMapping("/clubs/{club-id}/schedules/{schedule-id}/records/{record-id}")
    public ResponseEntity deleteRecord(@PathVariable("club-id") @Positive long clubId,
                                       @PathVariable("schedule-id") @Positive long scheduleId,
                                       @PathVariable("record-id") @Positive long recordId) {
        recordService.deleteRecord(recordId, clubId, scheduleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
