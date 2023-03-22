import styled from 'styled-components';
import { RecodeListProps } from '../../../types';

const S_ListBox = styled.div`
  display: flex;
  text-align: center;
`;

const S_ListItem = styled.div<{ width?: string; color?: string; bgcolor?: string }>`
  // 기본 아이템 형식
  padding: 18px 0px;
  min-width: ${({ width }) => width || '50px'};
  color: ${({ color }) => color};
  background-color: ${({ bgcolor }) => bgcolor};
  border-bottom: 1px solid var(--gray100);
`;
const S_ProfileItem = styled(S_ListItem)`
  // 프로필사진 및 이름 부분
  min-width: 190px;
  text-align: left;
  font-size: 1.3rem;
  font-weight: 800;
  img {
    font-size: 0.8rem;
    font-weight: 600;
    border-radius: 10px;
  }
`;

function MemberRecordList({ profileImage, name, winRate, match, win, lose }: RecodeListProps) {
  return (
    <S_ListBox>
      <S_ListItem>1</S_ListItem>
      <S_ProfileItem>
        <img src={profileImage} alt='프로필사진'></img>
        {name}
      </S_ProfileItem>
      <S_ListItem width='80px' color='var(--blue300)' bgcolor='var(--gray100)'>
        {winRate}%
      </S_ListItem>
      <S_ListItem width='80px'>{match}</S_ListItem>
      <S_ListItem>{win}</S_ListItem>
      <S_ListItem>{lose}</S_ListItem>
      <S_ListItem>03</S_ListItem>
    </S_ListBox>
  );
}

export default MemberRecordList;
