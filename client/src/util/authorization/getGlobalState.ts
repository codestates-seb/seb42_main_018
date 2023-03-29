import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';

const getGlobalState = () => {
  let isLogin = useSelector((state: RootState) => state.isLogin);
  let userInfo = useSelector((state: RootState) => state.userInfo);
  let tokens = useSelector((state: RootState) => state.tokens);

  const sessionIsLogin = sessionStorage.getItem('isLogin');
  const sessionUserInfo = sessionStorage.getItem('userInfo');
  const sessionTokens = sessionStorage.getItem('tokens');

  if (!userInfo.userId && sessionIsLogin && sessionUserInfo && sessionTokens) {
    isLogin = JSON.parse(sessionIsLogin);
    userInfo = JSON.parse(sessionUserInfo);
    tokens = JSON.parse(sessionTokens);
  }

  return { isLogin, userInfo, tokens };
};

export default getGlobalState;
