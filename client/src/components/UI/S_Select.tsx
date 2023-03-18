// select 컴포넌트
import styled from 'styled-components';

export const S_Select = styled.select<{ width?: string; height?: string }>`
  border: none;
  width: ${(props) => props.width || '40%'};
  height: ${(props) => props.height || '50%'};
  padding: 15px;
  margin: 0px 10px 10px 0px;
  border-radius: 5px;
  background: var(--gray100);
  color: var(--gray600);
`;

// interface S_SelectProps {
//   id?: string;
//   name?: string;
//   //! TODO: children에 맞는 타입 지정 못함: any 해결해야 함
//   children?: any;
//   // children?: React.PropsWithChildren<HTMLOptionElement>; //? children이 두 개 이상의 요소일 때
//   // children?: React.ReactNodeArray; //? 시/도 셀렉트 박스 선택해도 시/군/구에 필터링 데이터 안 들어옴
//   // children?: React.ReactElement[] | React.ReactNode; // 마찬가지로 2번째 박스 못 불러옴
//   onChange?: (e: React.ChangeEvent<HTMLSelectElement>) => void;
// }

// export function S_Select({ children, onChange }: S_SelectProps) {
//   return (
//     <StyledSelect onChange={onChange}>
//       {/* {children} */}
//       <option value={''}>옵션을 선택해주세요</option>
//       <option value={'선택1'}>선택 1</option>
//       <option value={'선택2'}>선택 2</option>
//       <option value={'선택3'}>선택 4</option>
//       <option value={'선택5'}>선택 4</option>
//     </StyledSelect>
//   );
// }
