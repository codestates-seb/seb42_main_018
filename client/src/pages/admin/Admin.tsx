import { useState } from "react";
import { S_TabButton } from "../../components/UI/S_Button";
import S_Container from "../../components/UI/S_Container";
import { S_Title } from "../../components/UI/S_Text";

interface Tab {
  id: number;
  title: string;
  contents: React.ReactNode;
}

function Admin() {
  const [tabIndex, setTabIndex] = useState(0);
  const tabs: Tab[] = [
    { id: 1, title: '소모임 관리', contents: <></>},
    { id: 2, title: '회원 관리', contents: <></> },
  ];

  const onClickTap = (idx:number) => {
    setTabIndex(idx);
  }
  return (
    <S_Container>
      <S_Title>관리자</S_Title>
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
  )
}

export default Admin;