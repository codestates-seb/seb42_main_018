import { useState } from 'react';
import styled from 'styled-components';

const TagsInput = styled.div`
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

function CreateTag() {
  const [tags, setTags] = useState<Array<string>>([]);

  // TODO: 쉼표 -> 엔터키로 변경 후 slice 없애기
  //! TODO: event type 수정
  const addTags = (e: any) => {
    const newTag = e.target.value.slice(0, -1);
    if (!tags.includes(newTag) || newTag !== '') {
      setTags([...tags, newTag]);
      e.target.value = '';
    }
  };
  const removeTags = (indexToRemove: number): void => {
    setTags(tags.filter((el: string, idx: number): boolean => idx !== indexToRemove));
  };

  return (
    <>
      <TagsInput>
        <label htmlFor='tagName'>태그</label>
        <input
          id='tagName'
          className='tag-input'
          type='text'
          // TODO: onSubmit 작성 후 쉼표 -> 엔터키로 변경
          onKeyUp={(e) => {
            {
              if (e.key === ',') addTags(e);
            }
          }}
          placeholder='쉼표(,)를 누르면 태그가 추가됩니다.'
        />
        <ul id='tags'>
          {tags.map((tag: string, index: number) => (
            <li key={index} className='tag'>
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
      </TagsInput>
    </>
  );
}

export default CreateTag;
