import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getFetch, patchFetch } from '../../../util/api';
import CreateLocal from './_createLocal';
import CreateTag from './_createTag';
import S_Container from '../../../components/UI/S_Container';
import { S_FormWrapper } from './CreateClub';
import { S_Title, S_Label, S_Description } from '../../../components/UI/S_Text';
import { S_Input } from '../../../components/UI/S_Input';
import { S_TextArea } from '../../../components/UI/S_TextArea';
import { S_Button, S_EditButton } from '../../../components/UI/S_Button';
import { S_RadioWrapper } from './CreateClub';
import { S_ImgBox } from '../../mypage/EditProfile';
import { ClubData } from '../../../types';

export interface EditClubDataType {
  clubName: string;
  content: string;
  local: string;
  tagName?: string[];
  isPrivate: boolean;
}

interface ImgFileType {
  lastModified: number;
  lastModifiedDate?: object;
  name: string;
  size: number;
  type: string;
  webkitRelativePath: string;
}

export const S_Label_mg_top = styled(S_Label)`
  margin-top: 1rem;
`;

const S_ImageBox = styled(S_ImgBox)`
  & > img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 10px;
    margin-right: 10px;
    background-color: var(--gray200);
  }
`;

function EditClub() {
  const navigate = useNavigate();
  const { id: clubId } = useParams();
  const URL = `${process.env.REACT_APP_URL}/clubs/${clubId}`;

  const [clubInfo, setClubInfo] = useState<ClubData>();
  const [inputs, setInputs] = useState<EditClubDataType>({
    clubName: '',
    content: '',
    local: '',
    tagName: [],
    isPrivate: false
  });
  const { categoryName, clubImageUrl, local: prevLocal, secret } = clubInfo || {};
  const { clubName, content } = inputs || {};

  useEffect(() => {
    const getClubInfo = async () => {
      const res = await getFetch(URL);
      const clubInfo: ClubData = res.data;
      setClubInfo(clubInfo);

      // * 기존 소모임 정보를 input 초기값으로 세팅
      const { local: prevLocal, tagResponseDtos } = clubInfo || {};
      const prevTags = tagResponseDtos?.map((tag) => tag.tagName) || [];
      setTags(prevTags);
      setLocalValue(prevLocal);

      if (clubInfo) {
        setInputs({
          ...inputs,
          clubName: clubInfo.clubName,
          content: clubInfo.content,
          local: clubInfo.local,
          tagName: clubInfo.tagResponseDtos.map((tag) => tag.tagName),

          // TODO: types > index.ts에서 secret 필드 ? 빠진거 확인하고 || false 삭제
          // !BUG : 비공개 소모임(secret: true) 생성해도 서버에서 전부 secret: false 로 처리되고 있음
          isPrivate: clubInfo.secret || false
        });
      }
    };
    getClubInfo();
  }, []);

  const [tags, setTags] = useState<string[]>([]);
  const [localValue, setLocalValue] = useState('');

  const onChange = (
    e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: name === 'isPrivate' ? value === 'true' : value });
  };

  // !TODO: profileImage patch 요청 어떻게 보낼지 BE와 방식 논의 필요
  // 버튼 클릭시 파일첨부(input#file) 실행시켜주는 함수. 못생긴 파일첨부 input은 안녕!
  const uploadImg = () => {
    const inputname = document.getElementById('uploadImg');
    inputname?.click();
  };

  // 파일로 가져온 이미지 미리보기
  const [imgFile, setImgFile] = useState(clubImageUrl);
  // const [imgFile, setImgFile] = useState<ImgFileType>();
  const handleFileUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    console.log(file);

    if (file) {
      const reader = new FileReader();
      reader.addEventListener('load', () => {
        setImgFile(reader.result as string);
      });
      // setImgFile(file);
      reader.readAsDataURL(file);
    }
  };

  console.log(typeof imgFile); // string

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const URL = `${process.env.REACT_APP_URL}/clubs/${clubId}`;
    const updateData = {
      ...inputs,
      tagName: tags,
      local: localValue,
      // * 이미지 파일 string으로 추가
      clubImageUrl: imgFile
    };

    console.log(updateData);
    // const res = await patchFetch(URL, updateData);
    // if (res) navigate(`/club/${clubId}`);
  };

  return (
    <S_Container>
      <S_Title>소모임 정보 수정</S_Title>
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

          <div>
            <label htmlFor='categoryName'>
              <S_Label_mg_top>카테고리</S_Label_mg_top>
            </label>
            <S_Description>소모임 종류는 처음 모임을 만든 다음에는 변경할 수 없어요.</S_Description>
            <S_Input
              id='categoryName'
              name='categoryName'
              type='text'
              defaultValue={categoryName}
              disabled
              width='96%'
            />
          </div>
          <CreateLocal prevData={prevLocal} inputValue={localValue} setInputValue={setLocalValue} />
          <CreateTag tags={tags} setTags={setTags} />

          {/* //TODO: 사진 등록 영역 */}
          <div>
            <S_Label_mg_top>사진 등록</S_Label_mg_top>
            <S_ImageBox>
              <img id='previewimg' src={imgFile ? imgFile : clubImageUrl} alt='소모임 소개 사진' />
              <label htmlFor='file'>
                <S_EditButton type='button' onClick={uploadImg}>
                  변경
                </S_EditButton>
                <input
                  id='uploadImg'
                  type='file'
                  accept='image/*'
                  onChange={handleFileUpload}
                  hidden
                />
              </label>
            </S_ImageBox>
          </div>

          <fieldset className='isPrivate'>
            <div>
              <S_Label_mg_top>공개여부 *</S_Label_mg_top>
            </div>
            <S_RadioWrapper>
              <div className='partition'>
                {secret ? (
                  <S_Input
                    type='radio'
                    id='private'
                    name='isPrivate'
                    value='true'
                    onChange={onChange}
                  />
                ) : (
                  <S_Input
                    type='radio'
                    id='private'
                    name='isPrivate'
                    value='true'
                    onChange={onChange}
                    defaultChecked
                  />
                )}
                <label htmlFor='public'>공개</label>
              </div>
              <div className='partition'>
                {secret ? (
                  <S_Input
                    type='radio'
                    id='private'
                    name='isPrivate'
                    value='true'
                    onChange={onChange}
                    defaultChecked
                  />
                ) : (
                  <S_Input
                    type='radio'
                    id='private'
                    name='isPrivate'
                    value='true'
                    onChange={onChange}
                  />
                )}
                <label htmlFor='private'>비공개</label>
              </div>
            </S_RadioWrapper>
          </fieldset>
          <div className='submit-btn-box'>
            <S_Button>정보수정 완료</S_Button>
          </div>
        </form>
      </S_FormWrapper>
    </S_Container>
  );
}

export default EditClub;
