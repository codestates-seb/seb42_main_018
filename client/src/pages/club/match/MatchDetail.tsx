import { useEffect, useState } from 'react';
import styled from 'styled-components';
import KakaoMapView from '../../../components/kakao/KakaoMapView';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import { S_SelectButton, S_NegativeButton } from '../../../components/UI/S_Button';
import { S_Description, S_Label, S_Text, S_Title } from '../../../components/UI/S_Text';
import { S_NameTag } from '../../../components/UI/S_Tag';
import { ModalBackdrop } from '../../../components/UI/S_Modal';
import { getFetch } from '../../../util/api';
import { useNavigate, useParams } from 'react-router-dom';
import { Schedule } from './ClubSchedule';
import { Candidate, Record, TeamList } from './CreateMatch';
import getGlobalState from '../../../util/authorization/getGlobalState';
import { ResUsersType } from './EditMatch';

const S_MapView = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 300px;
  height: 300px;
  border-radius: 20px;
  padding: 20px;
  section {
    width: 260px;
    border: 1px solid black;
    border-radius: 20px;
    padding: 10px;
  }
`;

function MatchDetail() {
  const [matchData, setMatchData] = useState<Schedule>();
  const { id, scid } = useParams();
  const { userInfo } = getGlobalState();
  const navigate = useNavigate();

  const [candidateList, setCandidateList] = useState<Candidate[]>([]);
  const [isOpenMapView, setIsOpenMapView] = useState(false);

  const mapViewModalHandler = () => {
    setIsOpenMapView(!isOpenMapView);
  };

  useEffect(() => {
    if (!userInfo.userClubResponses.map((el) => el.clubId).includes(Number(id))) {
      alert('권한이 없습니다.');
      navigate(`/club/${id}`);
    }
    getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/schedules/${scid}`).then((data) => {
      const resData = data.data;
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
          firstTeamNumber: el.firstTeam,
          secondTeamNumber: el.secondTeam,
          firstTeamScore: el.firstTeamScore,
          secondTeamScore: el.secondTeamScore
        };
      });

      setMatchData({
        ...resData,
        teamList,
        records
      });
    });
  }, []);

  return (
    <S_Container>
      <S_Title>경기 상세</S_Title>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>날짜/시간 선택 *</S_Label>
        <S_Input type='date' value={matchData?.date} readOnly />
        <S_Input type='time' value={matchData?.time} readOnly />
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>장소 *</S_Label>
        <S_Input type='text' value={matchData?.placeName} readOnly />
        <S_SelectButton onClick={mapViewModalHandler} style={{ width: 'auto' }}>
          지도보기
        </S_SelectButton>
        {isOpenMapView && (
          <ModalBackdrop onClick={mapViewModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapView x={matchData?.latitude} y={matchData?.longitude} />
            </S_MapView>
          </ModalBackdrop>
        )}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>참석자</S_Label>
        <div>
          {matchData?.candidates &&
            matchData?.candidates.map((member, idx) => {
              return <S_NameTag key={idx}>{member.nickName}</S_NameTag>;
            })}
        </div>
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>팀구성</S_Label>
        {matchData?.teamList &&
          matchData?.teamList.map((team, idx) => {
            return (
              <div key={team.id} style={{ display: 'flex', justifyContent: 'space-between' }}>
                <S_Text style={{ margin: '0' }}>{idx + 1}팀</S_Text>
                <div
                  style={{
                    flexGrow: 1,
                    border: 'none',
                    width: '60%',
                    marginLeft: '3px',
                    paddingLeft: '1px',
                    marginBottom: '3px'
                  }}
                >
                  {matchData.teamList[idx].members.map((member, memberIdx) => (
                    <S_NameTag key={memberIdx}>{member}&times;</S_NameTag>
                  ))}
                </div>
                <div style={{ height: '100%' }}></div>
              </div>
            );
          })}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>전적</S_Label>
        <S_Description>경기 결과입니다.</S_Description>
        {matchData?.records &&
          matchData?.records.map((record, idx) => {
            return (
              <div key={record.id} style={{ display: 'flex', alignItems: 'center' }}>
                <span style={{ width: '55px', color: 'var(--gray600)' }}>{idx + 1}경기</span>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    margin: '5px 0',
                    width: '85%'
                  }}
                >
                  <div
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      flexGrow: '1',
                      justifyContent: 'center',
                      height: '40px',
                      borderRadius: '8px',
                      backgroundColor:
                        record.firstTeamScore > record.secondTeamScore ? '#afffaf' : '#ff72726b'
                    }}
                  >
                    <span style={{ fontWeight: 'bold' }}>{record.firstTeamNumber}</span>
                    <span style={{ marginRight: '20px' }}>팀</span>
                    <S_Input
                      type='number'
                      style={{
                        margin: '0',
                        height: '30px',
                        textAlign: 'center',
                        width: '30px',
                        backgroundColor: 'var(--white)',
                        borderRadius: '8px'
                      }}
                      readOnly
                      value={record.firstTeamScore}
                    />
                  </div>
                  <span style={{ margin: '0 5px' }}>:</span>
                  <div
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      flexGrow: '1',
                      justifyContent: 'center',
                      height: '40px',
                      borderRadius: '8px',
                      backgroundColor:
                        record.firstTeamScore < record.secondTeamScore ? '#afffaf' : '#ff72726b'
                    }}
                  >
                    <S_Input
                      type='number'
                      style={{
                        margin: '0',
                        height: '30px',
                        textAlign: 'center',
                        width: '30px',
                        backgroundColor: 'var(--white)',
                        borderRadius: '8px'
                      }}
                      readOnly
                      value={record.secondTeamScore}
                    />
                    <span style={{ marginLeft: '20px', fontWeight: 'bold' }}>
                      {record.secondTeamNumber}
                    </span>
                    <span>팀</span>
                  </div>
                </div>
              </div>
            );
          })}
      </div>
      <div></div>
    </S_Container>
  );
}
export default MatchDetail;
