import { useState } from 'react';
import CreateCategory from './_createCategory';
import CreateLocal from './_createLocal';
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
  const sampleDataType = {
    clubName: '',
    content: '',
    local: '',
    categoryName: '',
    isPrivate: false
  };

  const sampleData = {
    clubName: '배사모',
    content: '배드민턴이 좋은 사람들은 여기여기 모여라',
    local: '제주 서귀포시',
    categoryName: '배드민턴',
    isPrivate: false
  };

  // createTag로 내려보내야함
  const [tags, setTags] = useState<Array<string>>([]);
  const [categoryValue, setCategoryValue] = useState('');
  const [localValue, setLocalValue] = useState('');

  const [inputs, setInputs] = useState<clubType>(sampleDataType);
  const { clubName, content, local, categoryName, tagName, isPrivate } = inputs;

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  //   console.log(inputs);
  //   console.log('태그: ', tags);
  //   console.log('카테고리 종류: ', categoryValue);
  //   console.log('local :', localValue);

  // TODO 지역 select 두 개 다 입력해야만 그 다음 진행되게 방어
  // if (localValue === '' || localValue.includes('undefined'))

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
      <CreateCategory inputValue={categoryValue} setInputValue={setCategoryValue} />
      <CreateLocal inputValue={localValue} setInputValue={setLocalValue} />
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
