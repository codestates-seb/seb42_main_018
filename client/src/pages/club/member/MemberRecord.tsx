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
  // TODO: 하드코딩 데이터로 표시, 추후 axios get 요청 구현
  // const data = {
  //   data: [
  //     {
  //       nickName: 'John',
  //       profileImage: {
  //         imageId: 1,
  //         fileName: 'image.jpg',
  //         url: 'https://avatars.githubusercontent.com/u/115607789?s=64&v=4'
  //       },
  //       playCount: 10,
  //       winCount: 7,
  //       loseCount: 2,
  //       drawCount: 1,
  //       winRate: 0.7
  //     },

  //     {
  //       nickName: 'Jane',
  //       profileImage: {
  //         imageId: 2,
  //         fileName: 'image.jpg',
  //         url: 'https://avatars.githubusercontent.com/u/115607789?s=64&v=4'
  //       },
  //       playCount: 15,
  //       winCount: 5,
  //       loseCount: 8,
  //       drawCount: 2,
  //       winRate: 0.33
  //     }
  //   ],
  //   pageInfo: {
  //     page: 1,
  //     size: 5,
  //     totalElements: 2,
  //     totalPages: 1
  //   }
  // };
  return (
    <S_Box>
      <MemberRecordTitle />
      {members.map((e) => (
        <MemberRecordList
          key={e.nickName}
          profileImage={e.profileImage}
          nickName={e.nickName}
          winRate={e.winRate}
          playCount={e.playCount}
          winCount={e.winCount}
          drawCount={e.drawCount}
          loseCount={e.loseCount}
        />
      ))}
    </S_Box>
  );
}

export default MemberRecord;
