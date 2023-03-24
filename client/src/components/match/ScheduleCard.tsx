import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { S_SelectButton } from '../UI/S_Button';
import { S_Label, S_Text } from '../UI/S_Text';

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
  const navigate = useNavigate();
  const { id } = useParams();
  const [clickedButton, setClickedButton] = useState<string | null>('');
  const buttonHandler = (e: React.MouseEvent<HTMLElement>) => {
    setClickedButton(e.currentTarget.getAttribute('name'));
  };
  return (
    <>
      <hr style={{ margin: '20px 0' }} />
      <S_CardContainer onClick={() => navigate(`/club/${id}/match/${props.scheduleId}`)}>
        <S_Information>
          <S_Text>{`${props.date} ${props.time}`}</S_Text>
          <S_Label>{props.placeName}</S_Label>
        </S_Information>
        <S_ButtonContainer>
          <S_SelectButton
            name='attendance'
            clicked={`${clickedButton}`}
            className={clickedButton === 'attendance' ? 'clicked' : ''}
            onClick={buttonHandler}
            style={{ margin: '2px' }}
          >
            참석
          </S_SelectButton>
          <S_SelectButton
            name='absence'
            clicked={`${clickedButton}`}
            className={clickedButton === 'absence' ? 'clicked' : ''}
            onClick={buttonHandler}
            style={{ margin: '2px' }}
          >
            불참
          </S_SelectButton>
        </S_ButtonContainer>
      </S_CardContainer>
    </>
  );
}

export default ScheduleCard;
