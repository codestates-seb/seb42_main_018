import axios from 'axios';
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
    return res.data;
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
