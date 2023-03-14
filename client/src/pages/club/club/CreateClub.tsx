import { useState } from 'react';
import CreateCategory from './_createCategory';
import CreateTag from './_createTag';

export interface clubType {
  clubName: string;
  content: string;
  local?: string;
  categoryName: string;
  tagName?: Array<string>;
  isPrivate: boolean | string;
}

function CreateClub() {
  const sampleData = {
    clubName: '',
    content: '',
    local: '',
    categoryName: '',
    isPrivate: false
  };

  // createTag로 내려보내야함
  const [tags, setTags] = useState<Array<string>>([]);
  const [categoryInputValue, setCategoryInputValue] = useState<string>('');

  const [inputs, setInputs] = useState<clubType>(sampleData);
  const { clubName, content, local, categoryName, tagName, isPrivate } = inputs;

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    console.log(e.target);
    setInputs({ ...inputs, [name]: value });
  };

  console.log(inputs);
  //   console.log('태그: ', tags);
  //   console.log('카테고리 종류: ', categoryInputValue);
  // TODO :  local 두 개 남음
  // TODO: isPrivate 서버에 보내기 전에 Boolean 처리

  return (
    <form>
      <h2>신규 소모임 만들기</h2>
      <div>
        <label htmlFor='clubName'>소모임 이름 *</label>
        <input id='clubName' name='clubName' type='text' value={clubName} onChange={onChange} />
      </div>
      <div>
        <label htmlFor='content'>소모임 소개글 *</label>
        <textarea
          id='content'
          name='content'
          placeholder='소모임 소개와 함께 가입조건, 모임장소 및 날짜를 입력해 보세요.'
          value={content}
          onChange={onChange}
        />
      </div>
      <CreateCategory inputValue={categoryInputValue} setInputValue={setCategoryInputValue} />
      <div>
        {/* TODO: 지역 두 단계로 나눠서 입력받을 수 있는 api 찾아봐야함 */}
        <label htmlFor='local'>지역 선택 *</label>
        <select id='local1'>
          <option>선택</option>
          <option value='11'>서울특별시</option>
        </select>
        <select id='local2'>
          <option>선택</option>
        </select>
      </div>
      <CreateTag tags={tags} setTags={setTags} />
      <fieldset>
        <legend>공개여부 선택 *</legend>
        <label htmlFor='public'>공개</label>
        <input
          type='radio'
          id='public'
          name='isPrivate'
          value='false'
          onChange={onChange}
          defaultChecked
        />
        <label htmlFor='private'>비공개</label>
        <input type='radio' id='private' name='isPrivate' value='true' onChange={onChange} />
      </fieldset>
      <button>소모임 만들기</button>
    </form>
  );
}

export default CreateClub;
