import axios from 'axios';
import S_Container from '../../components/UI/S_Container';
import { S_Button } from '../../components/UI/S_Button';
import { postFetch } from '../../API_TEST';

function Login() {
  // * GET 요청 관련 로직
  const getFetch = async (url: string) => {
    try {
      const res = await axios.get(url);
      return res;
    } catch (err) {
      console.error(err);
    }
  };

  const KAKAO_LOGIN_URL = 'https://dev.somojeon.site/oauth2/authorization/kakao';
  const handleKakaoLogin = async () => {
    const res = await getFetch(KAKAO_LOGIN_URL);
    console.log('카카오톡 로그인 응답 : ', res);
  };

  // * POST 요청 관련 로직
  const POST_URL = 'https://dev.somojeon.site/auth/login';
  const newData = {
    email: 'email@example.com',
    password: 'password'
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const res = await postFetch(POST_URL, newData, { withCredentials: true });
    console.log('post 응답 데이터: ', res);
  };

  return (
    <S_Container>
      로그인페이지
      <S_Button
        onClick={handleKakaoLogin}
        addStyle={{ backgroundColor: '#FEE500', color: 'var(--gray500)', hoverBgColor: '#FADA0A' }}
      >
        카카오톡 로그인
      </S_Button>
      <form onSubmit={onSubmit}>
        <S_Button>일반 회원가입</S_Button>
      </form>
    </S_Container>
  );
}

export default Login;
