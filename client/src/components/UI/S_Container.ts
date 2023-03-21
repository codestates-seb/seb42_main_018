// 헤더 아래 모든 페이지의 배경색과 패딩 스타일입니다.
import styled from 'styled-components';

const S_Container = styled.div`
  display: flex;
  flex-direction: column;
  background-color: var(--white);
  margin-top: 50px;
  padding: 20px;
  min-height: calc(100vh - 100px);
`;

export default S_Container;
