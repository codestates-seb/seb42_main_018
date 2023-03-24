import styled from 'styled-components';
import { S_Label, S_Description } from '../../UI/S_Text';
import { MemberData } from '../../../types';

const S_Box = styled.div`
  // 전체 컨테이너 스타일
  display: flex;
  align-items: center;
  padding: 20px 10px;
  border-top: 1px solid var(--gray100);
  img {
    // 프로필 이미지 스타일
    height: 65px;
    border-radius: 10px;
  }
`;
const S_Contents = styled.div`
  // 별명과 승률 스타일
  padding-left: 20px;
  padding-top: 5px;
`;

function ClubMemberItem({ nickName, profileImage, winRate }: MemberData) {
  // TODO: 전달받은 클럽 멤버 데이터를 매핑해줄 것

  return (
    <S_Box>
      <img src={profileImage} alt='프로필이미지' />
      <S_Contents>
        <S_Label>{nickName}</S_Label>
        <S_Description>승률 {winRate}%</S_Description>
      </S_Contents>
    </S_Box>
  );
}

export default ClubMemberItem;
