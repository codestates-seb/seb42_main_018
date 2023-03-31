import axios, { AxiosResponse, AxiosError } from 'axios';
import store, { setUserInfo, setTokens, JwtTokensType } from '../store/store';
import { SESSION_STORAGE_JWT_TOKENS_KEY } from './commonConstants';

const verifyTokens = async (res: AxiosResponse) => {
  // * refreshToken 만료 (maxAge: 420 min)
  if (res.headers['refresh-token-expired'] === 'True') {
    window.location.replace(`${process.env.REACT_APP_CLIENT_URL}/login`);
  }

  // * accessToken 만료 (maxAge: 30 min)
  if (res.headers['access-token-expired'] === 'True') {
    const tokens = {
      accessToken: res.headers.authorization,
      refreshToken: res.headers.refresh
    };
    store.dispatch(setTokens(tokens));
    sessionStorage.setItem(SESSION_STORAGE_JWT_TOKENS_KEY, JSON.stringify(tokens));
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

    if (res) verifyTokens(res);

    if (res.status === 200) return res.data;
  } catch (err) {
    console.error(err);
  }
};

export const postFetch = async <T>(
  url: string,
  newData: T,
  tokens?: JwtTokensType,
  changeUserInfo?: boolean
) => {
  try {
    const res = await axios.post(url, newData, {
      headers: {
        'Content-Type': 'application/json',
        withCredentials: true,
        Authorization: tokens && tokens.accessToken,
        Refresh: tokens && tokens.refreshToken
      }
    });

    if (res) verifyTokens(res);

    if (changeUserInfo && res) {
      const { userInfo, tokens } = store.getState();
      getFetch(`${process.env.REACT_APP_URL}/users/${userInfo.userId}`, tokens).then((resp) =>
        store.dispatch(setUserInfo(resp.data))
      );
    }

    if (res.status === 200 || res.status === 201) return res;
  } catch (err: unknown) {
    console.error(err);

    if (err instanceof AxiosError && err.response) {
      const errorStatus = err.response.data.status;
      const errorMsg = err.response.data.message;
      if (errorStatus === 401 && errorMsg === 'Unauthorized') {
        alert('비밀번호가 맞지 않습니다.');
      }
    }
  }
};

export const patchFetch = async <T>(
  url: string,
  updateData: T,
  tokens?: JwtTokensType,
  changeUserInfo?: boolean,
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

    if (res) verifyTokens(res);

    if (changeUserInfo && res) {
      const { userInfo, tokens } = store.getState();
      getFetch(`${process.env.REACT_APP_URL}/users/${userInfo.userId}`, tokens).then((resp) =>
        store.dispatch(setUserInfo(resp.data))
      );
    }

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

    if (res) verifyTokens(res);
    if (res.status === 204) return res;
  } catch (err) {
    console.error(err);
  }
};

export const putFetch = async <T>(url: string, putData: T, tokens?: JwtTokensType) => {
  try {
    const res = await axios.put(url, putData, {
      headers: {
        withCredentials: true,
        Authorization: tokens?.accessToken,
        Refresh: tokens?.refreshToken
      }
    });

    if (res) verifyTokens(res);
    if (res.status === 200) return res;
  } catch (err) {
    console.error(err);
  }
};
