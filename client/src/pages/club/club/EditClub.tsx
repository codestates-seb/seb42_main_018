import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getFetch } from '../../../util/api';
import CreateLocal from './_createLocal';
import CreateTag from './_createTag';
import S_Container from '../../../components/UI/S_Container';
import { S_FormWrapper } from './CreateClub';
import { S_Title, S_Label, S_Description } from '../../../components/UI/S_Text';
import { S_Input } from '../../../components/UI/S_Input';
import { S_TextArea } from '../../../components/UI/S_TextArea';
import { S_Button } from '../../../components/UI/S_Button';
import { S_RadioWrapper } from './CreateClub';
import { ClubData } from '../../../types';

export interface EditClubDataType {
  clubName: string;
  content: string;
  local: string;
  tagName?: string[];
  isPrivate: boolean;
}

function EditClub() {
  const { id: clubId } = useParams();
  const [clubInfo, setClubInfo] = useState<ClubData>();

  const [inputs, setInputs] = useState<EditClubDataType>({
    clubName: '',
    content: '',
    local: '',
    tagName: [],
    isPrivate: false
  });

  const { categoryName, local: prevLocal } = clubInfo || {};

  const URL = `${process.env.REACT_APP_URL}/clubs/${clubId}`;
  useEffect(() => {
    const getClubInfo = async () => {
      const res = await getFetch(URL);
      setClubInfo(res.data);
    };
    getClubInfo();
  }, []);

  useEffect(() => {
    if (clubInfo) {
      setInputs({
        ...inputs,
        clubName: clubInfo.clubName,
        content: clubInfo.content,
        local: clubInfo.local,
        tagName: clubInfo.tagResponseDtos.map((tag) => tag.tagName),
        // TODO: secret 필드 바뀐거 확인하고  || false 삭제
        isPrivate: clubInfo.secret || false
      });
    }
  }, [clubInfo]);
  // console.log('서버에서 보내준 소모임 정보:', clubInfo);
  // console.log('input value:', inputs);

  // !TODO: profileImage 사진 어떻게 보낼지

  const { clubName, content, local, tagName, isPrivate } = inputs || {};

  const [tags, setTags] = useState<string[]>([]);
  const [categoryValue, setCategoryValue] = useState('');
  const [localValue, setLocalValue] = useState('');

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: name === 'isPrivate' ? value === 'true' : value });
  };

  const onSubmit = () => {
    console.log('제출');
    const URL = `${process.env.REACT_APP_URL}/clubs/${clubId}`;
  };

  return (
    <S_Container>
      <S_Title>소모임 정보 수정</S_Title>
      <S_FormWrapper>
        <form onSubmit={onSubmit}>
          <div>
            <label htmlFor='clubName'>
              <S_Label>소모임 이름 *</S_Label>
            </label>
            <S_Input
              id='clubName'
              name='clubName'
              type='text'
              maxLength={16}
              defaultValue={clubInfo?.clubName}
              value={clubName}
              onChange={onChange}
              width='96%'
            />
          </div>
          <div>
            <label htmlFor='content'>
              <S_Label>소모임 소개글 *</S_Label>
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

          <div>
            <label htmlFor='categoryName'>
              <S_Label>카테고리</S_Label>
            </label>
            <S_Description>소모임 종류는 처음 모임을 만든 다음에는 변경할 수 없어요.</S_Description>
            <S_Input
              id='categoryName'
              name='categoryName'
              type='text'
              defaultValue={clubInfo?.categoryName}
              disabled
              width='96%'
            />
          </div>
          {/* //TODO: <option selected> */}
          <CreateLocal prevData={prevLocal} inputValue={localValue} setInputValue={setLocalValue} />
          {/* //TODO: prevTags props로 보내기 */}
          <CreateTag tags={tags} setTags={setTags} />
          {/* 사진 등록 영역 */}
          <S_Label>사진 등록</S_Label>
          <fieldset className='isPrivate'>
            <div>
              <S_Label>공개여부 *</S_Label>
            </div>
            <S_RadioWrapper>
              <div className='partition'>
                <S_Input
                  type='radio'
                  id='public'
                  name='isPrivate'
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
                  name='isPrivate'
                  value='true'
                  onChange={onChange}
                />
                <label htmlFor='private'>비공개</label>
              </div>
            </S_RadioWrapper>
          </fieldset>
          <S_Button>정보수정 완료</S_Button>
        </form>
      </S_FormWrapper>
    </S_Container>
  );
}

export default EditClub;
