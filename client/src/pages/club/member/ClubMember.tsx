import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import Tabmenu from '../../../components/TabMenu';
import S_Container from '../../../components/UI/S_Container';
import ClubMemberItem from '../../../components/club/member/memberItem';
import { ClubMemberProps, SubTab } from '../../../types';
import MemberRecord from './MemberRecord';
import SubTabMenu from '../../../components/SubTabMenu';
import MemberList from './MemberList';

const S_MemberBox = styled.div``;

function ClubMember() {
  const { id } = useParams();
  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];
  const subtabs = [
    { id: 1, title: '전체 멤버', contents: <MemberList /> },
    { id: 2, title: '멤버 기록', contents: <MemberRecord /> }
  ];

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <SubTabMenu subtabs={subtabs} />
    </S_Container>
  );
}

export default ClubMember;
