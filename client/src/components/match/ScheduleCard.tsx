import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { idText, ScriptKind } from 'typescript';
import { Candidate } from '../../pages/club/match/CreateMatch';
import { getFetch, postFetch } from '../../util/api';
import getGlobalState from '../../util/authorization/getGlobalState';
import { S_SelectButton } from '../UI/S_Button';
import { S_Label, S_SmallDescription, S_Text } from '../UI/S_Text';

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
}
function ScheduleCard(props: ScheduleCardProps) {
  // const { id } = useParams();
  const { isLogin, userInfo, tokens } = getGlobalState();
  const navigate = useNavigate();
  const [clickedButton, setClickedButton] = useState<string | null>('');
  const [candidateList, setCandidateList] = useState<Candidate[]>([]);
  const buttonHandler = (e: React.MouseEvent<HTMLElement>) => {
    setClickedButton(e.currentTarget.getAttribute('name'));
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
      getFetch(`${process.env.REACT_APP_URL}/candidates/schedules/${props.scheduleId}`).then(
        (data) => {
          setCandidateList([...data.data]);
          console.log(candidateList);
        }
      );
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
    );
  };

  return (
    <>
      <S_CardContainer onClick={() => navigate(`/club/${props.clubId}/match/${props.scheduleId}`)}>
        <S_Information>
          <S_Text>{`${props.date} ${props.time}`}</S_Text>
          <S_Label>{props.placeName}</S_Label>
        </S_Information>
        <S_ButtonContainer>
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
          <S_ButtonWrapper>
            <S_SelectButton
              name='attendance'
              clicked={`${clickedButton}`}
              className={clickedButton === 'attendance' ? 'clicked' : ''}
              onClick={(e) => {
                e.stopPropagation();
                buttonHandler(e);
                attendSchedule();
              }}
              style={{ margin: '2px' }}
            >
              참석
            </S_SelectButton>
            <S_SelectButton
              name='absence'
              clicked={`${clickedButton}`}
              className={clickedButton === 'absence' ? 'clicked' : ''}
              onClick={(e) => {
                e.stopPropagation();
                buttonHandler(e);
                absentSchedule();
              }}
              style={{ margin: '2px' }}
            >
              불참
            </S_SelectButton>
          </S_ButtonWrapper>
        </S_ButtonContainer>
      </S_CardContainer>
      <hr style={{ margin: '20px 0' }} />
    </>
  );
}

export default ScheduleCard;
