import styled from 'styled-components';
import { S_SelectButton } from '../UI/S_Button';
import { S_Description, S_Text } from '../UI/S_Text';
import dummy from '../../assets/default_profile.svg';
import { MemberUser } from '../../pages/club/setting/_totalMember';
import { S_Select } from '../UI/S_Select';

const S_SettingCardContainer = styled.div`
  display: flex;
`;
const S_ImageWrapper = styled.div`
  margin: 10px;
`;
const S_ContentWrapper = styled.div`
  padding-top: 10px;
  width: 50%;
  height: 60px;
`;
const S_ButtonWrapper = styled.div`
  display: flex;
  justify-content: end;
  align-items: center;
  width: 50%;
  margin: 5px 0;
`;

interface MemberUserCardProps {
  member: MemberUser;
}

function MemberUserCard(props: MemberUserCardProps) {
  const banishMember = () => {
    console.log('추방API작성');
  };
  return (
    <S_SettingCardContainer>
      <S_ImageWrapper>
        <img alt='profile' src={dummy} />
      </S_ImageWrapper>
      <S_ContentWrapper>
        <S_Text color='var(--black)' style={{ fontWeight: 'bold' }}>
          {props.member.nickName}
        </S_Text>
      </S_ContentWrapper>
      <S_ButtonWrapper>
        <S_Select style={{ margin: '0' }}>
          <option>일반</option>
          <option>매니저</option>
        </S_Select>
        <S_SelectButton onClick={banishMember} width='auto'>
          추방
        </S_SelectButton>
      </S_ButtonWrapper>
    </S_SettingCardContainer>
  );
}

export default MemberUserCard;
