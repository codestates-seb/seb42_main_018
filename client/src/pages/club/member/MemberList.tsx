import styled from 'styled-components';
import ClubMemberItem from '../../../components/club/member/memberItem';
import { ClubMemberProps } from '../../../types';

const S_Box = styled.div`
  // 컨텐츠 전체 박스
  margin-top: 20px;
`;

function MemberList({ members }: ClubMemberProps) {
  return (
    <S_Box>
      {/* 받아온 데이터 맵핑 */}
      {members.map((el) => (
        <ClubMemberItem
          key={el.nickName}
          profileImage={el.profileImage}
          nickName={el.nickName}
          winRate={el.winRate}
        />
      ))}
    </S_Box>
  );
}

export default MemberList;