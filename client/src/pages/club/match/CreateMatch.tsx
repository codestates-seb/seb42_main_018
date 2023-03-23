import { useEffect, useState } from 'react';
import styled from 'styled-components';
import KakaoMapSearch, { PlaceType } from '../../../components/kakao/KakaoMapSearch';
import KakaoMapView from '../../../components/kakao/KakaoMapView';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import {
  S_Button,
  S_ButtonGray,
  S_EditButton,
  S_SelectButton
} from '../../../components/UI/S_Button';
import { S_Description, S_Label, S_Title } from '../../../components/UI/S_Text';
import { S_NameTag } from '../../../components/UI/S_Tag';
import AddMemberPopUp from '../../../components/match/AddMemberPopUp';

import { useForm } from 'react-hook-form';
import RecordCard from '../../../components/match/RecordCard';
import TeamCard from '../../../components/match/TeamCard';

export const S_MapBackdrop = styled.div`
  background-color: rgba(0, 0, 0, 0.3);
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
  overflow: hidden;
`;

export const S_MapView = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 300px;
  height: 400px;
  border-radius: 20px;
  padding: 20px;
  section {
    width: 260px;
    border: 1px solid black;
    border-radius: 20px;
    padding: 10px;
  }
`;

export interface TeamList {
  id: number;
  members: string[];
}

export interface Record {
  id: number;
  firstTeam: string;
  secondTeam: string;
  firstTeamScore: number;
  secondTeamScore: number;
}

export interface MatchData {
  date: string | undefined;
  time: string | undefined;
  placeName: string | undefined;
  longitude: number | undefined;
  latitude: number | undefined;
  candidates: string[];
  teamList: TeamList[];
  records: Record[];
}

function CreateMatch() {
  const {
    register,
    unregister,
    // formState: { errors },
    getValues
  } = useForm({ mode: 'onChange' });

  const [matchData, setMatchData] = useState<MatchData>();

  const [date, setDate] = useState<string>(new Date().toISOString().slice(0, 10));
  const [time, setTime] = useState<string | undefined>();
  const [placeValue, setPlaceValue] = useState<PlaceType>();
  //참가를 누른 멤버들
  const candidates: string[] = [
    '박대운',
    '우제훈',
    '김은택',
    '김아애',
    '문채리',
    '전규언전규언전규',
    '박대운2',
    '우제훈2',
    '김은택2',
    '김아애2',
    '문채리2',
    '전규언전'
  ];

  //팀구성에 필요한 후보들(팀에 들어가거나 빠질 때 실시간 반영되는 리스트)
  const [candidateList, setCandidateList] = useState(candidates);

  const [teamList, setTeamList] = useState<TeamList[]>([{ id: 0, members: [] }]);
  const [records, setRecords] = useState<Record[]>([]);

  const [isOpenMapSetting, setIsOpenMapSetting] = useState(false);
  const [isOpenMapView, setIsOpenMapView] = useState(false);

  const [isOpenAddMember, setIsOpenAddMember] = useState(false);
  const [addButtonIndex, setAddButtonIndex] = useState(0);
  const [addButtonPos, setAddButtonPos] = useState({ x: 0, y: 0 });

  const setRequestData = (
    date: string | undefined,
    time: string | undefined,
    place: PlaceType | undefined,
    candidates: string[],
    teams: TeamList[],
    records: Record[]
  ) => {
    const data = {
      date,
      time,
      placeName: place?.place_name,
      longitude: place?.y,
      latitude: place?.x,
      candidates: candidates?.length !== 0 ? candidates : [],
      teamList: !(teams?.length === 1 && teams[0].members.length === 0) ? teams : [],
      records: records.length !== 0 ? records : []
    };
    console.log(data);
    setMatchData(data);
  };

  const dateChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDate(e.target.value);
  };

  const timeChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTime(e.target.value);
  };

  const mapSettingModalHandler = () => {
    setIsOpenMapSetting(!isOpenMapSetting);
  };

  const mapViewModalHandler = () => {
    if (!placeValue) return;
    setIsOpenMapView(!isOpenMapView);
  };

  const openAddMemberHandler = () => {
    setIsOpenAddMember(!isOpenAddMember);
  };

  const checkValidation = () => {
    if (!placeValue || !time) return false;
    else return true;
  };

  const deleteNameTagFromTeam = (idx: number, memberIdx: number) => {
    const copied = [...teamList];
    const deletedMember = copied[idx].members.splice(memberIdx, 1);
    setCandidateList([...candidateList, deletedMember[0]]);
    setTeamList(copied);
  };

  const openMemberListPopup = (idx: number, e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    if (!candidateList.length) {
      return;
    }
    if (addButtonIndex !== idx) {
      setAddButtonIndex(idx);
      // setIsOpenAddMember(false);
      setIsOpenAddMember(true);
    } else {
      setAddButtonIndex(idx);
      openAddMemberHandler();
    }
    setAddButtonPos({ x: e.nativeEvent.pageX, y: e.nativeEvent.pageY });
  };
  const addTeam = () => {
    const newTeam = {
      id: teamList[teamList.length - 1].id + 1,
      members: []
    };
    setTeamList([...teamList, newTeam]);
  };

  const deleteTeam = (idx: number) => {
    if (teamList.length === 1) {
      setCandidateList(candidates);
      setTeamList([
        {
          id: 0,
          members: []
        }
      ]);
      return;
    } else {
      const deleted = [...teamList];
      const deletedTeam = deleted.splice(idx, 1);
      setCandidateList([...candidateList, ...deletedTeam[0].members]);
      setTeamList(deleted);
      setIsOpenAddMember(false);
    }
  };

  const addRecord = () => {
    const newRecord: Record = {
      id: records.length ? records[records.length - 1].id + 1 : 0,
      firstTeam: '1',
      secondTeam: '1',
      firstTeamScore: 0,
      secondTeamScore: 0
    };
    setRecords([...records, newRecord]);
  };

  const deleteRecord = (idx: number, record: Record) => {
    const deleted = [...records];
    deleted.splice(idx, 1);
    setRecords(deleted);
    unregister(`${record.id}`);
  };

  const updateRecord = () => {
    const copiedRecords: Record[] = [];
    const copiedValues = Object.entries(getValues());
    copiedValues.forEach((el) => {
      const temp = {
        id: Number(el[0]),
        ...el[1]
      };
      copiedRecords.push(temp);
    });
    setRecords(copiedRecords);
  };

  const saveMatchData = () => {
    if (!checkValidation()) {
      alert('*가 표시된 항목은 필수 입력란입니다.');
      return;
    }
    updateRecord();
  };

  if (!candidateList.length && isOpenAddMember) {
    setIsOpenAddMember(false);
  }

  useEffect(() => {
    setRequestData(date, time, placeValue, candidates, teamList, records);
  }, [records]);

  return (
    <S_Container onClick={() => setIsOpenAddMember(false)}>
      <S_Title>경기 등록</S_Title>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>날짜/시간 선택 *</S_Label>
        <S_Input type='date' value={date} onChange={dateChangeHandler} />
        <S_Input type='time' value={time || ''} onChange={timeChangeHandler} />
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>장소 *</S_Label>
        <S_Input type='text' value={placeValue?.place_name || ''} readOnly />
        <S_SelectButton onClick={mapSettingModalHandler} style={{ width: 'auto' }}>
          지도설정
        </S_SelectButton>
        <S_SelectButton onClick={mapViewModalHandler} style={{ width: 'auto' }}>
          지도보기
        </S_SelectButton>
        {isOpenMapSetting && (
          <S_MapBackdrop onClick={mapSettingModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapSearch
                mapSettingModalHandler={mapSettingModalHandler}
                setPlaceValue={setPlaceValue}
                placeValue={placeValue}
              />
            </S_MapView>
          </S_MapBackdrop>
        )}
        {isOpenMapView && (
          <S_MapBackdrop onClick={mapViewModalHandler}>
            <S_MapView style={{ height: '300px' }} onClick={(e) => e.stopPropagation()}>
              {placeValue && <KakaoMapView y={placeValue.y} x={placeValue.x} />}
            </S_MapView>
          </S_MapBackdrop>
        )}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>참석자</S_Label>
        <S_Description>
          경기를 등록하면 경기정보 페이지에서 참석/불참을 선택할 수 있어요.
        </S_Description>
        <S_Description>
          참석을 선택한 멤버는 자동으로 등록됩니다.
          <S_EditButton style={{ padding: '0 7px', float: 'right' }}>추가</S_EditButton>
        </S_Description>

        <div>
          {candidates &&
            candidates.map((member, idx) => {
              return <S_NameTag key={idx}>{member}</S_NameTag>;
            })}
        </div>
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>팀구성</S_Label>
        {teamList &&
          teamList.map((team, idx) => {
            return (
              <div key={team.id}>
                <TeamCard
                  teamList={teamList}
                  team={team}
                  idx={idx}
                  deleteNameTagFromTeam={deleteNameTagFromTeam}
                  openMemberListPopup={openMemberListPopup}
                  deleteTeam={deleteTeam}
                />
              </div>
            );
          })}
        {isOpenAddMember && (
          <AddMemberPopUp
            top={addButtonPos.y}
            left={addButtonPos.x}
            candidateList={candidateList}
            setCandidateList={setCandidateList}
            idx={addButtonIndex}
            setTeamList={setTeamList}
            teamList={teamList}
            setIsOpenAddMember={setIsOpenAddMember}
          />
        )}
        <S_ButtonGray
          onClick={() => {
            addTeam();
          }}
        >
          팀 구성 목록 추가 +
        </S_ButtonGray>
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>전적</S_Label>
        <S_Description>경기가 종료된 뒤 결과를 입력해보세요.</S_Description>
        {records &&
          records.map((record, idx) => {
            return (
              <div key={record.id}>
                <RecordCard
                  record={record}
                  idx={idx}
                  teamList={teamList}
                  deleteRecord={deleteRecord}
                  register={register}
                />
              </div>
            );
          })}
        <S_ButtonGray
          style={{ marginTop: '10px' }}
          onClick={() => {
            addRecord();
          }}
        >
          전적 목록 추가+
        </S_ButtonGray>
      </div>
      <div>
        <S_Button
          onClick={() => {
            saveMatchData();
          }}
        >
          저장하기 +
        </S_Button>
      </div>
    </S_Container>
  );
}

export default CreateMatch;
