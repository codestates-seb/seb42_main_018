import { useState } from 'react';
import CreateCategory from './_createCategory';
import CreateLocal from './_createLocal';
import CreateTag from './_createTag';

export interface clubType {
  clubName: string;
  content: string;
  local: string;
  categoryName: string;
  tagName?: Array<string>;
  isPrivate: boolean | string;
}

function CreateClub() {
  //   const sampleData = {
  //     clubName: '배사모',
  //     content: '배드민턴이 좋은 사람들은 여기여기 모여라',
  //     local: '제주 서귀포시',
  //     categoryName: '배드민턴',
  //     isPrivate: false
  //   };

  const [tags, setTags] = useState<Array<string>>([]);
  const [categoryValue, setCategoryValue] = useState('');
  const [localValue, setLocalValue] = useState('');

  const [inputs, setInputs] = useState<clubType>({
    clubName: '',
    content: '',
    local: '',
    categoryName: '',
    tagName: [],
    isPrivate: false
  });
  const { clubName, content, isPrivate } = inputs;

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (
      clubName === '' ||
      content === '' ||
      categoryValue === '' ||
      localValue === '' ||
      localValue.includes('undefined')
    ) {
      // TODO: 추후 모달로 변경
      alert('*가 표시된 항목은 필수 입력란입니다.');
      return;
    }

    const newData: clubType = {
      ...inputs,
      categoryName: categoryValue,
      local: localValue,
      tagName: tags,
      isPrivate: isPrivate === 'true' ? true : false
    };

    if (tags.length === 0) {
      delete newData.tagName;
    }

    // console.log(newData);

    // TODO: 서버 post 요청 로직 작성
  };

  return (
    <form onSubmit={onSubmit}>
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
