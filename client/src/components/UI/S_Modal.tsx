// 액션을 수행한 뒤 뜨는 확인창, 모달 컴포넌트
// 작업 진행중

import styled from 'styled-components';
import { S_Label } from './S_Text';

export const ModalBackdrop = styled.div`
  // * 화면 전체 차지
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.35);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
`;

export const ModalContainer = styled.div`
  width: 300px;
  padding: 50px 10px;
  border-radius: 10px;
  background-color: var(--white);
  text-align: center;
  align-items: center;
`;

const ButtonContainer = styled.div`
  display: flex;
`;
const Button = styled.button`
  width: auto;
  height: 30px;
  margin: 0px 5px;
  background-color: var(--blue300);
`;

function S_Modal() {
  return (
    <ModalContainer>
      <S_Label>durldp </S_Label>
      <ButtonContainer></ButtonContainer>
    </ModalContainer>
  );
}

export default S_Modal;
