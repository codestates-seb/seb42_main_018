import styled from 'styled-components';
import { S_Label, S_Description } from '../../UI/S_Text';
import { MemberData } from '../../../types';
import leaderBadgeIcon from '../../../assets/icon_leader-badge.svg';

const S_Box = styled.div`
  // 전체 컨테이너 스타일
  display: flex;
  align-items: center;
  padding: 20px 10px;
  border-top: 1px solid var(--gray100);
  .profileImg {
    // 프로필 이미지 스타일
    object-fit: cover;
    width: 65px;
    height: 65px;
    border-radius: 10px;
  }
  .leaderBadge {
    margin-left: 5px;
    width: 1rem;
  }
`;
const S_Contents = styled.div`
  // 별명과 승률 스타일
  padding-left: 20px;
  padding-top: 5px;
`;

function ClubMemberItem({ nickName, profileImage, winRate, clubRole }: MemberData) {
  return (
    <S_Box>
      <img className='profileImg' src={profileImage} alt='프로필이미지' />
      <S_Contents>
        <S_Label>
          {nickName}
          {clubRole === 'LEADER' && (
            <img className='leaderBadge' src={leaderBadgeIcon} alt='소모임장 아이콘' />
          )}
        </S_Label>
        <S_Description>승률 {winRate * 100}%</S_Description>
      </S_Contents>
    </S_Box>
  );
}

export default ClubMemberItem;
