// select 컴포넌트
import styled from 'styled-components';
import { cacheMatchIgnoreParams } from 'workbox-core/_private';

const StyledSelect = styled.select`
  border: none;
  width: 40%;
  height: 50%;
  padding: 15px;
  margin: 0px 10px 10px 0px;
  border-radius: 5px;
  background: var(--gray100);
  color: var(--gray600);
`;

interface S_SelectProps {
  id?: string;
  name?: string;
  //! any 타입 해결 : children이 두 개 이상의 요소일 때
  // children?: React.PropsWithChildren<HTMLOptionElement>;
  children?: React.ReactNodeArray;
  onChange?: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}

export function S_Select({ children, onChange }: S_SelectProps) {
  // console.log(children);
  // console.log(children[1]);
  // console.log(children[1][3]);
  return (
    <StyledSelect onChange={onChange}>
      {children}
      {/* <option value={''}>옵션을 선택해주세요</option>
      <option value={'선택1'}>선택 1</option>
      <option value={'선택2'}>선택 2</option>
      <option value={'선택3'}>선택 4</option>
      <option value={'선택5'}>선택 4</option> */}
    </StyledSelect>
  );
}
