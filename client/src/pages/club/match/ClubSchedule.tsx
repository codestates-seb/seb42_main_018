import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import ScheduleCard from '../../../components/match/ScheduleCard';
import { S_Button, S_TabButton } from '../../../components/UI/S_Button';
import S_Container from '../../../components/UI/S_Container';
import { S_Description, S_Label, S_Text, S_Title } from '../../../components/UI/S_Text';
import { Record } from './CreateMatch';

const S_MatchSchedule = styled.div``;
const S_List = styled.div``;

const S_TapMenu = styled.div``;

export interface Schedule {
  scheduleId: number;
  clubId: number;
  date: string;
  placeName: string;
  latitude: number;
  logitude: number;
  createdAt: string;
  records: Record[];
  candidates: string[];
}

function ClubSchedule() {
  const navigate = useNavigate();
  const [clickedTab, setClickedTab] = useState<string | null>('scheduled');

  const tabHandler = (e: React.MouseEvent<HTMLElement>) => {
    setClickedTab(e.currentTarget.getAttribute('name'));
  };
  return (
    <S_Container>
      <div>
        <S_Button
          onClick={() => {
            navigate('/club/:id/match/create');
          }}
        >
          새 경기 또는 지난 경기 등록하기 +
        </S_Button>
      </div>
      <S_TapMenu>
        <S_TabButton name='scheduled' onClick={tabHandler} className={clickedTab === "scheduled" ? "clicked" : ""}>
          예정 경기
        </S_TabButton>
        <S_TabButton name='past' onClick={tabHandler} className={clickedTab === "past" ? "clicked" : ""}>
          지난 경기
        </S_TabButton>
      </S_TapMenu>
      <S_MatchSchedule>
        <S_Title>경기 일정</S_Title>
        <S_Text>경기 시작 전까지 참석하기 버튼을 누를 수 있어요.</S_Text>
        <S_List>
          <ScheduleCard />
        </S_List>
      </S_MatchSchedule>
    </S_Container>
  );
}

export default ClubSchedule;
