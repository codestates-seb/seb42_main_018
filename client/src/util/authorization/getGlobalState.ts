import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';
import {
  SESSION_STORAGE_ISLOGIN_KEY,
  SESSION_STORAGE_USERINFO_KEY,
  SESSION_STORAGE_JWT_TOKENS_KEY
} from '../commonConstants';

const getGlobalState = () => {
  let isLogin = useSelector((state: RootState) => state.isLogin);
  let userInfo = useSelector((state: RootState) => state.userInfo);
  let tokens = useSelector((state: RootState) => state.tokens);

  const sessionIsLogin = sessionStorage.getItem(SESSION_STORAGE_ISLOGIN_KEY);
  const sessionUserInfo = sessionStorage.getItem(SESSION_STORAGE_USERINFO_KEY);
  const sessionTokens = sessionStorage.getItem(SESSION_STORAGE_JWT_TOKENS_KEY);

  if (!userInfo.userId && sessionIsLogin && sessionUserInfo && sessionTokens) {
    isLogin = JSON.parse(sessionIsLogin);
    userInfo = JSON.parse(sessionUserInfo);
    tokens = JSON.parse(sessionTokens);
  }

  return { isLogin, userInfo, tokens };
};

export default getGlobalState;
