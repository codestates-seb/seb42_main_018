import { useNavigate } from 'react-router-dom';
import {
  useAppDispatch,
  setIsLogin,
  setTokens,
  setUserInfo,
  userInitialState,
  tokensInitialState
} from '../../store/store';
import {
  SESSION_STORAGE_ISLOGIN_KEY,
  SESSION_STORAGE_USERINFO_KEY,
  SESSION_STORAGE_JWT_TOKENS_KEY
} from '../commonConstants';

// TODO : dispatch await로 처리 끝난 뒤 navigate 함수 실행
export function useLogoutRequestLogic() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await dispatch(setIsLogin(false));
    await dispatch(setUserInfo(userInitialState));
    await dispatch(setTokens(tokensInitialState));

    sessionStorage.removeItem(SESSION_STORAGE_ISLOGIN_KEY);
    sessionStorage.removeItem(SESSION_STORAGE_USERINFO_KEY);
    sessionStorage.removeItem(SESSION_STORAGE_JWT_TOKENS_KEY);
    navigate('/');
  };

  return { handleLogout };
}
