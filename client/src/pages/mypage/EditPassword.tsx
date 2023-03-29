import S_Container from '../../components/UI/S_Container';
import Tabmenu from '../../components/TabMenu';
import styled from 'styled-components';
import { S_Label, S_Title } from '../../components/UI/S_Text';
import getGlobalState from '../../util/authorization/getGlobalState';
import InputPassword from '../../components/login/_inputPassword';
import { useState } from 'react';
import { checkPassword } from '../../util/authorization/checkPassword';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { S_Button } from '../../components/UI/S_Button';
import { patchFetch } from '../../util/api';

const S_PasswordBox = styled.div`
  margin-top: 50px;
  h1 {
    margin-bottom: 30px;
  }
  .emailBox {
    margin-bottom: 40px;
  }
`;

function EditPassword() {
  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];

  const navigate = useNavigate();
  const { userInfo, tokens } = getGlobalState();

  // 서버로 전해줄 데이터
  const [inputs, setInputs] = useState({
    currentPassword: '',
    nextPassword: '',
    nextPasswordCheck: ''
  });
  const { currentPassword, nextPassword, nextPasswordCheck } = inputs;
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  // 비밀번호 유효성 검사 상태관리
  const [currentPasswordError, setCurrentPasswordError] = useState(false); // 기존 비밀번호
  const [nextPasswordError, setNextPasswordError] = useState(false); // 바꿀 비밀번호
  const [nextPasswordCheckError, setNextPasswordCheckError] = useState(false); // 바꿀 비밀번호 확인

  // 비밀번호 유효성 검사 함수
  const checkPasswordValidation = () => {
    const isValidPassword = checkPassword(nextPassword);
    if (!isValidPassword) setNextPasswordError(true);
    else setNextPasswordError(false);
    if (nextPassword !== nextPasswordCheck) setNextPasswordCheckError(true);
    else setNextPasswordCheckError(false);
    return isValidPassword && nextPassword === nextPasswordCheck;
  };

  // 제출 버튼 함수
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!currentPassword || !nextPassword || !nextPasswordCheck) return;
    const result = checkPasswordValidation();
    if (!result) return;
    const res = await patchFetch(
      `${process.env.REACT_APP_URL}/users/${userInfo.userId}/password`,
      inputs,
      tokens
    );

    if (res) {
      if (res?.headers.request === 'False') {
        alert('비밀번호가 일치하지 않습니다.');
        setCurrentPasswordError(true);
        return;
      } else {
        alert('수정이 완료되었습니다!');
        navigate('/mypage');
      }
    }
  };
  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_PasswordBox>
        <S_Title>계정 설정</S_Title>
        {/* TODO : 본인 이메일 div 스타일 */}
        <div className='emailBox'>
          <S_Label> 본인 이메일</S_Label>
          {userInfo.email ? userInfo.email : 'name@emeil.com'}
        </div>

        <form className='passwordBox' onSubmit={onSubmit}>
          <InputPassword
            name='currentPassword'
            label='현재 비밀번호'
            value={currentPassword}
            onChange={onChange}
            errorState={currentPasswordError}
            errorMsg='현재 비밀번호와 일치하지 않습니다.'
          />
          <InputPassword
            name='nextPassword'
            label='새 비밀번호'
            value={nextPassword}
            onChange={onChange}
            errorState={nextPasswordError}
            errorMsg='비밀번호는 영문 알파벳과 숫자를 최소 1개 이상 포함하여 8~20자여야 합니다.'
          />
          <InputPassword
            name='nextPasswordCheck'
            label='비밀번호 확인'
            value={nextPasswordCheck}
            onChange={onChange}
            errorState={nextPasswordCheckError}
            errorMsg='비밀번호가 일치하지 않습니다.'
          />
          <S_Button>비밀번호 수정</S_Button>
        </form>
      </S_PasswordBox>
    </S_Container>
  );
}
export default EditPassword;
