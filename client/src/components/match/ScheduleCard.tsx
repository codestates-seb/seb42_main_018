import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import {
  Candidate,
  S_ButtonBox,
  S_ConfirmModalContainer
} from '../../pages/club/match/CreateMatch';
import { deleteFetch, getFetch, postFetch } from '../../util/api';
import getGlobalState from '../../util/authorization/getGlobalState';
import { S_Button, S_SelectButton } from '../UI/S_Button';
import { ModalBackdrop } from '../UI/S_Modal';
import { S_Description, S_Label, S_SmallDescription, S_Text } from '../UI/S_Text';

const S_CardContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;
const S_Information = styled.div`
  display: flex;
  flex-direction: column;
`;

const S_ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const S_ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
`;
interface ScheduleCardProps {
  date?: string;
  time?: string;
  placeName?: string;
  scheduleId: number;
  clubId: number;
  candidates: Candidate[];
}
function ScheduleCard(props: ScheduleCardProps) {
  const { id } = useParams();
  const { userInfo, tokens } = getGlobalState();
  const navigate = useNavigate();
  const [candidateList, setCandidateList] = useState<Candidate[]>(props.candidates);
  const [candidatesUserId, setCandidatesUserId] = useState<number[]>([]);
  const [isOpenDeleteSchedule, setIsOpenDeleteSchedule] = useState(false);

  const isPassed = new Date(`${props.date} ${props.time}`) < new Date();

  const myClub = userInfo.userClubResponses?.find((club) => club.clubId === Number(id));
  const isLeader = myClub?.clubRole === 'LEADER' || myClub?.clubRole === 'MANAGER';

  const buttonHandler = () => {
    getFetch(`${process.env.REACT_APP_URL}/candidates/schedules/${props.scheduleId}`).then(
      (data) => {
        setCandidateList([...data.data]);
      }
    );
  };

  const attendSchedule = () => {
    postFetch(
      `${process.env.REACT_APP_URL}/clubs/${props.clubId}/schedules/${props.scheduleId}/users/${userInfo.userId}/attend`,
      {
        userId: userInfo.userId,
        scheduleId: props.scheduleId,
        clubId: props.clubId
      },
      tokens
    ).then(() => {
      buttonHandler();
    });
  };

  const absentSchedule = () => {
    postFetch(
      `${process.env.REACT_APP_URL}/clubs/${props.clubId}/schedules/${props.scheduleId}/users/${userInfo.userId}/absent`,
      {
        userId: userInfo.userId,
        scheduleId: props.scheduleId,
        clubId: props.clubId
      },
      tokens
    ).then(() => {
      buttonHandler();
    });
  };

  const deleteSchedule = () => {
    deleteFetch(
      `${process.env.REACT_APP_URL}/clubs/${props.clubId}/schedules/${props.scheduleId}`,
      tokens
    );
  };

  useEffect(() => {
    setCandidatesUserId([
      ...candidateList.filter((ele) => ele.attendance === 'ATTEND').map((el) => el.userId)
    ]);
  }, [candidateList]);

  return (
    <>
      <S_CardContainer onClick={() => navigate(`/club/${props.clubId}/match/${props.scheduleId}`)}>
        <S_Information>
          <S_Text>{`${props.date} ${props.time}`}</S_Text>
          <S_Label>{props.placeName}</S_Label>
        </S_Information>
        <S_ButtonContainer>
          {isLeader && (
            <div style={{ display: 'flex', justifyContent: 'end' }}>
              <S_SmallDescription
                onClick={(e) => {
                  e.stopPropagation();
                  navigate(`/club/${props.clubId}/match/${props.scheduleId}/edit`);
                }}
                color='var(--red100)'
                style={{ textAlign: 'right', marginRight: '10px' }}
              >
                수정
              </S_SmallDescription>
              <S_SmallDescription
                onClick={(e) => {
                  e.stopPropagation();
                  setIsOpenDeleteSchedule(true);
                }}
                color='var(--red100)'
                style={{ textAlign: 'right', marginRight: '10px' }}
              >
                삭제
              </S_SmallDescription>
            </div>
          )}
          {!isPassed && (
            <S_ButtonWrapper>
              <S_SelectButton
                name='attendance'
                className={candidatesUserId.includes(userInfo.userId as number) ? 'attendance' : ''}
                onClick={(e) => {
                  e.stopPropagation();
                  attendSchedule();
                }}
                style={{ margin: '2px' }}
              >
                참석
              </S_SelectButton>
              <S_SelectButton
                name='absence'
                className={!candidatesUserId.includes(userInfo.userId as number) ? 'absence' : ''}
                onClick={(e) => {
                  e.stopPropagation();
                  absentSchedule();
                }}
                style={{ margin: '2px' }}
              >
                불참
              </S_SelectButton>
            </S_ButtonWrapper>
          )}
        </S_ButtonContainer>
      </S_CardContainer>
      <hr style={{ margin: '20px 0' }} />
      {isOpenDeleteSchedule && (
        <ModalBackdrop>
          <S_ConfirmModalContainer style={{ height: 'auto' }}>
            <div style={{ width: '90%' }}>
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <S_Label style={{ textAlign: 'left' }}>{props.placeName}</S_Label>
                <S_Description style={{ textAlign: 'right' }}>&nbsp;[{props.date}]</S_Description>
              </div>
              <S_Description style={{ textAlign: 'right' }}>
                스케쥴을 삭제 하시겠습니까?
              </S_Description>
            </div>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  deleteSchedule();
                  setIsOpenDeleteSchedule(false);
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
                  setIsOpenDeleteSchedule(false);
                }}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_ConfirmModalContainer>
        </ModalBackdrop>
      )}
    </>
  );
}

export default ScheduleCard;
