import { useLocation, useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const S_TabContainer = styled.div`
  // 탭메뉴 전체 박스
  margin: -20px auto 0;
  max-width: 500px;
  position: fixed;
  top: 50;
  left: 0;
  right: 0;
  display: flex;
  z-index: 100;
`;
const S_TabItem = styled.button<{ active: boolean }>`
  // 탭 하나하나 버튼 스타일
  flex-grow: 1;
  height: 50px;
  align-items: center;
  font-size: 1.2rem;
  font-weight: 600;
  background-color: var(--white);
  color: ${({ active }) => (active ? 'var(--gray600)' : 'var(--gray300)')};
  border-bottom: 2px solid ${({ active }) => (active ? 'var(--gray600)' : 'var(--white)')};

  :hover {
    color: var(--gray600);
  }
`;
interface Tab {
  id: number;
  title: string;
  path: string;
}

interface TabmenuProps {
  tabs: Tab[];
}

function Tabmenu({ tabs }: TabmenuProps) {
  const navigate = useNavigate();
  const location = useLocation().pathname;

  return (
    <S_TabContainer>
      {tabs.map((tab) => (
        <S_TabItem key={tab.id} active={location === tab.path} onClick={() => navigate(tab.path)}>
          {tab.title}
        </S_TabItem>
      ))}
    </S_TabContainer>
  );
}

export default Tabmenu;
