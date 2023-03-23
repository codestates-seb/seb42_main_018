import S_Container from '../../components/UI/S_Container';
import Tabmenu from '../../components/TabMenu';
import styled from 'styled-components';
import { S_Title } from '../../components/UI/S_Text';
import getGlobalState from '../../util/authorization/getGlobalState';
import InputPassword from '../../components/login/_inputPassword';
import { useState } from 'react';
import { checkPassword } from '../../util/authorization/checkPassword';
import { useLoginRequestLogic } from '../../util/authorization/useLoginRequestLogic';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { S_Button } from '../../components/UI/S_Button';

const S_PasswordBox = styled.div`
  margin-top: 50px;
`;

function EditPassword() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const returnUrl = searchParams.get('returnUrl'); // 바로 디코딩해줌. query string이 없으면 null

  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];
  const { userInfo } = getGlobalState();
  console.log(userInfo);

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_PasswordBox>
        <S_Title>계정 설정</S_Title>
        <div className='emailBox'>{userInfo.email ? userInfo.email : 'emailID@emeil.com'}</div>
        <div className='passwordBox'>
          {/* TODO : 비밀번호 로직 추가 */}
          {/* <InputPassword
            name='password'
            label='비밀번호'
            value={password}
            onChange={onChange}
            errorState={passwordError}
            errorMsg='비밀번호는 영문 알파벳과 숫자를 최소 1개 이상 포함하여 8~20자여야 합니다.'
          />
          <InputPassword
            name='confirmPassword'
            label='비밀번호 확인'
            value={confirmPassword}
            onChange={onChange}
            errorState={confirmPasswordError}
            errorMsg='비밀번호가 일치하지 않습니다.'
          /> */}
        </div>
        <S_Button>비밀번호 수정</S_Button>
      </S_PasswordBox>
    </S_Container>
  );
}

export default EditPassword;
