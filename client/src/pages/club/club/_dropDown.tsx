import styled from 'styled-components';
import { HandleDropDownClick } from './_createCategory';

const S_DropDownContainer = styled.ul`
  background-color: var(--white);

  /* TODO: 스타일링 코드 정돈 필요 */
  width: 145px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  list-style-type: none;
  margin-block-start: 0;
  margin-block-end: 0;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  padding-inline-start: 0px;
  margin-top: -1px;
  padding: 0.5rem 0;
  z-index: 3;

  > li {
    padding: 0 1rem;
    cursor: pointer;
    &.selected,
    &:hover {
      background-color: var(--gray200);
    }
  }
`;

interface DropDownProps {
  currentOption: number;
  options: Array<string>;
  handleComboBox: HandleDropDownClick;
}

function DropDown({ currentOption, options, handleComboBox }: DropDownProps) {
  return (
    <S_DropDownContainer>
      {options.map((item: string, idx: number) => {
        return (
          <li
            role='presentation'
            key={idx}
            className={idx === currentOption ? 'selected' : ''}
            onClick={() => handleComboBox(item)}
          >
            {item}
          </li>
        );
      })}
    </S_DropDownContainer>
  );
}

export default DropDown;
