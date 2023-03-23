import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styled from 'styled-components';
import { checkEmail, checkPassword } from '../../util/authorization/checkPassword';
import { postFetch } from '../../util/api';
import {
  RegisterUserInputType,
  useLoginRequestLogic
} from '../../util/authorization/useLoginRequestLogic';
import { jwtTokensType, setIsLogin, setTokens, setUserInfo } from '../../store/store';
import S_Container from '../../components/UI/S_Container';
import { S_LoginWrapper, S_InstructionWrapper } from './Login';
import { S_Title, S_Label, S_Description } from '../../components/UI/S_Text';
import { S_Input } from '../../components/UI/S_Input';
import { S_Button, S_EditButton, S_SelectButton } from '../../components/UI/S_Button';
import { useDispatch } from 'react-redux';

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
  & .email-input-area {
    margin-bottom: 10px;
    display: flex;
    align-items: center;

    & > input {
      margin: 0 0.8rem 0 0;
    }
  }
  & .register-btn {
    margin-top: 1vh;
  }
`;

function Register() {
  const navigate = useNavigate();

  const [isFromOauthLogin, setIsFromOauthLogin] = useState(false);
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

  // 소셜 로그인 최초 시도 후 회원가입 페이지로 보내진 경우
  // tempTokens: 소셜 로그인 후 회원가입 요청을 보내는 사용자를 위한 임시 토큰
  const [tempTokens, setTempTokens] = useState<jwtTokensType>();

  useEffect(() => {
    const url = new URL(window.location.href);

    if (url.search) {
      setIsFromOauthLogin(true);

      const accessToken = url.searchParams.get('access_token');
      const refreshToken = url.searchParams.get('refresh_token');
      const snsEmail = url.searchParams.get('email');

      // TODO: temp 리프레쉬 토큰이 만료한 경우(30분 경과) 액세스 토큰 재발급 로직 필요
      if (accessToken && refreshToken)
        setTempTokens({
          accessToken,
          refreshToken
        });

      if (snsEmail) setInputs({ ...inputs, email: snsEmail });
    }
  }, []);

  const [isEmailDuplicationChecked, setIsEmailDuplicationChecked] = useState(false);
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [confirmPasswordError, setConfirmPasswordError] = useState(false);

  // * POST 요청 관련 로직
  const { handleLogin } = useLoginRequestLogic();
  const POST_URL = `${process.env.REACT_APP_URL}/users`;
  const dispatch = useDispatch();

  const checkEmailValidation = () => {
    const isValidEmail = checkEmail(email);
    if (!isValidEmail) setEmailError(true);
    else setEmailError(false);

    return isValidEmail;
  };

  const checkPasswordValidation = () => {
    const isValidPassword = checkPassword(password);

    if (!isValidPassword) setPasswordError(true);
    else setPasswordError(false);

    if (password !== confirmPassword) setConfirmPasswordError(true);
    else setConfirmPasswordError(false);

    return isValidPassword;
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!isEmailDuplicationChecked) {
      alert('먼저 이메일 중복 확인을 해주세요');
      return;
    }

    const userInfo: RegisterUserInputType = { ...inputs };

    if (isFromOauthLogin) {
      console.log('카톡 회원가입 요청');

      if (!email || !nickName) return;

      delete userInfo.password;
      delete userInfo.confirmPassword;

      const registerResponse = await postFetch(POST_URL, userInfo, tempTokens?.accessToken);

      // * access token 30분 경과하여 만료된 경우 서버에서 login 페이지로 자동 리다이렉트 시켜줌
      if (registerResponse) {
        dispatch(setIsLogin(true));
        dispatch(setUserInfo(registerResponse.data.data));
        dispatch(
          setTokens({
            accessToken: registerResponse.headers.authorization,
            refreshToken: registerResponse.headers.refresh
          })
        );
        navigate('/home');
      }
    } else {
      console.log('일반 회원가입 요청');
      if (!email || !password || !confirmPassword || !nickName) return;

      // 이메일 & 비밀번호 유효성 검사
      const result1 = checkEmailValidation();
      const result2 = checkPasswordValidation();
      if (!result1 || !result2) return;

      // 서버에 회원가입 post 요청
      delete userInfo.confirmPassword;

      const registerResponse = await postFetch(POST_URL, userInfo);
      console.log(registerResponse);

      // 회원가입 성공 후 로그인 post 요청 연달아 시도
      if (registerResponse) {
        delete userInfo.nickName;
        const loginResponse = await handleLogin(userInfo);
        if (loginResponse) navigate('/home');
      }
    }
  };

  const checkEmailDuplication = async () => {
    if (!email) {
      alert('이메일 주소를 먼저 입력해 주세요.');
      return;
    }

    // 이메일 유효성 검사
    const result = checkEmailValidation();
    if (!result) return;

    console.log('post 요청');
    const POST_URL = `${process.env.REACT_APP_URL}/users/email`;
    const res = await postFetch(POST_URL, { email });
    console.log(res);

    if (res) {
      if (res.status === 200) {
        alert('사용할 수 있는 이메일 주소입니다.');
        setIsEmailDuplicationChecked(true);
      } else {
        alert('이미 가입된 이메일 주소입니다.');
      }
    }
  };
  // console.log(isEmailDuplicationChecked);

  return (
    <S_Container>
      <S_RegisterWrapper>
        <div className='title-wrapper'>
          <Link to='/'>
            <S_Title>소모전 로그인하기</S_Title>
          </Link>
        </div>
        <form onSubmit={onSubmit}>
          <div className='form-wrapper'>
            <div className='email-box'>
              <label htmlFor='email'>
                <S_Label>이메일</S_Label>
              </label>
              <div className='email-input-area'>
                <S_Input
                  id='email'
                  name='email'
                  type='text'
                  width='96%'
                  value={email}
                  onChange={onChange}
                />
                <S_SelectButton
                  type='button'
                  width='auto'
                  style={{ whiteSpace: 'nowrap' }}
                  onClick={checkEmailDuplication}
                >
                  중복 확인
                </S_SelectButton>
              </div>

              {emailError && (
                <S_Description color={'var(--red100)'}>
                  유효하지 않은 형식의 이메일입니다.
                </S_Description>
              )}
            </div>

            {!isFromOauthLogin && (
              <>
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
                    <S_Description color={'var(--red100)'}>
                      비밀번호가 일치하지 않습니다.
                    </S_Description>
                  )}
                </div>
              </>
            )}

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
          <S_EditButton onClick={() => navigate('/login')}>
            이미 아이디가 있다면 로그인 해주세요
          </S_EditButton>
        </S_InstructionWrapper>
      </S_RegisterWrapper>
    </S_Container>
  );
}

export default Register;
