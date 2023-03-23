import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Tabmenu from '../../components/TabMenu';
import { S_Button, S_EditButton, S_SelectButton } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Label, S_Title } from '../../components/UI/S_Text';
import getGlobalState from '../../util/authorization/getGlobalState';
import styled from 'styled-components';
import InputNickname from '../../components/login/_inputNickname';
import { S_Input } from '../../components/UI/S_Input';

const S_EditBox = styled.div`
  margin-top: 50px;
  h1 {
    margin-bottom: 5vh;
  }
  .profileImgBox {
    margin-bottom: 8vh;
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
    margin-bottom: 8vh;
  }
`;

const S_ImgBox = styled.div`
  // 유저 프로필이미지 설정
  display: flex;
  align-items: center;
`;

function EditProfile() {
  const navigate = useNavigate();
  const { userInfo } = getGlobalState();
  const [inputs, setInputs] = useState({
    nickName: userInfo.nickName
  });
  const { nickName } = inputs;
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  // 상단탭
  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];

  const uploadImg = () => {
    const inputname = document.getElementById('uploadImg');
    inputname?.click();
  };

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_EditBox>
        <S_Title>현재 프로필 수정</S_Title>
        <div className='profileImgBox'>
          <S_Label>프로필사진</S_Label>
          <S_ImgBox>
            {/* <img src={userInfo.profileImage} alt='프로필사진' /> */}
            <img id='previewimg' src={userInfo.profileImage} alt='프로필사진' />
            <label htmlFor='file'>
              <S_EditButton onClick={uploadImg}>변경</S_EditButton>
              <input id='uploadImg' type='file' accept='image/*' hidden />
            </label>
          </S_ImgBox>
        </div>
        <div className='nicknamebox'>
          <InputNickname value={nickName} onChange={onChange} />
        </div>
      </S_EditBox>
      <S_Button onClick={() => navigate('/mypage')}>회원정보 수정</S_Button>
    </S_Container>
  );
}

export default EditProfile;
//
