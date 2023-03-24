import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ReactNode } from 'react';
import getGlobalState from '../util/authorization/getGlobalState';

interface LoginCheckerProps {
  children: ReactNode;
}

function LoginChecker({ children }: LoginCheckerProps) {
  const navigate = useNavigate();
  const { isLogin } = getGlobalState();

  useEffect(() => {
    if (!isLogin) navigate('/login');
  }, [isLogin]);

  return isLogin ? <>{children}</> : null;
}

export default LoginChecker;
