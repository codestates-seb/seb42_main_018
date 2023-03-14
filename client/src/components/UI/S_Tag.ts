// 태그 컴포넌트

import styled from "styled-components";

export const S_Tag = styled.span`
  padding: 5px 7px;
  border-radius: 5px;
  color: var(--blue300);
  background-color: var(--blue100);
  font-size: 0.8rem;
  font-weight: 600;
  margin-right: 5px
`

export const S_TagSmall = styled(S_Tag)`
  padding: 4px 6px;
  border-radius: 3px;
  font-size: 0.7rem;
`