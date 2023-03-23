import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DeleteAccount from './DeleteAccount';
import Tabmenu from '../../components/TabMenu';
import { S_Button } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Input } from '../../components/UI/S_Input';
import { S_Description, S_Label, S_Text } from '../../components/UI/S_Text';

function EditProfile() {
  const navigate = useNavigate();

  // 상단탭
  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <div>
        <S_Label>프로필사진</S_Label>
        <S_Label>닉네임</S_Label>
        <S_Description>닉네임은 언제든지 바꿀 수 있어요.</S_Description>
        <S_Input></S_Input>
        <S_Label>이메일</S_Label>
        <S_Text>이메일 주소 고정</S_Text>
        <S_Label>비밀번호</S_Label>
        <S_Input></S_Input>
        <S_Label>비밀번호 확인</S_Label>
        <S_Input></S_Input>
        <S_Button onClick={() => navigate('/mypage')}>회원정보 수정</S_Button>
        <DeleteAccount />
      </div>
    </S_Container>
  );
}

export default EditProfile;
