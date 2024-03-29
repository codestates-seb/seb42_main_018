import { useEffect, useState } from 'react';
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
import { getFetch, patchFetch, postFetch, putFetch } from '../../../util/api';
import { useNavigate, useParams } from 'react-router-dom';
import { ModalBackdrop } from '../../../components/UI/S_Modal';
import {
  Candidate,
  MatchData,
  Record,
  S_ButtonBox,
  S_ConfirmModalContainer,
  S_MapView,
  TeamList
} from './CreateMatch';
import getGlobalState from '../../../util/authorization/getGlobalState';
import { MemberUser } from '../setting/_totalMember';
import AddCandidatePopUp from '../../../components/match/AddCandidatePopUp';

export interface ResUsersType {
  userId: number | undefined;
  email: string;
  nickName: string;
  userStatus: '' | 'USER_NEW' | 'USER_ACTIVE' | 'USER_SLEEP' | 'USER_QUIT';
  profileImage: string;
}

function EditMatch() {
  const {
    register,
    unregister,
    // formState: { errors },
    getValues
  } = useForm({ mode: 'onChange' });

  const [matchData, setMatchData] = useState<MatchData>();

  const { id, scid } = useParams();
  const { userInfo, tokens } = getGlobalState();
  const navigate = useNavigate();

  const [date, setDate] = useState<string>(new Date().toISOString().slice(0, 10));
  const [time, setTime] = useState<string>();
  const [placeValue, setPlaceValue] = useState<PlaceType>();

  //팀구성에 필요한 후보들(팀에 들어가거나 빠질 때 실시간 반영되는 리스트)
  const [candidateList, setCandidateList] = useState<string[]>([]);

  const [teamList, setTeamList] = useState<TeamList[]>([
    { id: 0, teamNumber: 1, members: [], membersIds: [] }
  ]);
  const [records, setRecords] = useState<Record[]>([]);

  const [totalMembers, setTotalMembers] = useState<MemberUser[]>([]);

  const [isPending, setIsPending] = useState(true);
  const [isOpenMapSetting, setIsOpenMapSetting] = useState(false);
  const [isOpenMapView, setIsOpenMapView] = useState(false);
  const [isOpenAddMember, setIsOpenAddMember] = useState(false);
  const [isOpenConfirm, setIsOpenConfirm] = useState(false);
  const [isOpenAddCandidate, setIsOpenAddCandidate] = useState(false);

  const [addButtonIndex, setAddButtonIndex] = useState(0);
  const [addButtonPos, setAddButtonPos] = useState({ x: 0, y: 0 });
  const [addCandidateButtonPos, setAddCandidateButtonPos] = useState({ x: 0, y: 0 });

  const dateChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDate(e.target.value);
  };

  const timeChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTime(e.target.value);
  };

  const openAddCandidateHandler = () => {
    setIsOpenAddCandidate(!isOpenAddCandidate);
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
    copied[idx].membersIds.splice(memberIdx, 1);
    setCandidateList([...candidateList, deletedMember[0]]);
    setTeamList(copied);
  };

  const openMemberListPopup = (idx: number, e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    if (!candidateList.length) {
      return;
    }
    if (addButtonIndex !== idx) {
      setAddButtonIndex(idx);
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
      teamNumber: teamList.length + 1,
      members: [],
      membersIds: []
    };
    setTeamList([...teamList, newTeam]);
  };

  const deleteTeam = (idx: number) => {
    if (teamList.length === 1) {
      setCandidateList(matchData?.candidates.map((el) => el.nickName) as string[]);
      setTeamList([
        {
          id: 0,
          teamNumber: 1,
          members: [],
          membersIds: []
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
      firstTeamNumber: 1,
      secondTeamNumber: 2,
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

  const updateRecord = async () => {
    const copiedRecords: Record[] = [];
    const copiedValues = Object.entries(getValues());
    copiedValues.forEach((el) => {
      const temp = {
        id: Number(el[0]),
        firstTeamNumber: Number(el[1].firstTeamNumber),
        secondTeamNumber: Number(el[1].secondTeamNumber),
        firstTeamScore: Number(el[1].firstTeamScore),
        secondTeamScore: Number(el[1].secondTeamScore)
      };
      copiedRecords.push(temp);
    });
    setRecords(copiedRecords);
  };

  const saveMatchData = () => {
    setTeamList(
      [...teamList].map((el, idx) => {
        return {
          ...el,
          teamNumber: idx + 1
        };
      })
    );
    setMatchData({
      date,
      time,
      placeName: placeValue?.place_name,
      longitude: Number(placeValue?.y),
      latitude: Number(placeValue?.x),
      candidates: matchData?.candidates.length !== 0 ? (matchData?.candidates as Candidate[]) : [],
      teamList: !(teamList?.length === 1 && teamList[0].members.length === 0) ? teamList : [],
      records: records.length !== 0 ? records : []
    });
  };

  const putMatchData = async () => {
    await putFetch(`${process.env.REACT_APP_URL}/clubs/${id}/schedules/${scid}`, matchData, tokens);
  };

  if (!candidateList.length && isOpenAddMember) {
    setIsOpenAddMember(false);
  }

  const absentSchedule = async (userId: number) => {
    await postFetch(
      `${process.env.REACT_APP_URL}/clubs/${id}/schedules/${scid}/users/${userId}/absent`,
      {
        userId,
        scheduleId: scid,
        clubId: id
      },
      tokens
    );
    await getFetch(`${process.env.REACT_APP_URL}/candidates/schedules/${scid}`, tokens).then(
      (res) => {
        setMatchData({
          ...(matchData as MatchData),
          candidates: res.data
        });
      }
    );
  };

  const deleteCandidateFromTeam = (userId: number) => {
    const copiedTeamList = [...teamList];
    copiedTeamList.forEach((team) => {
      if (team.membersIds.includes(userId)) {
        const idx = team.membersIds.indexOf(userId);
        team.membersIds.splice(idx, 1);
        team.members.splice(idx, 1);
      }
    });
    setTeamList([...copiedTeamList]);
  };

  const checkTeamInRecords = (): boolean => {
    let result = true;
    records.forEach((record) => {
      const firstTeam = teamList.filter((el) => el.teamNumber === record.firstTeamNumber);
      const secondTeam = teamList.filter((el) => el.teamNumber === record.secondTeamNumber);

      firstTeam[0].membersIds.forEach((el) => {
        if (secondTeam[0].membersIds.includes(el)) {
          alert('두 팀에 동일한 멤버가 존재합니다.');
          result = false;
        }
      });
    });
    return result;
  };

  useEffect(() => {
    if (!userInfo.userClubResponses.map((el) => el.clubId).includes(Number(id))) {
      alert('권한이 없습니다.');
      navigate(`/club/${id}`);
    }
    getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/schedules/${scid}`).then((res) => {
      const resData = res.data;
      const teamList = resData.teamList.map((el: TeamList) => {
        return {
          id: el.teamId,
          teamNumber: el.teamNumber,
          members: el.users?.map((el: ResUsersType) => el.nickName),
          membersIds: el.users?.map((el: ResUsersType) => el.userId)
        };
      });

      const records = resData.records.map((el: Record) => {
        return {
          id: el.recordId,
          firstTeamNumber: el.firstTeamNumber,
          secondTeamNumber: el.secondTeamNumber,
          firstTeamScore: el.firstTeamScore,
          secondTeamScore: el.secondTeamScore
        };
      });

      setMatchData({
        ...resData,
        teamList,
        records
      });
      setIsPending(false);
    });
  }, []);

  useEffect(() => {
    if (matchData) {
      setDate(matchData.date);
      setTime(matchData.time);
      setPlaceValue({
        place_name: matchData.placeName,
        x: matchData.latitude,
        y: matchData.longitude
      });
      setTeamList(
        matchData.teamList.length === 0
          ? [{ id: 0, teamNumber: 1, members: [], membersIds: [] }]
          : matchData.teamList
      );
      setRecords(matchData.records);
      setCandidateList(matchData?.candidates.map((el) => el.nickName));
    }
  }, [isPending]);

  useEffect(() => {
    saveMatchData();
  }, [records]);

  useEffect(() => {
    if (isOpenAddCandidate) {
      getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/members`, tokens).then((data) => {
        setTotalMembers(data.data);
      });
    }
  }, [isOpenAddCandidate]);

  return (
    <S_Container
      onClick={() => {
        setIsOpenAddMember(false);
        setIsOpenAddCandidate(false);
      }}
    >
      <S_Title>경기 수정</S_Title>
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
          <ModalBackdrop onClick={mapSettingModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapSearch
                mapSettingModalHandler={mapSettingModalHandler}
                setPlaceValue={setPlaceValue}
                placeValue={placeValue}
              />
            </S_MapView>
          </ModalBackdrop>
        )}
        {isOpenMapView && (
          <ModalBackdrop onClick={mapViewModalHandler}>
            <S_MapView style={{ height: '300px' }} onClick={(e) => e.stopPropagation()}>
              {placeValue && <KakaoMapView y={placeValue.y} x={placeValue.x} />}
            </S_MapView>
          </ModalBackdrop>
        )}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>참석자</S_Label>
        <S_Description>
          경기를 등록하면 경기정보 페이지에서 참석/불참을 선택할 수 있어요.
        </S_Description>
        <S_Description>
          참석을 선택한 멤버는 자동으로 등록됩니다.
          <S_EditButton
            style={{ padding: '0 7px', float: 'right' }}
            onClick={(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
              e.stopPropagation();
              openAddCandidateHandler();
              setAddCandidateButtonPos({ x: e.nativeEvent.pageX, y: e.nativeEvent.pageY });
            }}
          >
            추가
          </S_EditButton>
        </S_Description>

        <div>
          {matchData?.candidates &&
            matchData?.candidates.map((member, idx) => {
              return (
                <S_NameTag
                  key={idx}
                  onClick={() => {
                    deleteCandidateFromTeam(member.userId);
                    absentSchedule(member.userId);
                  }}
                >
                  {member.nickName}&times;
                </S_NameTag>
              );
            })}
        </div>
        {isOpenAddCandidate && (
          <AddCandidatePopUp
            top={addCandidateButtonPos.y}
            left={addCandidateButtonPos.x}
            candidates={matchData?.candidates}
            setIsOpenAddMember={setIsOpenAddMember}
            totalMembers={totalMembers}
            setMatchData={setMatchData}
            matchData={matchData}
          />
        )}
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
                  records={records}
                  setRecords={setRecords}
                />
              </div>
            );
          })}
        {isOpenAddMember && (
          <AddMemberPopUp
            top={addButtonPos.y}
            left={addButtonPos.x}
            candidates={matchData?.candidates as Candidate[]}
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
            if (!checkValidation()) {
              alert('*가 표시된 항목은 필수 입력란입니다.');
              return;
            }
            updateRecord();
            if (!checkTeamInRecords()) {
              return;
            }
            setIsOpenConfirm(true);
          }}
        >
          저장하기 +
        </S_Button>
        {isOpenConfirm && (
          <ModalBackdrop>
            <S_ConfirmModalContainer>
              <S_Label>일정을 등록하시겠습니까?</S_Label>
              <S_ButtonBox>
                <S_Button
                  addStyle={{ width: '48%' }}
                  onClick={() => {
                    putMatchData().then(() => navigate(`/club/${id}/match`));
                  }}
                >
                  확인
                </S_Button>
                <S_Button
                  addStyle={{
                    width: '48%',
                    backgroundColor: 'var(--gray100)',
                    color: 'var(--gray400)',
                    hoverBgColor: 'var(--gray200)'
                  }}
                  onClick={() => {
                    setIsOpenConfirm(false);
                  }}
                >
                  취소
                </S_Button>
              </S_ButtonBox>
            </S_ConfirmModalContainer>
          </ModalBackdrop>
        )}
      </div>
    </S_Container>
  );
}

export default EditMatch;
