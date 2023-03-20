import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import useGoToIntro from '../../util/hooks/useGoToIntro';
import { checkEmail, checkPassword } from '../../util/authorization/checkPassword';
import { postFetch } from '../../util/api';
import S_Container from '../../components/UI/S_Container';
import { S_LoginWrapper, S_InstructionWrapper } from './Login';
import { S_Title, S_Label, S_Description } from '../../components/UI/S_Text';
import { S_Input } from '../../components/UI/S_Input';
import { S_Button, S_EditButton } from '../../components/UI/S_Button';

const S_RegisterWrapper = styled(S_LoginWrapper)`
  & .title-wrapper {
    justify-content: flex-start;
  }

  & .form-wrapper {
    margin-top: 1vh;
    height: 70vh;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }
  & .register-btn {
    margin-top: 1vh;
  }
`;

interface userInfoType {
  email: string;
  password: string;
  nickName: string;
  confirmPassword?: string;
}

function Register() {
  const navigate = useNavigate();
  const goToIntro = useGoToIntro();
  const goToLogin = () => {
    navigate('/login');
  };

  const [inputs, setInputs] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    nickName: ''
  });
  const { email, password, confirmPassword, nickName } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };
  console.log(inputs);

  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [confirmPasswordError, setConfirmPasswordError] = useState(false);

  // * POST 요청 관련 로직
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!email || !password || !confirmPassword || !nickName) return;

    // 이메일 & 비밀번호 유효성 검사
    const isValidEmail = checkEmail(email);
    const isValidPassword = checkPassword(password);

    if (!isValidEmail) setEmailError(true);
    else setEmailError(false);

    if (!isValidPassword) setPasswordError(true);
    else setPasswordError(false);

    if (password !== confirmPassword) setConfirmPasswordError(true);
    else setConfirmPasswordError(false);

    if (!isValidEmail || !isValidPassword) return;

    // 서버에 post 요청
    const POST_URL = `${process.env.REACT_APP_URL}/users`;
    const userInfo: userInfoType = { ...inputs };
    delete userInfo.confirmPassword;

    // console.log(userInfo);

    const res = await postFetch(POST_URL, userInfo);

    if (res) {
      console.log(res);
      // ! 성공시 res.data 빈 문자열 -> user 정보 받아야 하지 않을까?
      // ! 헤더에 jwt 토큰 없음
      // headers.location : "/users/104"

      // TODO 1. user data (jwt 토큰 포함) 전역 상태로 담기
      // 2. /home 으로 리다이렉트
      //   navigate('/home');
    }
  };

  // TODO: 이메일, 닉네임 중복 검사 요청

  return (
    <S_Container>
      <S_RegisterWrapper>
        <div className='title-wrapper' role='presentation' onClick={goToIntro}>
          <S_Title>소모전 회원가입</S_Title>
        </div>
        <form onSubmit={onSubmit}>
          <div className='form-wrapper'>
            <div>
              <label htmlFor='email'>
                <S_Label>이메일</S_Label>
              </label>
              <S_Input
                id='email'
                name='email'
                type='text'
                width='96%'
                value={email}
                onChange={onChange}
              />

              {emailError && (
                <S_Description color={'var(--red100)'}>
                  유효하지 않은 형식의 이메일입니다.
                </S_Description>
              )}
            </div>

            <div>
              <label htmlFor='password'>
                <S_Label>비밀번호</S_Label>
              </label>
              <S_Description>
                비밀번호는 최소 1개의 문자와 1개의 숫자를 포함하여 8~20자여야 합니다.
              </S_Description>
              <S_Input
                id='password'
                name='password'
                type='password'
                width='96%'
                value={password}
                onChange={onChange}
              />
              {passwordError && (
                <S_Description color={'var(--red100)'}>
                  비밀번호의 조건을 만족하지 않습니다.
                </S_Description>
              )}
            </div>

            <div>
              <label htmlFor='confirmPassword'>
                <S_Label>비밀번호 확인</S_Label>
              </label>
              <S_Input
                id='confirmPassword'
                name='confirmPassword'
                type='password'
                width='96%'
                value={confirmPassword}
                onChange={onChange}
              />
              {confirmPasswordError && (
                <S_Description color={'var(--red100)'}>비밀번호가 일치하지 않습니다.</S_Description>
              )}
            </div>

            <div>
              <label htmlFor='nickname'>
                <S_Label>닉네임</S_Label>
              </label>
              <S_Description>닉네임은 언제든지 바꿀 수 있어요.</S_Description>
              <S_Input
                id='nickname'
                name='nickName'
                type='text'
                width='96%'
                value={nickName}
                onChange={onChange}
              />
            </div>
            <div className='register-btn'>
              <S_Button>회원가입</S_Button>
            </div>
          </div>
        </form>

        <S_InstructionWrapper>
          <S_EditButton onClick={goToLogin}>이미 아이디가 있다면 로그인 해주세요</S_EditButton>
        </S_InstructionWrapper>
      </S_RegisterWrapper>
    </S_Container>
  );
}

export default Register;
