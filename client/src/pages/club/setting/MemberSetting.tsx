import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_TabButton } from '../../../components/UI/S_Button';
import S_Container from '../../../components/UI/S_Container';
import { S_Title } from '../../../components/UI/S_Text';
import BannedMember from './_bannedMember';
import TotalMember from './_totalMember';
import WaitingMember from './_waitingMember';

const S_TapMenu = styled.div``;

interface Tab {
  id: number;
  title: string;
  contents: React.ReactNode;
}

function MemberSetting() {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const tabs: Tab[] = [
    { id: 1, title: '전체 멤버 목록', contents: <TotalMember/>},
    { id: 2, title: '가입 대기 목록', contents: <WaitingMember/> },
    { id: 3, title: '차단 목록', contents: <BannedMember/> }
  ];

  const onClickTap = (idx:number) => {
    setTabIndex(idx);
  }

  return (
    <S_Container>
      <S_Title>회원 관리</S_Title>
      <div>
        {tabs.map((el, idx) => (
          <S_TabButton key={el.id} onClick={() => onClickTap(idx)} className={tabIndex === idx ? "clicked" : ""}>
            {el.title}
          </S_TabButton>
        ))}
      </div>
      {
        tabs[tabIndex].contents
      }
    </S_Container>
  );
}

export default MemberSetting;
