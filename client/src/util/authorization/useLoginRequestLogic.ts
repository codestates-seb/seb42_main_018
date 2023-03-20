import { useDispatch } from 'react-redux';
import { postFetch } from '../api';
import { setIsLogin, setTokens, setUserInfo } from '../../store/store';

export interface RegisterUserInputType {
  email: string;
  password: string;
  // 회원가입 요청 성공 후 로그인 요청까지 연달아 보내야 하기 때문에 nickName 필드 optional
  nickName?: string;
  confirmPassword?: string;
}

export function useLoginRequestLogic() {
  const dispatch = useDispatch();

  const handleLogin = async (data: RegisterUserInputType) => {
    const POST_URL = `${process.env.REACT_APP_URL}/auth/login`;
    const res = await postFetch(POST_URL, data);

    if (res) {
      dispatch(setIsLogin(true));
      dispatch(setUserInfo(res.data.data));
      dispatch(
        setTokens({
          accessToken: res.headers.authorization,
          refreshToken: res.headers.refresh
        })
      );
    }

    return res;
  };

  return { handleLogin };
}
