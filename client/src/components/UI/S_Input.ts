// input 컴포넌트
// <S_Input type="number" />
// <S_Input type="date" />
// <S_Input type="time"/>

import styled from 'styled-components';

export const S_Input = styled.input<{ width?: string }>`
  border: none;
  width: ${(props) => props.width || 'inherit'};
  height: 40px;
  padding: 0px 10px;
  margin: 0px 10px 10px 0px;
  border-radius: 5px;
  background: var(--gray100);
  color: var(--gray600);

  :focus {
    outline: 1px solid var(--blue300);
  }

  ::placeholder,
  :disabled {
    color: var(--gray300);
  }
`;
