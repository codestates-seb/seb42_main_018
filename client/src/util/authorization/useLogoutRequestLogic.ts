import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import {
  setIsLogin,
  setTokens,
  setUserInfo,
  userInitialState,
  tokensInitialState
} from '../../store/store';

// TODO : useDispatch 타입 설정
// TODO : dispatch await로 처리 끝난 뒤 navigate 함수 실행
// ERROR MSG: 'await' has no effect on the type of this expression.ts(80007)
export function useLogoutRequestLogic() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await dispatch(setIsLogin(false));
    await dispatch(setUserInfo(userInitialState));
    await dispatch(setTokens(tokensInitialState));
    navigate('/');
  };

  return { handleLogout };
}