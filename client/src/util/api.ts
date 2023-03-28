import axios, { AxiosResponse } from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch } from '../store/store';
import { JwtTokensType, setTokens } from '../store/store';

const refreshTokens = async (res: AxiosResponse, tokens: JwtTokensType) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  // * refreshToken 만료 (maxAge: 420 min)
  if (res.headers['expired'] === 'True') {
    navigate('/login');
  }

  if (res.headers.authorization && res.headers.refresh) {
    dispatch(
      setTokens({
        accessToken: res.headers.authorization,
        refreshToken: res.headers.refresh
      })
    );
  }

  // console.log('api.ts 파일', res);
  // console.log(res.headers['access-token-expired']);
  // console.log(typeof res.headers['access-token-expired']); // string
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

    if (res.status === 200) return res.data;
  } catch (err: unknown) {
    // console.error(err);
    // console.log(err.code);
    // console.log(err.message);
    // return err.message;
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

    if (res.status === 204) return res;
  } catch (err) {
    console.log(err);
  }
};

export const putFetch = async <T>(url: string, putData: T) => {
  try {
    const res = await axios.put(url, putData);
    if (res.status === 200) return res;
  } catch (err) {
    console.error(err);
  }
};
