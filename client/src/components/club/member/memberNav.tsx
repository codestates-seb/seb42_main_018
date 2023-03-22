import React, { useState } from 'react';
import ClubMember from '../../../pages/club/member/ClubMember';
import MemberRecord from '../../../pages/club/member/MemberRecord';

interface Tab {
  id: number;
  title: string;
  contents: React.ReactNode;
}

function MemberNav() {
  const [tab, setTab] = useState(0);

  const tabs: Tab[] = [
    { id: 1, title: '전체 멤버', contents: <ClubMember /> },
    { id: 2, title: '멤버 기록', contents: <MemberRecord /> }
  ];

  const onClickTap = (idx: number) => {
    setTab(idx);
  };
  return (
    <>
      {tabs.map((e, idx) => (
        <button key={e.id} onClick={() => onClickTap(idx)}>
          {e.title}
        </button>
      ))}
    </>
  );
}

export default MemberNav;
