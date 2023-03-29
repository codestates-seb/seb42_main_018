import styled from 'styled-components';
import NoItem from '../../../components/club/member/NoItem';
import MemberRecordList from '../../../components/club/member/recordList';
import MemberRecordTitle from '../../../components/club/member/recordTitle';
import { ClubMemberProps } from '../../../types';

const S_Box = styled.div`
  // 컨텐츠 전체 박스
  margin-top: 20px;
  overflow-x: auto;
  overflow-y: hidden;
  margin-bottom: 20px;
`;

function MemberRecord({ members }: ClubMemberProps) {
  return (
    <S_Box>
      {members.length === 0 && (
        <NoItem
          src='https://3dicons.sgp1.cdn.digitaloceanspaces.com/v1/dynamic/color/chart-dynamic-color.png'
          alt='차트 이미지'
          label='아직 기록된 경기가 없어요!'
        />
      )}
      {/* TODO : 정렬 로직 추가 */}
      {members.length !== 0 && <MemberRecordTitle />}
      {members.length !== 0 &&
        members.map((el) => (
          <MemberRecordList
            key={el.nickName}
            profileImage={el.profileImage}
            nickName={el.nickName}
            clubRole={el.clubRole}
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
