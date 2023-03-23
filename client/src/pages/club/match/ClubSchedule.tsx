import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { S_Button, S_TabButton } from '../../../components/UI/S_Button';
import S_Container from '../../../components/UI/S_Container';
import { S_Title } from '../../../components/UI/S_Text';
import { Record } from './CreateMatch';
import PastMatch from './_pastMatch';
import ScheduledMatch from './_scheduledMatch';

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

interface Tab {
  id: number;
  title: string;
  contents: React.ReactNode;
}

function ClubSchedule() {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const tabs: Tab[] = [
    { id: 1, title: '예정 경기', contents: <ScheduledMatch /> },
    { id: 2, title: '지난 경기', contents: <PastMatch /> }
  ];

  const onClickTap = (idx: number) => {
    setTabIndex(idx);
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
      <div>
        {tabs.map((el, idx) => (
          <S_TabButton
            key={el.id}
            onClick={() => onClickTap(idx)}
            className={tabIndex === idx ? 'clicked' : ''}
          >
            {el.title}
          </S_TabButton>
        ))}
      </div>
      <S_Title>경기 일정</S_Title>
      {tabs[tabIndex].contents}
    </S_Container>
  );
}

export default ClubSchedule;
