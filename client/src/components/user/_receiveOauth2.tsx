import { useEffect } from 'react';
import { useAppDispatch } from '../../store/store';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { setIsLogin, setUserInfo, setTokens } from '../../store/store';
import { getFetch } from '../../util/api';
import { RETURN_URL_PARAM } from '../../util/commonConstants';

interface ReturnUrlProps {
  returnUrl?: string;
}

function ReceiveOauth2({ returnUrl }: ReturnUrlProps) {
  if (returnUrl) {
    sessionStorage.setItem(RETURN_URL_PARAM, returnUrl);
  }

  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [searchParams] = useSearchParams();

  const accessToken = searchParams.get('access_token');
  const refreshToken = searchParams.get('refresh_token');
  const userId = searchParams.get('id');

  if (accessToken && refreshToken) {
    dispatch(
      setTokens({
        accessToken,
        refreshToken
      })
    );
    sessionStorage.setItem(
      'tokens',
      JSON.stringify({
        accessToken,
        refreshToken
      })
    );
  }

  useEffect(() => {
    const getUserInfo = async () => {
      if (userId) {
        const URL = `${process.env.REACT_APP_URL}/users/${userId}`;

        if (accessToken && refreshToken) {
          const res = await getFetch(URL, { accessToken, refreshToken });

          if (res) {
            dispatch(setIsLogin(true));
            dispatch(setUserInfo(res.data));

            sessionStorage.setItem('isLogin', JSON.stringify(true));
            sessionStorage.setItem('userInfo', JSON.stringify(res.data));

            const goToReturnUrl = sessionStorage.getItem(RETURN_URL_PARAM);
            goToReturnUrl ? navigate(goToReturnUrl) : navigate('/home');
          }
        }
      }
    };
    getUserInfo();
  }, []);

  return null;
}

export default ReceiveOauth2;
