import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Tabmenu from '../../components/TabMenu';
import { S_Button, S_EditButton } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Label, S_Title } from '../../components/UI/S_Text';
import getGlobalState from '../../util/authorization/getGlobalState';
import InputNickname from '../../components/login/_inputNickname';
import { patchFetch } from '../../util/api';
import { ImageFileType } from '../club/club/EditClub';
import FormData from 'form-data';

const S_EditBox = styled.div`
  margin-top: 50px;
  h1 {
    margin-bottom: 30px;
  }
  .profileImgBox {
    margin-bottom: 30px;
    img {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 10px;
      margin-right: 10px;
      background-color: var(--gray200);
    }
  }
  .nicknamebox {
    margin-bottom: 30px;
  }
`;

export const S_ImgBox = styled.div`
  // 유저 프로필이미지 설정
  display: flex;
  align-items: center;
`;

function EditProfile() {
  // 상단탭
  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];

  const navigate = useNavigate();
  const { userInfo, tokens } = getGlobalState();

  // 버튼 클릭시 파일첨부(input=file 태그) 실행시켜주는 함수. 못생긴 파일첨부 input은 안녕!
  const uploadImg = () => {
    const inputname = document.getElementById('uploadImg');
    inputname?.click();
  };

  // 파일로 가져온 이미지 미리보기
  const [imgFile, setImgFile] = useState(userInfo.profileImage);
  const [profileImageFile, setProfileImageFile] = useState<ImageFileType>(); // 서버에 form-data로 전송할 파일 객체

  function handleFileUpload(event: React.ChangeEvent<HTMLInputElement>) {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.addEventListener('load', () => {
        setImgFile(reader.result as string);
      });
      reader.readAsDataURL(file);
      setProfileImageFile(file);
    }
  }
  console.log(profileImageFile);

  // 닉네임 수정 관련
  const [inputs, setInputs] = useState({
    profileImageId: userInfo.profileImage,
    nickName: userInfo.nickName
  });
  const { nickName } = inputs;
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };
  console.log(inputs);

  // 회원정보 수정 버튼 클릭시 실행될 함수
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // TODO : 패치 데이터 날리기

    if (profileImageFile) {
      const formData: FormData = new FormData();
      formData.append('nickname', inputs.nickName);
      formData.append('profileImage', profileImageFile); // 키네임 확인 필요

      console.log(formData); // 빈 객체로 보임
      const formDataEntries = formData as unknown as Array<[string, unknown]>;

      console.log(Array.from(formDataEntries)); // formData에 담긴 key-value pair 확인 가능
      const contentType = `multipart/form-data; boundary=${(formData as any)._boundary}`;
      // `${process.env.REACT_APP_URL}/users/${userInfo.userId}`
      const res = await patchFetch(
        `${process.env.REACT_APP_URL}/users/${userInfo.userId}`,
        formData,
        tokens,
        contentType
      );
      console.log(res);
      if (res) alert('수정이 완료되었습니다!'); // TODO : 모달로 변경
    }
  };

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_EditBox>
        <S_Title>현재 프로필 수정</S_Title>
        <form onSubmit={onSubmit}>
          <div className='profileImgBox'>
            <S_Label>프로필사진</S_Label>
            <S_ImgBox>
              <img
                id='previewimg'
                src={imgFile ? imgFile : userInfo.profileImage}
                alt='프로필사진'
              />
              <label htmlFor='file'>
                <S_EditButton onClick={uploadImg}>변경</S_EditButton>
                <input
                  id='uploadImg'
                  type='file'
                  accept='image/*'
                  onChange={handleFileUpload}
                  hidden
                />
              </label>
            </S_ImgBox>
          </div>
          <div className='nicknamebox'>
            <InputNickname value={nickName} onChange={onChange} />
          </div>
          <S_Button>프로필 수정</S_Button>
        </form>
      </S_EditBox>
    </S_Container>
  );
}

export default EditProfile;
