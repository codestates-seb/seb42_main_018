import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import Tabmenu from '../../../components/TabMenu';
import S_Container from '../../../components/UI/S_Container';
import MemberNav from './_memberNav';
import MemberRecordList from './_recordList';
import MemberRecordTitle from './_recordTitle';

const S_MemberRecordBox = styled.div`
  overflow: auto;
`;
export interface RecodeListProps {
  // 받아올 멤버 정보 타입 설정
  memberId?: number;
  profileImage: string;
  name: string;
  winRate: string;
  match: string;
  win: string;
  lose: string;
}

function MemberRecord() {
  const { id } = useParams();
  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

  // TODO: 하드코딩 데이터로 표시, 추후 axios get 요청 구현
  const data: RecodeListProps[] = [
    {
      memberId: 1,
      profileImage: '이미지',
      name: '별명',
      winRate: 'string',
      match: 'string',
      win: 'string',
      lose: 'string'
    }
  ];
  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <MemberNav />
      <S_MemberRecordBox>
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
      </S_MemberRecordBox>
    </S_Container>
  );
}

export default MemberRecord;
