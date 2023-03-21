import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import Tabmenu from '../../../components/TabMenu';
import S_Container from '../../../components/UI/S_Container';
import ClubMemberItem from './_memberItem';
import MemberNav from './_memberNav';

const S_MemberBox = styled.div`
  min-height: calc(100vh - 100px);
`;

export interface ClubMemberProps {
  // 받아올 멤버 정보 타입 설정
  memberId?: number;
  profileImage: string;
  name: string;
  winRate: string;
}

function ClubMember() {
  const { id } = useParams();
  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

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
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_MemberBox>
        {data.map((e) => (
          <ClubMemberItem
            key={e.memberId}
            profileImage={e.profileImage}
            name={e.name}
            winRate={e.winRate}
          />
        ))}
        <MemberNav />
      </S_MemberBox>
    </S_Container>
  );
}

export default ClubMember;
