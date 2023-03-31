import { useAppDispatch } from '../../store/store';
import { postFetch } from '../api';
import { setIsLogin, setTokens, setUserInfo } from '../../store/store';
import {
  SESSION_STORAGE_ISLOGIN_KEY,
  SESSION_STORAGE_USERINFO_KEY,
  SESSION_STORAGE_JWT_TOKENS_KEY
} from '../commonConstants';

export interface RegisterUserInputType {
  email: string;
  password?: string;
  // 회원가입 요청 성공 후 로그인 요청까지 연달아 보내야 하기 때문에 nickName 필드 optional
  nickName?: string;
  confirmPassword?: string;
}

export function useLoginRequestLogic() {
  const dispatch = useAppDispatch();

  const handleLogin = async (data: RegisterUserInputType) => {
    const POST_URL = `${process.env.REACT_APP_URL}/auth/login`;
    const res = await postFetch(POST_URL, data);

    if (res) {
      dispatch(setIsLogin(true));
      dispatch(setUserInfo(res.data.data));

      const tokens = {
        accessToken: res.headers.authorization,
        refreshToken: res.headers.refresh
      };
      dispatch(setTokens(tokens));

      sessionStorage.setItem(SESSION_STORAGE_ISLOGIN_KEY, JSON.stringify(true));
      sessionStorage.setItem(SESSION_STORAGE_USERINFO_KEY, JSON.stringify(res.data.data));
      sessionStorage.setItem(SESSION_STORAGE_JWT_TOKENS_KEY, JSON.stringify(tokens));
    }

    return res;
  };

  return { handleLogin };
}
