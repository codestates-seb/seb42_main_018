import axios from 'axios';
import S_Container from '../../components/UI/S_Container';
import { S_Button } from '../../components/UI/S_Button';

function Login() {
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
    console.log(res);
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
    </S_Container>
  );
}

export default Login;
