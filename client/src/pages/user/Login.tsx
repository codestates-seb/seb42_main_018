import { useState } from 'react';
import styled from 'styled-components';
import { postFetch } from '../../util/api';
import { checkEmail, checkPassword } from '../../util/authorization/checkPassword';
import alertPreparingService from '../../util/alertPreparingService';
import S_Container from '../../components/UI/S_Container';
import { S_Button, S_EditButton } from '../../components/UI/S_Button';
import { S_Title, S_Label, S_Description } from '../../components/UI/S_Text';
import { S_Input } from '../../components/UI/S_Input';

const S_LoginWrapper = styled.div`
  height: calc(90vh - 50px);
  display: flex;
  flex-direction: column;

  & > .title-wrapper {
    height: 20vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  & > .oauth-wrapper {
    margin-top: auto;
    height: 11vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
`;

const S_SearchPasswordWrapper = styled.div`
  margin-top: 1.5vh;
  display: flex;
  justify-content: center;

  & > div {
    transform: translateY(15%);
  }
`;

function Login() {
  const [inputs, setInputs] = useState({
    email: '',
    password: ''
  });
  const { email, password } = inputs;

  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };
  // console.log(inputs);

  // * 카카오톡 로그인 관련 로직
  const KAKAO_LOGIN_URL = `${process.env.REACT_APP_URL}/oauth2/authorization/kakao`;
  const handleKakaoLogin = async () => {
    return window.location.assign(KAKAO_LOGIN_URL);
  };

  // * POST 요청 관련 로직
  const POST_URL = `${process.env.REACT_APP_URL}/auth/login`;

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!email || !password) return;

    // 이메일 & 비밀번호 유효성 검사
    const isValidEmail = checkEmail(email);
    const isValidPassword = checkPassword(password);
    console.log(isValidEmail);

    if (!isValidEmail) setEmailError(true);
    else setEmailError(false);

    if (!isValidPassword) setPasswordError(true);
    else setPasswordError(false);

    if (!isValidEmail || !isValidPassword) return;

    // 서버에 post 요청
    const res = await postFetch(POST_URL, inputs);
    console.log('응답 데이터: ', res);

    // TODO : 응답 상태에 따라 리다이렉트 로직
  };

  // TODO : 소모전 로그인하기 섹션 클릭하면 리다이렉트

  return (
    <S_Container>
      <S_LoginWrapper>
        <div className='title-wrapper'>
          <S_Title>소모전 로그인하기</S_Title>
        </div>
        <div className='form-wrapper'>
          <form onSubmit={onSubmit}>
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
                <>
                  <S_Description color={'var(--red100)'}>
                    유효하지 않은 형식의 이메일입니다.
                  </S_Description>
                </>
              )}
            </div>

            <div>
              <label htmlFor='password'>
                <S_Label>비밀번호</S_Label>
              </label>
              <S_Input
                id='password'
                name='password'
                type='text'
                width='96%'
                value={password}
                onChange={onChange}
              />
              {passwordError && (
                <>
                  <S_Description color={'var(--red100)'}>
                    비밀번호는 영문 알파벳과 숫자를 최소 1개 이상 포함하여 8~20자여야 합니다.
                  </S_Description>
                </>
              )}
            </div>

            <S_Button>로그인하기</S_Button>
          </form>
          <S_SearchPasswordWrapper>
            <S_EditButton onClick={alertPreparingService}>비밀번호 찾기</S_EditButton>
            <S_Description>&middot;</S_Description>
            <S_EditButton>회원가입</S_EditButton>
          </S_SearchPasswordWrapper>
        </div>
        <div className='oauth-wrapper'>
          {/* //TODO: 색상 팔레트 확인 후 변수로 수정 */}
          <S_Button
            onClick={handleKakaoLogin}
            addStyle={{
              backgroundColor: 'var(--kakao-main-theme)',
              color: 'var(--gray500)',
              hoverBgColor: 'var(--kakao-hover-theme)'
            }}
          >
            카카오톡 로그인
          </S_Button>
          <S_Button
            onClick={alertPreparingService}
            addStyle={{
              backgroundColor: 'var(--naver-main-theme)',
              hoverBgColor: 'var(--naver-hover-theme)'
            }}
          >
            네이버 로그인
          </S_Button>
        </div>
      </S_LoginWrapper>
    </S_Container>
  );
}

export default Login;
