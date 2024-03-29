import { useNavigate, useParams } from 'react-router-dom';
import Tabmenu from '../../../components/TabMenu';
import S_Container from '../../../components/UI/S_Container';
import MemberRecord from './MemberRecord';
import SubTabMenu from '../../../components/SubTabMenu';
import MemberList from './MemberList';
import { MemberData } from '../../../types';
import { useEffect, useState } from 'react';
import { getFetch } from '../../../util/api';
import getGlobalState from '../../../util/authorization/getGlobalState';

function ClubMember() {
  const { id } = useParams();
  const { userInfo, tokens } = getGlobalState();
  const navigate = useNavigate();

  const [members, setMembers] = useState<MemberData[]>([]); // 뿌려줄 멤버 리스트
  useEffect(() => {
    // if (!userInfo.userClubResponses.map((el) => el.clubId).includes(Number(id))) {
    //   alert('권한이 없습니다.');
    //   navigate(`/club/${id}`);
    // }
    getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/members`, tokens).then((data) => {
      const members: MemberData[] = data.data;
      setMembers(members);
    });
  }, []);

  // 상단탭
  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

  // 서브 버튼탭
  const subtabs = [
    {
      id: 1,
      title: '전체 멤버',
      contents: <MemberList members={members} />
    },
    {
      id: 2,
      title: '멤버 기록',
      contents: <MemberRecord members={members} />
    }
  ];

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <SubTabMenu subtabs={subtabs} />
    </S_Container>
  );
}

export default ClubMember;
