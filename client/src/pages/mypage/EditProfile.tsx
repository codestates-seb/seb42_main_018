import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Tabmenu from '../../components/TabMenu';
import { S_Button, S_EditButton } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Label, S_Title } from '../../components/UI/S_Text';
import getGlobalState from '../../util/authorization/getGlobalState';
import InputNickname from '../../components/login/_inputNickname';

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
  const { userInfo } = getGlobalState();

  // 버튼 클릭시 파일첨부(input=file 태그) 실행시켜주는 함수. 못생긴 파일첨부 input은 안녕!
  const uploadImg = () => {
    const inputname = document.getElementById('uploadImg');
    inputname?.click();
  };

  // 파일로 가져온 이미지 미리보기
  const [imgFile, setImgFile] = useState(userInfo.profileImage);
  function handleFileUpload(event: React.ChangeEvent<HTMLInputElement>) {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.addEventListener('load', () => {
        setImgFile(reader.result as string);
      });
      reader.readAsDataURL(file);
    }
  }

  // 닉네임 수정 관련
  const [inputs, setInputs] = useState({
    nickName: userInfo.nickName
  });
  const { nickName } = inputs;
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  // 회원정보 수정 버튼 클릭시 실행될 함수
  const submitProfile = () => {
    // TODO : 패치 데이터 날리기
    navigate('/mypage'); // 수정 마치면 마이페이지로 이동
  };

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_EditBox>
        <S_Title>현재 프로필 수정</S_Title>
        <div className='profileImgBox'>
          <S_Label>프로필사진</S_Label>
          <S_ImgBox>
            <img id='previewimg' src={imgFile ? imgFile : userInfo.profileImage} alt='프로필사진' />
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
      </S_EditBox>
      <S_Button onClick={submitProfile}>프로필 수정</S_Button>
    </S_Container>
  );
}

export default EditProfile;
//
