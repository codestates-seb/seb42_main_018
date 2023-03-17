import axios from 'axios';
import styled from 'styled-components';
import S_Container from '../../components/UI/S_Container';
import { S_Button, S_EditButton } from '../../components/UI/S_Button';
import { S_Title, S_Label, S_Description } from '../../components/UI/S_Text';
import { S_Input } from '../../components/UI/S_Input';
import { postFetch } from '../../API_TEST';

const S_SearchPasswordWrapper = styled.div`
  display: flex;
  justify-content: center;

  & > div {
    transform: translateY(15%);
  }
`;

function Login() {
  // * 카카오톡 로그인 관련 로직
  const KAKAO_LOGIN_URL = 'https://dev.somojeon.site/oauth2/authorization/kakao';
  const handleKakaoLogin = async () => {
    return window.location.assign(KAKAO_LOGIN_URL);
  };

  // * POST 요청 관련 로직
  const POST_URL = 'https://dev.somojeon.site/auth/login';
  const newData = {
    email: 'test4@gmail.com',
    password: '1234'
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // const config = {
    //   headers: {
    //     'Content-Type': 'application/json',
    //     withCredentials: true,
    //     Authorization: 'test string'
    //   }
    // };

    console.log('post 요청');
    const res = await postFetch(POST_URL, newData);
    console.log('응답 데이터: ', res);
  };

  return (
    <S_Container>
      <S_Title>소모전 로그인하기</S_Title>
      <form onSubmit={onSubmit}>
        <div>
          <label htmlFor='email'>
            <S_Label>이메일</S_Label>
          </label>
          <S_Input
            id='email'
            name='email'
            type='text'
            // value={}
            // onChange={onChange}
          />
        </div>
        <div>
          <label htmlFor='password'>
            <S_Label>비밀번호</S_Label>
          </label>
          <S_Input
            id='password'
            name='password'
            type='text'
            // value={}
            // onChange={onChange}
          />
          <S_Description color={'var(--red100)'}>
            비밀번호는 영문 알파벳과 숫자를 최소 1개 이상 포함하여 8~20자여야 합니다.
          </S_Description>
        </div>

        <S_Button>로그인하기</S_Button>
      </form>
      <S_SearchPasswordWrapper>
        <S_EditButton>비밀번호 찾기</S_EditButton>
        <S_Description>&middot;</S_Description>
        <S_EditButton>회원가입</S_EditButton>
      </S_SearchPasswordWrapper>

      {/* //TODO: 색상 팔레트 확인 후 변수로 수정 */}
      <S_Button
        onClick={handleKakaoLogin}
        addStyle={{ backgroundColor: '#FEE500', color: 'var(--gray500)', hoverBgColor: '#FADA0A' }}
      >
        카카오톡 로그인
      </S_Button>
      <S_Button addStyle={{ backgroundColor: '#5AC466', hoverBgColor: '#33a45c' }}>
        네이버 로그인
      </S_Button>
    </S_Container>
  );
}

export default Login;
