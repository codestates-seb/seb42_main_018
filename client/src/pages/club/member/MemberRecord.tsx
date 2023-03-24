import styled from 'styled-components';
import MemberRecordList from '../../../components/club/member/recordList';
import MemberRecordTitle from '../../../components/club/member/recordTitle';
import { ClubMemberProps } from '../../../types';

const S_Box = styled.div`
  // 컨텐츠 전체 박스
  margin-top: 20px;
  overflow-x: auto;
  overflow-y: hidden;
`;

function MemberRecord({ members }: ClubMemberProps) {
  return (
    <S_Box>
      <MemberRecordTitle />
      {members.map((el) => (
        <MemberRecordList
          key={el.nickName}
          profileImage={el.profileImage}
          nickName={el.nickName}
          winRate={el.winRate}
          playCount={el.playCount}
          winCount={el.winCount}
          drawCount={el.drawCount}
          loseCount={el.loseCount}
        />
      ))}
    </S_Box>
  );
}

export default MemberRecord;
