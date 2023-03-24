import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Tabmenu from '../../../components/TabMenu';
import { S_Button, S_TabButton } from '../../../components/UI/S_Button';
import S_Container from '../../../components/UI/S_Container';
import { S_Title } from '../../../components/UI/S_Text';
import { getFetch } from '../../../util/api';
import { Record, TeamList } from './CreateMatch';
import PastMatch from './_pastMatch';
import ScheduledMatch from './_scheduledMatch';

export interface Schedule {
  scheduleId: number;
  clubId: number;
  date: string;
  time: string;
  placeName: string;
  latitude: number;
  longitude: number;
  createdAt: string;
  records: Record[];
  teamList: TeamList[];
  candidates: string[];
}

interface SubTab {
  id: number;
  title: string;
  contents: React.ReactNode;
}

function ClubSchedule() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [tabIndex, setTabIndex] = useState(0);

  const [clubSchedules, setClubSchedules] = useState<Schedule[]>([]);

  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

  const subTabs: SubTab[] = [
    {
      id: 1,
      title: '예정 경기',
      contents: (
        // <ScheduledMatch schedule={clubSchedules.filter((el) => new Date(el.date) >= new Date())} />
        <ScheduledMatch schedule={clubSchedules} />
      )
    },
    {
      id: 2,
      title: '지난 경기',
      contents: (
        <PastMatch schedule={clubSchedules.filter((el) => new Date(el.date) < new Date())} />
      )
    }
  ];

  const onClickTap = (idx: number) => {
    setTabIndex(idx);
  };

  useEffect(() => {
    getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/schedules`).then((data) => {
      console.log(data);
      setClubSchedules([...data.data]);
    });
  }, []);
  return (
    <S_Container>
      <Tabmenu tabs={tabs}></Tabmenu>
      <div style={{ marginTop: '35px' }}>
        <S_Button
          onClick={() => {
            navigate(`/club/${id}/match/create`);
          }}
        >
          새 경기 또는 지난 경기 등록하기 +
        </S_Button>
      </div>
      <div>
        {subTabs.map((el, idx) => (
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
      {subTabs[tabIndex].contents}
    </S_Container>
  );
}

export default ClubSchedule;
