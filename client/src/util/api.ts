import axios from 'axios';

export const getFetch = async (url: string) => {
  try {
    const res = await axios.get(url);
    return res.data;
  } catch (err) {
    console.error(err);
  }
};

export const postFetch = async (url: string, newData: any) => {
  try {
    const res = await axios.post(url, JSON.stringify(newData), {
      headers: {
        'Content-Type': 'application/json',
        withCredentials: true
      }
    });

    // ? res.ok 왜 안 될까?
    if (res.status === 200 || res.status === 201) {
      return res;
    }
  } catch (err) {
    console.error(err);
  }
};
