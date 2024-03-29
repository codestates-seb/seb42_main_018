import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import FormData from 'form-data';
import { getFetch, patchFetch } from '../../../util/api';
import getGlobalState from '../../../util/authorization/getGlobalState';
import CreateLocal from '../../../components/club/club/_createLocal';
import CreateTag from '../../../components/club/club/_createTag';
import S_Container from '../../../components/UI/S_Container';
import { S_Title, S_Label, S_Description } from '../../../components/UI/S_Text';
import { S_Input } from '../../../components/UI/S_Input';
import { S_TextArea } from '../../../components/UI/S_TextArea';
import { S_Button, S_EditButton } from '../../../components/UI/S_Button';
import { S_FormWrapper, S_RadioWrapper } from './CreateClub';
import { S_ImgBox } from '../../mypage/EditProfile';
import { ClubData } from '../../../types';

export interface EditClubDataType {
  clubName: string;
  content: string;
  local: string;
  tagList?: string[];
  clubPrivateStatus?: 'PUBLIC' | 'SECRET';
}

export interface ImageFileType {
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
  const { tokens } = getGlobalState();
  const { id: clubId } = useParams();
  const URL = `${process.env.REACT_APP_URL}/clubs/${clubId}`;

  const [clubInfo, setClubInfo] = useState<ClubData>();
  const {
    categoryName,
    clubImage,
    local: prevLocal,
    tagList: prevTagList,
    clubPrivateStatus: prevClubPrivateStatus
  } = clubInfo || {};

  const [inputs, setInputs] = useState<EditClubDataType>({
    clubName: '',
    content: '',
    local: prevLocal || '',
    tagList: prevTagList,
    clubPrivateStatus: prevClubPrivateStatus
  });
  const { clubName, content, clubPrivateStatus } = inputs || {};

  useEffect(() => {
    const getClubInfo = async () => {
      const res = await getFetch(URL);
      const clubInfo: ClubData = res.data;
      setClubInfo(clubInfo);

      // * 기존 소모임 정보를 input 초기값으로 세팅
      const { local: prevLocal, tagList: prevTagList } = clubInfo || {};
      setTags(prevTagList);
      setLocalValue(prevLocal);

      if (clubInfo) {
        setInputs({
          ...inputs,
          clubName: clubInfo.clubName,
          content: clubInfo.content,
          local: prevLocal,
          tagList: prevTagList,
          clubPrivateStatus: clubInfo.clubPrivateStatus
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
    setInputs({ ...inputs, [name]: value });
  };

  const uploadImg = () => {
    const inputname = document.getElementById('uploadImg');
    inputname?.click();
  };

  // 파일로 가져온 이미지 브라우저에서 미리보기
  const [imgFile, setImgFile] = useState(clubImage); // 사용자가 선택한 사진을 화면에서 바로 보여주기 위한 string
  const [clubImageFile, setClubImageFile] = useState<ImageFileType>(); // 서버에 form-data로 전송할 파일 객체
  const handleFileUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file) {
      const reader = new FileReader();
      reader.addEventListener('load', () => {
        setImgFile(reader.result as string);
      });
      reader.readAsDataURL(file);

      setClubImageFile(file);
    }
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    let temp = false,
      localData = '';
    if (localValue.includes('undefined') && prevLocal) temp = true;

    if (!clubName || !content || !localValue) {
      alert('*가 표시된 항목은 필수 입력란입니다.');
      return;
    }

    // local 변화 없을 때 '서울 undefined'로 값이 들어가는 것을 방어하기 위한 임시 코드
    if (temp && prevLocal) localData = prevLocal;
    else localData = localValue;

    const formData: FormData = new FormData();
    formData.append('clubName', clubName);
    formData.append('content', content);
    formData.append('local', localData);
    formData.append('tagList', tags);
    formData.append('clubPrivateStatus', clubPrivateStatus);

    if (clubImageFile) formData.append('clubImage', clubImageFile);
    else formData.append('clubImage', null);

    const contentType = `multipart/form-data; boundary=${(formData as any)._boundary}`;
    const res = await patchFetch(URL, formData, tokens, false, contentType);
    if (res) navigate(`/club/${clubId}`);
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
          {localValue && <CreateLocal inputValue={localValue} setInputValue={setLocalValue} />}
          <CreateTag tags={tags} setTags={setTags} />

          <div>
            <S_Label_mg_top>사진 등록</S_Label_mg_top>
            <S_ImageBox>
              <img id='previewimg' src={imgFile ? imgFile : clubImage} alt='소모임 소개 사진' />
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

          <fieldset className='clubPrivateStatus'>
            <div>
              <S_Label_mg_top>공개여부 *</S_Label_mg_top>
            </div>
            <S_RadioWrapper>
              <div className='partition'>
                {clubPrivateStatus === 'PUBLIC' ? (
                  <S_Input
                    type='radio'
                    id='private'
                    name='clubPrivateStatus'
                    value='PUBLIC'
                    onChange={onChange}
                    defaultChecked
                  />
                ) : (
                  <S_Input
                    type='radio'
                    id='private'
                    name='clubPrivateStatus'
                    value='PUBLIC'
                    onChange={onChange}
                  />
                )}
                <label htmlFor='public'>공개</label>
              </div>
              <div className='partition'>
                {clubPrivateStatus === 'SECRET' ? (
                  <S_Input
                    type='radio'
                    id='private'
                    name='clubPrivateStatus'
                    value='SECRET'
                    onChange={onChange}
                  />
                ) : (
                  <S_Input
                    type='radio'
                    id='private'
                    name='clubPrivateStatus'
                    value='SECRET'
                    onChange={onChange}
                    defaultChecked
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
