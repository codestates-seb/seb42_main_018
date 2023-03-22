import styled from 'styled-components';
import MemberRecordList from '../../../components/club/member/recordList';
import MemberRecordTitle from '../../../components/club/member/recordTitle';
import { S_Title } from '../../../components/UI/S_Text';
import { RecodeListProps } from '../../../types';

const S_Box = styled.div`
  // 컨텐츠 전체 박스
  margin-top: 20px;
  overflow-x: auto;
  overflow-y: hidden;
`;

function MemberRecord() {
  // TODO: 하드코딩 데이터로 표시, 추후 axios get 요청 구현
  const data: RecodeListProps[] = [
    {
      memberId: 1,
      profileImage: 'https://avatars.githubusercontent.com/u/115607789?s=64&v=4',
      name: '벌꿀오소리',
      winRate: '80',
      match: '10',
      win: '8',
      lose: '2'
    },
    {
      memberId: 1,
      profileImage: 'https://avatars.githubusercontent.com/u/97533911?s=120&v=4',
      name: '벌꿀오소리',
      winRate: '80',
      match: '10',
      win: '8',
      lose: '2'
    }
  ];
  return (
    <S_Box>
      <MemberRecordTitle />
      {data.map((e) => (
        <MemberRecordList
          key={e.memberId}
          profileImage={e.profileImage}
          name={e.name}
          winRate={e.winRate}
          match={e.match}
          win={e.win}
          lose={e.lose}
        />
      ))}
    </S_Box>
  );
}

export default MemberRecord;
