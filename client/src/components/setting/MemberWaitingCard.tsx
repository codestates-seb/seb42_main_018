import styled from 'styled-components';
import { WaitingUser } from '../../pages/club/setting/_waitingMember';
import dummy from '../../assets/default_profile.svg';
import { S_Description, S_Text } from '../UI/S_Text';
import { S_SelectButton } from '../UI/S_Button';
import { patchFetch, postFetch } from '../../util/api';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const S_WaitingCardContainer = styled.div`
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid var(--gray200);
`;

const S_CardWrapper = styled.div`
  display: flex;
`;
const S_ImageWrapper = styled.div`
  margin: 10px;
`;
const S_ContentWrapper = styled.div`
  padding-top: 10px;
  width: 100%;
  height: 100px;
`;
const S_ButtonWrapper = styled.div`
  text-align: right;
  margin: 5px 0;
`;

interface MemberWaitingCardProps {
  member: WaitingUser;
  setIsUpdated: React.Dispatch<React.SetStateAction<boolean>>;
  isUpdated: boolean;
}

function MemberWaitingCard(props: MemberWaitingCardProps) {
  const { id } = useParams();
  const acceptMember = async () => {
    patchFetch(`${process.env.REACT_APP_URL}/clubs/${id}/joins/${props.member.userInfo.userId}`, {
      joinStatus: 'CONFIRMED'
    }).then(() => {
      props.setIsUpdated(!props.isUpdated);
    });
  };

  const rejectMember = async () => {
    patchFetch(`${process.env.REACT_APP_URL}/clubs/${id}/joins/${props.member.userInfo.userId}`, {
      joinStatus: 'REFUSED'
    }).then(() => {
      props.setIsUpdated(!props.isUpdated);
    });
  };
  return (
    <S_WaitingCardContainer>
      <S_CardWrapper>
        <S_ImageWrapper>
          <img alt='profile' src={dummy} />
        </S_ImageWrapper>
        <S_ContentWrapper>
          <S_Text color='var(--black)' style={{ fontWeight: 'bold' }}>
            {props.member.userInfo.nickName}
          </S_Text>
          <S_Description color='var(--gray500)'>{props.member.content}</S_Description>
        </S_ContentWrapper>
      </S_CardWrapper>
      <S_ButtonWrapper>
        <S_SelectButton onClick={acceptMember} width='15%' style={{ marginRight: '5px' }}>
          가입승인
        </S_SelectButton>
        <S_SelectButton onClick={rejectMember} width='15%'>
          가입거절
        </S_SelectButton>
      </S_ButtonWrapper>
    </S_WaitingCardContainer>
  );
}

export default MemberWaitingCard;
