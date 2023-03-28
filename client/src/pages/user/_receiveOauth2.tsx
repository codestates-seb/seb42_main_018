import { useEffect, useState } from 'react';
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
  }

  console.log(accessToken);
  console.log(refreshToken);

  // const userId = 85;
  // const accessToken =
  //   'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJVU0VSIl0sInVzZXJJZCI6ODUsInVzZXJuYW1lIjoidXNlcjg1QGR1bW15LmNvbSIsInN1YiI6InVzZXI4NUBkdW1teS5jb20iLCJpYXQiOjE2Nzk4MzkxOTMsImV4cCI6MTY3OTg0MDk5M30.jNwATSq87ltoIyRukHXpnpsx0wMLkkuk9yqTq0NKmpGlNm-49gMr85jjbzC99bVryzesDwJr8Fzej9dlVyZcxg';
  // const refreshToken =
  //   'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyODVAZHVtbXkuY29tIiwiaWF0IjoxNjc5ODM5MTkzLCJleHAiOjE2Nzk4NjQzOTN9.R1EQygUVMfuehCJ5bhlUzFRXUUbtZgVAKaZ-jkJ_gqZF9-b1ydFS-aj03BIAWFaUP9cU8dxRt6VlIIX7lmRtew';

  useEffect(() => {
    const getUserInfo = async () => {
      if (userId) {
        const URL = `${process.env.REACT_APP_URL}/users/${userId}`;

        if (accessToken && refreshToken) {
          const res = await getFetch(URL, { accessToken, refreshToken });

          if (res) {
            dispatch(setIsLogin(true));
            dispatch(setUserInfo(res.data));

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
