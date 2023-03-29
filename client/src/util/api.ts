import axios from 'axios';
import store, { setUserInfo } from '../store/store';
import { JwtTokensType } from '../store/store';

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

    if (changeUserInfo && res) {
      const { userInfo, tokens } = store.getState();
      getFetch(`${process.env.REACT_APP_URL}/users/${userInfo.userId}`, tokens).then((resp) =>
        store.dispatch(setUserInfo(resp.data))
      );
    }

    if (res.status === 200 || res.status === 201) return res;
  } catch (err) {
    console.error(err);
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

    if (res.status === 204) return res;
  } catch (err) {
    console.log(err);
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
    if (res.status === 200) return res;
  } catch (err) {
    console.error(err);
  }
};
