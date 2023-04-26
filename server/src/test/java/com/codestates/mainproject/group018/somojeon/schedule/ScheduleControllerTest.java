package com.codestates.mainproject.group018.somojeon.schedule;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.config.SecurityConfiguration;
import com.codestates.mainproject.group018.somojeon.schedule.controller.ScheduleController;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.mapper.ScheduleMapper;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfiguration.class, JwtTokenizer.class, CustomAuthorityUtils.class})
@WebMvcTest(ScheduleController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private ScheduleMapper scheduleMapper;

    @Autowired
    private Gson gson;

    @Test
    public void postScheduleTest() throws Exception {
        Long clubId = 1L;
        ScheduleDto.Put post = new ScheduleDto.Put(
                1L, clubId, "2023-04-27", "14:25:00", "상봉 배드민턴장",
                123.45678, 68.345678, List.of(), List.of(), List.of());
        String content = gson.toJson(post);

        given(scheduleMapper.schedulePutDtoToSchedule(Mockito.any(ScheduleDto.Put.class), Mockito.any(UserService.class),
                Mockito.any(ClubService.class))).willReturn(new Schedule());

        Schedule mockResultSchedule = new Schedule();
        mockResultSchedule.setScheduleId(1L);

        given(scheduleService.createSchedule(Mockito.any(Schedule.class), clubId)).willReturn(mockResultSchedule);

        ResultActions actions =
                mockMvc.perform(
                        post("/clubs/{club-id}/schedules", clubId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.scheduleId").value(post.getScheduleId()))
                .andExpect(jsonPath("$.data.clubId").value(post.getClubId()))
                .andExpect(jsonPath("$.data.date").value(post.getDate()))
                .andExpect(jsonPath("$.data.time").value(post.getTime()))
                .andExpect(jsonPath("$.data.placeName").value(post.getPlaceName()))
                .andExpect(jsonPath("$.data.longitude").value(post.getLongitude()))
                .andExpect(jsonPath("$.data.latitude").value(post.getLatitude()))
                .andExpect(jsonPath("$.data.records").value(post.getRecords()))
                .andExpect(jsonPath("$.data.candidates").value(post.getCandidates()))
                .andExpect(jsonPath("$.data.teamList").value(post.getTeamList()));
    }
}
