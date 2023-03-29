import { useNavigate } from 'react-router-dom';
import {
  useAppDispatch,
  setIsLogin,
  setTokens,
  setUserInfo,
  userInitialState,
  tokensInitialState
} from '../../store/store';

// TODO : dispatch await로 처리 끝난 뒤 navigate 함수 실행
export function useLogoutRequestLogic() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await dispatch(setIsLogin(false));
    await dispatch(setUserInfo(userInitialState));
    await dispatch(setTokens(tokensInitialState));

    sessionStorage.removeItem('isLogin');
    sessionStorage.removeItem('userInfo');
    sessionStorage.removeItem('tokens');
    navigate('/');
  };

  return { handleLogout };
}
