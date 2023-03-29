import styled from 'styled-components';
import { MemberData } from '../../../types';

const S_ListBox = styled.div`
  display: flex;
  text-align: center;
  align-items: center;
  height: 60px;
  border-bottom: 1px solid var(--gray100);
`;

const S_ListItem = styled.div<{ width?: string; color?: string; bgcolor?: string }>`
  // 기본 아이템 형식
  padding: 20px 0px;
  min-width: ${({ width }) => width || '50px'};
  color: ${({ color }) => color};
  background-color: ${({ bgcolor }) => bgcolor};
`;
const S_ProfileItem = styled(S_ListItem)`
  // 프로필사진 및 이름 부분
  display: flex;
  align-items: center;
  min-width: 190px;
  text-align: left;
  font-size: 1.3rem;
  font-weight: 800;
  img {
    width: 40px;
    height: 40px;
    object-fit: cover;
    border-radius: 8px;
    margin-right: 8px;
  }
`;

function MemberRecordList({
  profileImage,
  nickName,
  winRate,
  playCount,
  winCount,
  drawCount,
  loseCount
}: MemberData) {
  return (
    <S_ListBox>
      <S_ProfileItem>
        <img src={profileImage} alt='프로필사진'></img>
        {nickName}
      </S_ProfileItem>
      <S_ListItem width='80px' color='var(--blue300)' bgcolor='var(--blue100)'>
        {winRate}%
      </S_ListItem>
      <S_ListItem width='80px'>{playCount}</S_ListItem>
      <S_ListItem>{winCount}</S_ListItem>
      <S_ListItem>{drawCount}</S_ListItem>
      <S_ListItem>{loseCount}</S_ListItem>
    </S_ListBox>
  );
}

export default MemberRecordList;
