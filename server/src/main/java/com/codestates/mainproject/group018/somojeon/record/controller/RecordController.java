package com.codestates.mainproject.group018.somojeon.record.controller;

import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.mapper.RecordMapper;
import com.codestates.mainproject.group018.somojeon.record.service.RecordService;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
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
@RequestMapping("/records")
public class RecordController {
    private final RecordService recordService;
    private final RecordMapper recordMapper;

    public RecordController(RecordService recordService, RecordMapper recordMapper) {
        this.recordService = recordService;
        this.recordMapper = recordMapper;
    }

//    @PostMapping
//    public ResponseEntity postRecord(@Valid @RequestBody RecordDto.Post requestBody) {
//        Record record = recordMapper.recordPostDtoToRecord(requestBody);
//
//        Record createdRecord = recordService.createRecord(record);
//        URI location = UriCreator.createUri("/records", createdRecord.getRecordId());
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @PatchMapping("/{record-id}")
//    public ResponseEntity patchRecord(@PathVariable("record-id") @Positive long recordId,
//                                      @Valid @RequestBody RecordDto.Patch requestBody) {
//        requestBody.addRecordId(recordId);
//
//        Record record = recordService.updateRecord(recordMapper.recordPatchDtoToRecord(requestBody));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(recordMapper.recordToRecordResponseDto(record)), HttpStatus.OK);
//    }

    @GetMapping("/{record-id}")
    public ResponseEntity getRecord(@PathVariable("record-id") @Positive long recordId) {
        Record record = recordService.findRecord(recordId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(recordMapper.recordToRecordResponseDto(record)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getRecords(@RequestParam("page") int page,
                                     @RequestParam("size") int size) {
        Page<Record> pageRecords = recordService.findRecords(page - 1, size);
        List<Record> records = pageRecords.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(recordMapper.recordsToRecordResponseDtos(records), pageRecords), HttpStatus.OK);
    }

    @DeleteMapping("/{record-id}")
    public ResponseEntity deleteRecord(@PathVariable("record-id") @Positive long recordId) {
        recordService.deleteRecord(recordId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
