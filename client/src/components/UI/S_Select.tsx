// select 컴포넌트

import styled from "styled-components";

const StyledSelect= styled.select`
  border: none;
  height: 30px;
  padding: 15px;
  margin: 0px 10px 10px 0px;
  border-radius: 5px;
  background: var(--gray100);
  color: var(--gray600);
`

export function S_Select() {
  return (
    <StyledSelect name="name" id="id">
      <option value={""}>옵션을 선택해주세요</option>
      <option value={"선택1"}>선택 1</option>
      <option value={"선택2"}>선택 2</option>
      <option value={"선택3"}>선택 4</option>
      <option value={"선택5"}>선택 4</option>
    </StyledSelect>
  )
}