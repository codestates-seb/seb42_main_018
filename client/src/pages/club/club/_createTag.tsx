import { useState } from 'react';
import styled from 'styled-components';
import { S_Description } from '../../../components/UI/S_Text';
import { S_Label_mg_top } from './EditClub';
import { S_Input } from '../../../components/UI/S_Input';
import { S_Tag } from '../../../components/UI/S_Tag';

export const S_TagWrapper = styled.ul`
  display: flex;
  margin-bottom: 10px;
`;

interface CreateTagProps {
  tags: string[];
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
  const removeTags = (indexToRemove: number) => {
    if (tags !== undefined) {
      setTags(tags.filter((el, idx) => idx !== indexToRemove));
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  return (
    <div>
      <label htmlFor='tagList'>
        <S_Label_mg_top>태그</S_Label_mg_top>
      </label>
      <S_Description>최대 3개까지 입력할 수 있습니다.</S_Description>
      <S_Input
        id='tagList'
        name='tagList'
        type='text'
        width='96%'
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
      <S_TagWrapper>
        {tags &&
          tags.map((tag, index) => (
            <li
              role='presentation'
              key={tag}
              onClick={() => {
                removeTags(index);
              }}
              style={{ cursor: 'pointer' }}
            >
              <S_Tag>{tag}&nbsp;&times;</S_Tag>
            </li>
          ))}
      </S_TagWrapper>
    </div>
  );
}

export default CreateTag;
