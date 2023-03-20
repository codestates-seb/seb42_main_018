import { useState, useRef } from 'react';
import DropDown from './_dropDown';
import { S_Input } from '../../../components/UI/S_Input';
import { S_Label, S_Description } from '../../../components/UI/S_Text';

export interface HandleDropDownClick {
  (option: string): void;
}

export interface CreateCategoryProps {
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

  //* options: input값을 포함하는 autocomplete 추천 항목 리스트 확인
  //* currentOption: 선택한 option을 index로 관리
  //* hasText: input값 유무 확인
  const INITIAL_OPTION_INDEX = -1;

  const [options, setOptions] = useState(categories);
  const [currentOption, setCurrentOption] = useState(INITIAL_OPTION_INDEX);
  const input = useRef(null);
  const hasText = !!inputValue;

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
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
      <>
        <label htmlFor='categoryName'>
          <S_Label>어떤 소모임을 만드실 건가요? *</S_Label>
        </label>
        <S_Description>소모임 종류는 한번 입력하시면 변경할 수 없습니다.</S_Description>
        <S_Input
          id='categoryName'
          name='categoryName'
          type='text'
          value={inputValue}
          onKeyUp={handleKeyUp}
          onChange={handleInputChange}
          ref={input}
        />
      </>
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

export default CreateCategory;
