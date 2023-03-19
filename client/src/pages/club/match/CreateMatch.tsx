import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import KakaoMapSearch, { PlaceType } from '../../../components/kakao/KakaoMapSearch';
import KakaoMapView from '../../../components/kakao/KakaoMapView';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import {
  S_Button,
  S_ButtonGray,
  S_EditButton,
  S_SelectButton,
  S_NegativeButton
} from '../../../components/UI/S_Button';
import { S_Description, S_Label, S_Text, S_Title } from '../../../components/UI/S_Text';
import { S_Tag, S_GridTag } from '../../../components/UI/S_Tag';
import AddMemberPopUp from '../../../components/match/AddMemberPopUp';

import { useForm } from "react-hook-form";

const S_MapBackdrop = styled.div`
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
`;

const S_MapView = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 300px;
  height: 300px;
  border-radius: 20px;
  justify-content: center;
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

export interface Datas {
  schedule: {
      date: string | undefined;
      time: string | undefined;
      placeName: string | undefined;
      placeCoordinate: {
          longitude: number | undefined;
          latitude: number | undefined;
      };
  };
  canidates: string[];
  teamList: TeamList[];
  records: Record[];
}

function CreateMatch() {
  const {
    register,
    unregister,
    // formState: { errors },
    getValues,
} = useForm({ mode: "onChange" });

  const [datas, setDatas] = useState<Datas>();

  const [date, setDate] = useState<string>();
  const [time, setTime] = useState<string>();
  const [placeValue, setPlaceValue] = useState<PlaceType>();
  //참가를 누른 멤버들
  const candidates: string[] = ['박대운', '우제훈', '김은택', '김아애', '문채리', '전규언전'];
  
  //팀구성에 필요한 후보들(팀에 들어가거나 빠질 때 실시간 반영되는 리스트)
  const [candidateList, setCandidateList] = useState(candidates);

  const [teamList, setTeamList] = useState<TeamList[]>([{ id: 0, members: [] }]);
  const [records, setRecords] = useState<Record[]>([]);
  // const records:Record[] = [];

  

  const [isOpenMapSetting, setIsOpenMapSetting] = useState<boolean>(false);
  const [isOpenMapView, setIsOpenMapView] = useState<boolean>(false);

  const [isOpenAddMember, setIsOpenAddMember] = useState(false);
  const [addButtonIndex, setAddButtonIndex] = useState(0);

  const setRequestDatas = (date:string|undefined, time:string|undefined, place:PlaceType|undefined, candidates:string[], teams:TeamList[], records:Record[]) => {
    const datas = {
      schedule: {
        date: date,
        time: time,
        placeName: place?.place_name,
        placeCoordinate: {
          longitude: place?.y,
          latitude: place?.x
        }
      },
      canidates: candidates.length !== 0 ? candidates : [],
      teamList: teams.length !== 0 ? teams : [],
      records: records.length !== 0 ? records : []
    }
    console.log(datas)
    setDatas(datas);
  }

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

  if (!candidateList.length && isOpenAddMember) {
    setIsOpenAddMember(false);
  }

  useEffect(() => {
    setRequestDatas(date, time, placeValue, candidates, teamList, records);
  },[records])

  return (
    <S_Container>
      <S_Title>경기 등록</S_Title>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>날짜/시간 선택</S_Label>
        <S_Input type='date' value={date} onChange={dateChangeHandler} />
        <S_Input type='time' value={time} onChange={timeChangeHandler} />
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>장소</S_Label>
        <S_Input type='text' value={placeValue?.place_name} readOnly />
        <S_SelectButton onClick={mapSettingModalHandler}>지도설정</S_SelectButton>
        <S_SelectButton onClick={mapViewModalHandler}>지도보기</S_SelectButton>
        {isOpenMapSetting && (
          <S_MapBackdrop onClick={mapSettingModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapSearch
                mapSettingModalHandler={mapSettingModalHandler}
                setPlaceValue={setPlaceValue}
              />
            </S_MapView>
          </S_MapBackdrop>
        )}
        {isOpenMapView && (
          <S_MapBackdrop onClick={mapViewModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapView place={placeValue} />
            </S_MapView>
          </S_MapBackdrop>
        )}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>참석자</S_Label>
        <S_Description>
          경기를 등록하면 경기정보 페이지에서 참석/불참을 선택할 수 있어요.
        </S_Description>
        <S_Description>참석을 선택한 멤버는 자동으로 등록됩니다. </S_Description>
        {candidates &&
          candidates.map((member, idx) => {
            return <S_Tag key={idx}>{member}</S_Tag>;
          })}
        <S_EditButton>추가</S_EditButton>
      </div>
      <div style={{ position: 'relative', marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>팀구성</S_Label>
        {teamList &&
          teamList.map((team, idx) => {
            return (
                <div key={team.id} style={{ display: 'flex', alignItems: 'center' }}>
                  <S_Text style={{ margin: '0' }}>{idx + 1}팀</S_Text>
                  <div
                    style={{
                      display: 'grid',
                      gridTemplateColumns: `repeat(5, 1fr)`,
                      rowGap: '3px',
                      gridAutoRows: '27px',
                      border: 'none',
                      width: '350px',
                      marginLeft: '3px',
                      paddingLeft: '1px',
                      marginBottom: '3px'
                    }}
                  >
                    {teamList[idx].members.map((member, memberIdx) => (
                      <S_GridTag
                        key={team.id++}
                        onClick={() => {
                          const copied = [...teamList];
                          const deletedMember = copied[idx].members.splice(memberIdx, 1);
                          setCandidateList([...candidateList, deletedMember[0]]);
                          setTeamList(copied);
                        }}
                      >
                        {member}&times;
                      </S_GridTag>
                    ))}
                  </div>
                  <S_EditButton
                    onClick={() => {
                      if (!candidateList.length) {
                        return;
                      }
                      if (addButtonIndex !== idx) {
                        console.log(idx);
                        setAddButtonIndex(idx);
                        setIsOpenAddMember(false);
                        setIsOpenAddMember(true);
                      } else {
                        setAddButtonIndex(idx);
                        openAddMemberHandler();
                      }
                    }}
                  >
                    추가
                    {/* {isOpenAddMember ? "확인" : "추가"} */}
                  </S_EditButton>
                  <S_NegativeButton
                    onClick={() => {
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
                        console.log(deletedTeam);
                        setCandidateList([...candidateList, ...deletedTeam[0].members]);
                        setTeamList(deleted);
                        setIsOpenAddMember(false);
                      }
                    }}
                  >
                    삭제
                  </S_NegativeButton>
                </div>
            );
          })}
        {isOpenAddMember && (
          <AddMemberPopUp
            top={(addButtonIndex + 1) * 32}
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
            const newTeam = {
              id: teamList.length ? teamList[teamList.length - 1].id + 1 : 0,
              members: []
            };
            setTeamList([...teamList, newTeam]);
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
              <div key={record.id} style={{display:'flex', alignItems: 'center', justifyContent: 'space-between'}}>
                <span>{idx + 1}경기</span>
                <select {...register(`${record.id}.firstTeam`)}>
                  {teamList &&
                    teamList.map((team, idx) => {
                      return <option key={idx+1}>{idx + 1}</option>;
                    })}
                </select>
                <span>팀</span>
                <S_Input {...register(`${record.id}.firstTeamScore`)} type='number' style={{margin: '0', height: "30px"}}></S_Input>
                <span>:</span>
                <S_Input {...register(`${record.id}.secondTeamScore`)} type='number' style={{margin: '0', height: "30px"}}></S_Input>
                <select {...register(`${record.id}.secondTeam`)}>
                  {teamList &&
                    teamList.map((team, idx) => {
                      return <option key={idx+2}>{idx + 1}</option>;
                    })}
                </select>
                <span>팀</span>
                <S_NegativeButton
                  onClick={() => {
                    const deleted = [...records];
                    deleted.splice(idx, 1);
                    setRecords(deleted);
                    unregister(`${record.id}`);
                  }}
                >
                  삭제
                </S_NegativeButton>
              </div>
            );
          })}
        <S_ButtonGray
          style={{marginTop: '10px'}}
          onClick={() => {
            const newRecord:Record = {
              id: records.length ? records[records.length - 1].id + 1 : 0,
              firstTeam: "1",
              secondTeam: "1",
              firstTeamScore: 0,
              secondTeamScore: 0
            };
            setRecords([...records, newRecord]);
          }}
        >
          경기 결과 목록 추가+
        </S_ButtonGray>
      </div>
      <div>
        <S_Button onClick={() => {
          const copiedRecords:Record[] = [];
          const copiedValues = Object.entries(getValues());
          copiedValues.forEach(el => {
            const temp = {
              id: Number(el[0]),
              ...el[1]
            }
            copiedRecords.push(temp);
          })
          setRecords(copiedRecords);
        }}>저장하기 +</S_Button>
      </div>
    </S_Container>
  );
}

export default CreateMatch;
