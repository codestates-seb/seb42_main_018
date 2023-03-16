import { useState } from 'react';
import styled from 'styled-components';
import { S_Label, S_Description } from '../../../components/UI/S_Text';
import { S_Input } from '../../../components/UI/S_Input';

// ! 태그 공통 컴포넌트로 수정해야 함
const S_TagsInput = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex-wrap: wrap;

  > input {
    flex: 1;
    border: none;
    height: 40px;
    font-size: 14px;
    :focus {
      outline: transparent;
    }
  }

  > ul {
    display: flex;
    flex-wrap: wrap;
    margin: 8px 0 0 0;

    > .tag {
      width: auto;
      height: 25px;
      margin: 0 8px 8px 0;
      padding: 0 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 6px;
      font-size: 14px;
      font-weight: 600;
      background: var(--blue100);
      color: var(--blue300);

      > .tag-close-icon {
        width: 16px;
        height: 16px;
        margin-left: 8px;

        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 18px;
        font-weight: 400;
        line-height: 16px;
        text-align: center;

        border-radius: 50%;
        background: var(--blue100);
        /* 컬러 팔레트에 없는 색상 */
        color: #7dabff;
        cursor: pointer;
      }
    }
  }
`;

interface CreateTagProps {
  tags: string[] | undefined;
  setTags: React.Dispatch<React.SetStateAction<string[]>>;
}

function CreateTag({ tags, setTags }: CreateTagProps) {
  const [inputValue, setInputValue] = useState('');

  // ! 끝에 스페이스 방어 못하고 있음
  const addTags = () => {
    if (tags !== undefined) {
      if (tags.includes(inputValue) || inputValue === '' || tags.length >= 3) {
        setInputValue('');
        return;
      }
      setTags([...tags, inputValue]);
      setInputValue('');
    }
  };
  const removeTags = (indexToRemove: number): void => {
    if (tags !== undefined) {
      setTags(tags.filter((el: string, idx: number): boolean => idx !== indexToRemove));
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  return (
    <>
      <S_TagsInput>
        <label htmlFor='tagName'>
          <S_Label>태그</S_Label>
        </label>
        <S_Description>최대 3개까지 입력할 수 있습니다.</S_Description>
        {/* //! TODO: 스타일 확인 */}
        <S_Input
          id='tagName'
          name='tagName'
          className='tag-input'
          type='text'
          value={inputValue}
          onChange={handleInputChange}
          onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) => {
            if (e.key === 'Enter') {
              // 한글 입력시 마지막 글자 중복 입력 방지
              if (e.nativeEvent.isComposing) return;

              e.preventDefault();
              addTags();
            }
          }}
          placeholder='엔터 키를 누르면 태그가 추가됩니다.'
        />
        <ul id='tags'>
          {tags &&
            tags.map((tag: string, index: number) => (
              <li key={tag} className='tag'>
                <span className='tag-title'>{tag}</span>
                <button
                  className='tag-close-icon'
                  onClick={() => {
                    removeTags(index);
                  }}
                >
                  &times;
                </button>
              </li>
            ))}
        </ul>
      </S_TagsInput>
    </>
  );
}

export default CreateTag;
