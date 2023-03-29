import styled from 'styled-components';
import { HandleDropDownClick } from './_createCategory';

const S_DropDownContainer = styled.ul`
  background-color: var(--gray100);

  width: 100%;
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
  margin-bottom: 10px;
  padding: 0.5rem 0;
  border-radius: 0 0 5px 5px;
  box-shadow: 0px 0px 30px -10px var(--gray200);

  z-index: 3;

  > li {
    padding: 0 1rem;
    font-size: 0.8rem;
    cursor: pointer;
    &.selected,
    &:hover {
      background-color: var(--gray200);
    }
  }
`;

interface DropDownProps {
  currentOption: number;
  options?: string[];
  handleComboBox: HandleDropDownClick;
  isFocused: boolean;
}

function DropDown({ currentOption, options, handleComboBox, isFocused }: DropDownProps) {
  return isFocused && options && options.length > 0 ? (
    <S_DropDownContainer>
      {options.map((item, idx) => {
        return (
          <li key={idx} className={idx === currentOption ? 'selected' : ''}>
            <button type='button' onClick={() => handleComboBox(item)}>
              {item}
            </button>
          </li>
        );
      })}
    </S_DropDownContainer>
  ) : null;
}

export default DropDown;
