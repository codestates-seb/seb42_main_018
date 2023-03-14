// textarea 컴포넌트

import styled from "styled-components";

export const S_TextArea = styled.textarea`
  border: none;
  height: 40px;
  padding: 13px;
  margin: 0px 10px 10px 0px;
  border-radius: 5px;
  background: var(--gray100);
  color: var(--gray600);

  :focus {
    outline: 1px solid var(--blue300);
  }
  
  ::placeholder {
    color: var(--gray300);
  }
`
