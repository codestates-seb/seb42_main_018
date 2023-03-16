import S_Container from '../../components/UI/S_Container';
import { S_Button } from '../../components/UI/S_Button';
function Login() {
  const handleKakaoLogin = () => {
    console.log('카카오 로그인 클릭');
  };

  return (
    <S_Container>
      로그인페이지
      <S_Button
        onClick={handleKakaoLogin}
        addStyle={{ backgroundColor: '#FAE64C', color: 'var(--gray500)' }}
      >
        카카오톡 로그인
      </S_Button>
    </S_Container>
  );
}

export default Login;
