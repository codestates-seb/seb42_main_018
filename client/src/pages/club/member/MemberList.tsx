import styled from 'styled-components';
import ClubMemberItem from '../../../components/club/member/memberItem';
import NoItem from '../../../components/club/member/NoItem';
import { ClubMemberProps } from '../../../types';

const S_Box = styled.div`
  // 컨텐츠 전체 박스
  margin-top: 20px;
  overflow-x: auto;
`;

function MemberList({ members }: ClubMemberProps) {
  return (
    <S_Box>
      {/* 받아온 데이터 맵핑 */}
      {members.length === 0 ? (
        <NoItem
          src='https://3dicons.sgp1.cdn.digitaloceanspaces.com/v1/dynamic/color/notebook-dynamic-color.png'
          alt='빈 상자 이미지'
          label='아직 가입한 멤버가 없어요!'
        />
      ) : (
        members.map((el) => (
          <ClubMemberItem
            key={el.userId}
            profileImage={el.profileImage}
            nickName={el.nickName}
            winRate={el.winRate}
            clubRole={el.clubRole}
          />
        ))
      )}
    </S_Box>
  );
}

export default MemberList;
