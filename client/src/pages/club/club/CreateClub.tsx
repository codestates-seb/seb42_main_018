import { useState } from 'react';
import CreateCategory from './_createCategory';
import CreateLocal from './_createLocal';
import CreateTag from './_createTag';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import { S_TextArea } from '../../../components/UI/S_TextArea';
import { S_Button } from '../../../components/UI/S_Button';
import { S_Title, S_Label, S_Text, S_Description } from '../../../components/UI/S_Text';
import { S_Tag } from '../../../components/UI/S_Tag';

export interface clubType {
  clubName: string;
  content: string;
  local: string;
  categoryName: string;
  tagName?: string[];
  isPrivate: boolean;
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
    setInputs({ ...inputs, [name]: name === 'isPrivate' ? value === 'true' : value });
  };

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // TODO: local 입력값은 local component에서 자체 해결하는 방안으로 리팩토링
    if (
      !clubName ||
      !content ||
      !categoryValue ||
      !localValue ||
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
      isPrivate
    };

    if (tags.length === 0) {
      delete newData.tagName;
    }

    // console.log(newData);

    // TODO: 서버 post 요청 로직 작성
  };

  return (
    <S_Container>
      <form onSubmit={onSubmit}>
        <S_Title>신규 소모임 만들기</S_Title>
        <div>
          <label htmlFor='clubName'>
            <S_Label>소모임 이름 *</S_Label>
          </label>
          <S_Input
            id='clubName'
            name='clubName'
            type='text'
            maxLength={10}
            value={clubName}
            onChange={onChange}
          />
        </div>
        <div>
          <label htmlFor='content'>
            <S_Label>소모임 소개글 *</S_Label>
          </label>
          <S_TextArea
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
          <legend>
            <S_Label>공개여부 선택 *</S_Label>
          </legend>
          <label htmlFor='public'>공개</label>
          <S_Input
            type='radio'
            id='public'
            name='isPrivate'
            value='false'
            onChange={onChange}
            defaultChecked
          />
          <label htmlFor='private'>비공개</label>
          <S_Input type='radio' id='private' name='isPrivate' value='true' onChange={onChange} />
        </fieldset>
        <S_Button>소모임 만들기</S_Button>
      </form>
    </S_Container>
  );
}

export default CreateClub;
