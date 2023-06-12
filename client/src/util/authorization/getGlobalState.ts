import { useSelector } from 'react-redux';
import { RootState } from '../../store/store';

const getGlobalState = () => {
  const isLogin = useSelector((state: RootState) => state.isLogin);
  const userInfo = useSelector((state: RootState) => state.userInfo);
  const tokens = useSelector((state: RootState) => state.tokens);

  return { isLogin, userInfo, tokens };
};

export default getGlobalState;
