import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { ReactNode } from 'react';
import getGlobalState from '../util/authorization/getGlobalState';
import { RETURN_URL_PARAM } from '../util/commonConstants';

interface LoginCheckerProps {
  children: ReactNode;
}

function LoginChecker({ children }: LoginCheckerProps) {
  const { isLogin } = getGlobalState();
  const { pathname } = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin)
      navigate(`/login?${RETURN_URL_PARAM}=${encodeURIComponent(pathname)}`, { replace: true });
  }, [isLogin]);

  return isLogin ? <>{children}</> : null;
}

export default LoginChecker;
