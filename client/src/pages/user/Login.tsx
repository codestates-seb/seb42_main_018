import { useState } from 'react';
import { useNavigate, Link, useSearchParams } from 'react-router-dom';
import styled from 'styled-components';
import { checkEmail, checkPassword } from '../../util/authorization/checkPassword';
import alertPreparingService from '../../util/alertPreparingService';
import { handleKakaoLogin } from '../../util/authorization/handleSnsLogin';
import { useLoginRequestLogic } from '../../util/authorization/useLoginRequestLogic';
import S_Container from '../../components/UI/S_Container';
import { S_Button, S_EditButton } from '../../components/UI/S_Button';
import { S_Title, S_Description } from '../../components/UI/S_Text';
import RegisterModal from './_registerModal';
import InputEmail from '../../components/login/_inputEmail';
import InputPassword from '../../components/login/_inputPassword';
import ReceiveOauth2 from './_receiveOauth2';

export const S_LoginWrapper = styled.div`
  height: calc(90vh - 50px);
  display: flex;
  flex-direction: column;

  & > .title-wrapper {
    height: 16vh;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }

  & > .oauth-wrapper {
    margin-top: auto;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  & > .oauth-wrapper button:first-child {
    margin-bottom: 8px;
  }
`;

export const S_InstructionWrapper = styled.div`
  margin-top: 1.5vh;
  display: flex;
  justify-content: center;

  & > div {
    transform: translateY(15%);
  }
`;

function Login() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const returnUrl = searchParams.get('returnUrl'); // 바로 디코딩해줌. query string이 없으면 null

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

  const [showModal, setShowModal] = useState(false);
  const handleModal = () => {
    setShowModal((current) => !current);
  };

  // * POST 요청 관련 로직
  const { handleLogin } = useLoginRequestLogic();

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!email || !password) return;

    // 이메일 & 비밀번호 유효성 검사
    const isValidEmail = checkEmail(email);
    const isValidPassword = checkPassword(password);

    if (!isValidEmail) setEmailError(true);
    else setEmailError(false);

    if (!isValidPassword) setPasswordError(true);
    else setPasswordError(false);

    if (!isValidEmail || !isValidPassword) return;

    // * 서버에 로그인 post 요청 및 전역 상태 설정
    const res = await handleLogin(inputs);

    if (res) {
      if (returnUrl) navigate(returnUrl, { replace: true });
      else navigate('/home');
    }
  };

  return (
    <S_Container>
      <S_LoginWrapper>
        <div className='title-wrapper'>
          <Link to='/'>
            <S_Title>소모전 로그인하기</S_Title>
          </Link>
        </div>
        <div className='form-wrapper'>
          <form onSubmit={onSubmit}>
            <InputEmail value={email} onChange={onChange} errorState={emailError} />
            <InputPassword
              name='password'
              label='비밀번호'
              value={password}
              onChange={onChange}
              errorState={passwordError}
              errorMsg='비밀번호는 영문 알파벳과 숫자를 최소 1개 이상 포함하여 8~20자여야 합니다.'
            />
            <S_Button>로그인하기</S_Button>
          </form>
          <S_InstructionWrapper>
            <S_EditButton onClick={alertPreparingService}>비밀번호 찾기</S_EditButton>
            <S_Description>&middot;</S_Description>
            <S_EditButton onClick={handleModal}>회원가입</S_EditButton>
          </S_InstructionWrapper>
        </div>
        <div className='oauth-wrapper'>
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

      <RegisterModal handleModal={handleModal} showModal={showModal} />
      {returnUrl && <ReceiveOauth2 returnUrl={returnUrl} />}
    </S_Container>
  );
}

export default Login;
