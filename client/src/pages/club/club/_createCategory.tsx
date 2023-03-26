import { useState, useEffect, useRef } from 'react';
import { getFetch } from '../../../util/api';
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

interface CategoryListDataType {
  categoryName: string;
}

function CreateCategory({ inputValue, setInputValue }: CreateCategoryProps) {
  const [categories, setCategories] = useState<string[]>();
  const [isFocused, setIsFocused] = useState(false);

  useEffect(() => {
    const GET_URL = `${process.env.REACT_APP_URL}/categories`;
    const getCategoryList = async () => {
      const res = await getFetch(GET_URL);
      const categoryList: CategoryListDataType[] = res.data;
      setCategories(categoryList.map((el) => el.categoryName));
    };
    getCategoryList();
  }, []);

  // console.log(categories);

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
    setOptions(categories?.filter((item) => item.startsWith(e.target.value)));
  };

  const handleDropDownClick: HandleDropDownClick = (clickedOption) => {
    setInputValue(clickedOption);
    setOptions(categories?.filter((item) => item.startsWith(clickedOption)));
  };

  const handleKeyUp = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (hasText && options) {
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

  const handleInputFocus = () => {
    setIsFocused(true);
  };

  const handleInputBlur = () => {
    setTimeout(() => {
      setIsFocused(false);
    }, 150);
  };

  return (
    <div style={{ marginTop: '0.8rem' }}>
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
          onFocus={handleInputFocus}
          onBlur={handleInputBlur}
          ref={input}
          width='96%'
          autoComplete='off'
        />
      </>
      {hasText && (
        <DropDown
          options={options}
          handleComboBox={handleDropDownClick}
          currentOption={currentOption}
          isFocused={isFocused}
        />
      )}
    </div>
  );
}

export default CreateCategory;
