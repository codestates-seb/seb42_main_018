import { useLocation, useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';

const S_TabContainer = styled.div`
  // 탭메뉴 전체 박스
  display: flex;
  margin: -20px -20px 20px -20px;
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

function Tabmenu() {
  const navigate = useNavigate();
  const location = useLocation().pathname;
  const { id } = useParams();

  const tabs: Tab[] = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

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
