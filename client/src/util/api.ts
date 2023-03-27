import axios, { AxiosResponse } from 'axios';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { JwtTokensType, setTokens } from '../store/store';

const refreshAccessToken = async (res: AxiosResponse, tokens: JwtTokensType) => {
  console.log('api.ts 파일', res);
  console.log(res.headers['access-token-expired']);
  console.log(typeof res.headers['access-token-expired']); // string

  // * accessToken 만료 (maxAge: 30 min)
  if (res.headers['Access-Token-Expired'] === 'True') {
    const REFRESH_URL = `${process.env.REACT_APP_URL}/auth/refresh`;
    const res = await axios.post(REFRESH_URL, '', {
      headers: {
        // 'Content-Type': 'application/json',
        withCredentials: true,
        Authorization: tokens && tokens.accessToken,
        Refresh: tokens && tokens.refreshToken
      }
    });

    // * refreshToken 만료 (maxAge: 420 min)
    if (res.headers['Refresh-Token-Expired'] === 'True') {
      const navigate = useNavigate();
      navigate('/login');
    }

    // * refreshToken은 유효해서 accessToken이 새로 발급됨
    if (res.status === 200) {
      const dispatch = useDispatch();
      dispatch(
        setTokens({
          accessToken: res.headers.authorization,
          refreshToken: res.headers.refresh
        })
      );
    }

    // TODO: 원래 하려고 했던 요청 재시도
  }
};

export const getFetch = async (url: string, tokens?: JwtTokensType) => {
  try {
    const res = await axios.get(url, {
      headers: {
        'Content-Type': 'application/json',
        withCredentials: true,
        Authorization: tokens && tokens.accessToken,
        Refresh: tokens && tokens.refreshToken
      }
    });

    if (tokens) refreshAccessToken(res, tokens);

    if (res.status === 200) return res.data;
  } catch (err) {
    console.error(err);
  }
};

export const postFetch = async <T>(url: string, newData: T, tokens?: JwtTokensType) => {
  try {
    const res = await axios.post(url, newData, {
      headers: {
        'Content-Type': 'application/json',
        withCredentials: true,
        Authorization: tokens && tokens.accessToken,
        Refresh: tokens && tokens.refreshToken
      }
    });

    if (tokens) refreshAccessToken(res, tokens);

    if (res.status === 200 || res.status === 201) return res;
  } catch (err) {
    console.error(err);
  }
};

export const patchFetch = async <T>(
  url: string,
  updateData: T,
  tokens?: JwtTokensType,
  contentType?: string
) => {
  try {
    const res = await axios.patch(url, updateData, {
      headers: {
        'Content-Type': contentType ? contentType : 'application/json',
        withCredentials: true,
        Authorization: tokens?.accessToken,
        Refresh: tokens?.refreshToken
      }
    });

    if (tokens) refreshAccessToken(res, tokens);

    if (res.status === 200) return res;
  } catch (err) {
    console.error(err);
  }
};

export const deleteFetch = async (url: string, tokens?: JwtTokensType) => {
  try {
    const res = await axios.delete(url, {
      headers: {
        withCredentials: true,
        Authorization: tokens?.accessToken,
        Refresh: tokens?.refreshToken
      }
    });

    if (tokens) refreshAccessToken(res, tokens);

    if (res.status === 204) return res;
  } catch (err) {
    console.log(err);
  }
};
