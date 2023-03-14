import { useState, useEffect, useRef } from 'react';
import styled from 'styled-components';

const S_InputContainer = styled.div<{ hasText: boolean }>`
  /* styling code  */
`;

interface HandleDropDownClick {
  (option: string): void;
}

interface CreateCategoryProps {
  inputValue: string;
  setInputValue: React.Dispatch<React.SetStateAction<string>>;
}

function CreateCategory({ inputValue, setInputValue }: CreateCategoryProps) {
  // TODO: 서버 api get 요청으로 카테고리 배열 데이터 받아오기
  const categories: Array<string> = [
    '배드민턴',
    '축구',
    '풋살',
    '농구',
    '배구',
    '골프',
    '볼링',
    '테니스',
    '하키',
    '당구'
  ];

  //* hasText: input값 유무 확인
  //* options: input값을 포함하는 autocomplete 추천 항목 리스트 확인
  //* currentOption: 선택한 option을 index처럼 관리
  const [hasText, setHasText] = useState<boolean>(false);
  const [options, setOptions] = useState(categories);
  const [currentOption, setCurrentOption] = useState<number>(-1);
  const input = useRef(null);

  useEffect(() => {
    if (inputValue === '') {
      setHasText(false);
    }
  }, [inputValue]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
    setHasText(true);
    setOptions(categories.filter((item) => item.startsWith(e.target.value)));
  };

  const handleDropDownClick: HandleDropDownClick = (clickedOption) => {
    setInputValue(clickedOption);
    setOptions(categories.filter((item) => item.startsWith(clickedOption)));
  };

  const handleKeyUp = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (hasText) {
      if (e.key === 'ArrowUp' && currentOption > -1) {
        setCurrentOption((cur) => cur - 1);
      } else if (e.key === 'ArrowDown' && currentOption < options.length - 1) {
        setCurrentOption((cur) => cur + 1);
      } else if (e.key === 'Enter') {
        handleDropDownClick(options[currentOption]);
        setCurrentOption(-1);
      }
    }
  };

  return (
    <div>
      <S_InputContainer hasText={hasText}>
        <label htmlFor='categoryName'>어떤 소모임을 만드실 건가요? *</label>
        <p>소모임 종류는 한번 입력하시면 변경할 수 없습니다.</p>
        <input
          id='categoryName'
          name='categoryName'
          type='text'
          value={inputValue}
          onKeyUp={handleKeyUp}
          onChange={handleInputChange}
          ref={input}
        />
      </S_InputContainer>
      {hasText && (
        <DropDown
          options={options}
          handleComboBox={handleDropDownClick}
          currentOption={currentOption}
        />
      )}
    </div>
  );
}

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

export const DropDown = ({ currentOption, options, handleComboBox }: DropDownProps) => {
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
};

export default CreateCategory;
