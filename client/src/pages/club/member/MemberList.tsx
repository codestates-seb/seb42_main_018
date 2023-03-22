import styled from 'styled-components';
import ClubMemberItem from '../../../components/club/member/memberItem';
import { S_Title } from '../../../components/UI/S_Text';
import { ClubMemberProps } from '../../../types';
const S_Box = styled.div`
  // 컨텐츠 전체 박스
  margin-top: 20px;
`;

function MemberList() {
  // TODO: 하드코딩 데이터로 표시, 추후 axios get 요청 구현
  const data: ClubMemberProps[] = [
    {
      memberId: 1,
      profileImage: '이미지',
      name: '별명',
      winRate: 'string'
    }
  ];

  return (
    <S_Box>
      {data.map((e) => (
        <ClubMemberItem
          key={e.memberId}
          profileImage={e.profileImage}
          name={e.name}
          winRate={e.winRate}
        />
      ))}
    </S_Box>
  );
}

export default MemberList;
