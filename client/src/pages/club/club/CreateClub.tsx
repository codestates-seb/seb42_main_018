import { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { postFetch } from '../../../util/api';
import CreateCategory from './_createCategory';
import CreateLocal from './_createLocal';
import CreateTag from './_createTag';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import { S_TextArea } from '../../../components/UI/S_TextArea';
import { S_Title } from '../../../components/UI/S_Text';
import { S_Label_mg_top } from './EditClub';
import { S_Button } from '../../../components/UI/S_Button';
import { EditClubDataType } from './EditClub';

export const S_FormWrapper = styled.div`
  margin-top: 1.2rem;
  display: flex;
  flex-direction: column;

  & .isSecret {
    display: flex;
    justify-content: space-between;
  }

  & .submit-btn-box {
    margin-top: 1.2rem;
  }
`;

export const S_RadioWrapper = styled.div`
  width: 40%;
  display: flex;
  justify-content: space-around;

  & input {
    transform: translateY(10%);
  }
  & input:focus {
    outline: 0;
  }

  & .partition {
    display: flex;
    align-items: center;
  }

  & label {
    display: inline-flex;
    width: 500%;
  }
`;

export interface CreateClubDataType extends EditClubDataType {
  categoryName: string;
}

function CreateClub() {
  const navigate = useNavigate();
  const [tags, setTags] = useState<string[]>([]);
  const [categoryValue, setCategoryValue] = useState('');
  const [localValue, setLocalValue] = useState('');

  // * textarea 높이 자동 조절 관련
  // const textareaRef = useRef<HTMLTextAreaElement>(null);

  // useEffect(() => {
  //   const textarea = textareaRef.current;

  //   if (textarea) {
  //     textarea.style.height = 'auto';
  //     textarea.style.height = `${textarea.scrollHeight}px`;
  //   }
  // }, [textareaRef]);

  const [inputs, setInputs] = useState<CreateClubDataType>({
    clubName: '',
    content: '',
    local: '',
    categoryName: '',
    isSecret: false
  });
  const { clubName, content, isSecret } = inputs;
  console.log(inputs);

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: name === 'isSecret' ? value === 'true' : value });
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
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

    const newClubData: CreateClubDataType = {
      ...inputs,
      categoryName: categoryValue,
      local: localValue,
      tagName: tags,
      isSecret
    };

    // console.log(newClubData);

    // TODO: 서버 post 요청 로직 작성
    const POST_URL = `${process.env.REACT_APP_URL}/clubs`;
    const res = await postFetch(POST_URL, newClubData);
    if (res) navigate(res.headers.location);
  };

  return (
    <S_Container>
      <S_Title>신규 소모임 만들기</S_Title>
      <S_FormWrapper>
        <form onSubmit={onSubmit}>
          <div>
            <label htmlFor='clubName'>
              <S_Label_mg_top>소모임 이름 *</S_Label_mg_top>
            </label>
            <S_Input
              id='clubName'
              name='clubName'
              type='text'
              maxLength={16}
              value={clubName}
              onChange={onChange}
              width='96%'
            />
          </div>
          <div>
            <label htmlFor='content'>
              <S_Label_mg_top>소모임 소개글 *</S_Label_mg_top>
            </label>
            <S_TextArea
              id='content'
              name='content'
              maxLength={255}
              placeholder='소모임 소개와 함께 가입조건, 모임장소 및 날짜를 입력해 보세요. (글자수 제한 255자)'
              value={content}
              onChange={onChange}
              // ref={textareaRef}
            />
          </div>
          <CreateCategory inputValue={categoryValue} setInputValue={setCategoryValue} />
          <CreateLocal inputValue={localValue} setInputValue={setLocalValue} />
          <CreateTag tags={tags} setTags={setTags} />
          <fieldset className='isSecret'>
            <div>
              <S_Label_mg_top>공개여부 *</S_Label_mg_top>
            </div>
            <S_RadioWrapper>
              <div className='partition'>
                <S_Input
                  type='radio'
                  id='public'
                  name='isSecret'
                  value='false'
                  onChange={onChange}
                  defaultChecked
                />
                <label htmlFor='public'>공개</label>
              </div>
              <div className='partition'>
                <S_Input
                  type='radio'
                  id='private'
                  name='isSecret'
                  value='true'
                  onChange={onChange}
                />
                <label htmlFor='private'>비공개</label>
              </div>
            </S_RadioWrapper>
          </fieldset>
          <div className='submit-btn-box'>
            <S_Button>소모임 만들기</S_Button>
          </div>
        </form>
      </S_FormWrapper>
    </S_Container>
  );
}

export default CreateClub;
