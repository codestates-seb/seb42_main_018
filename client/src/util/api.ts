import axios from 'axios';

export const getFetch = async (url: string) => {
  try {
    const res = await axios.get(url);
    return res.data;
  } catch (err) {
    console.error(err);
  }
};

// ? data의 타입 여러 가지인데 이 경우 타입 지정 어떻게 해결?
export const postFetch = async (url: string, newData: any) => {
  try {
    const res = await axios.post(url, JSON.stringify(newData), {
      headers: {
        'Content-Type': 'application/json',
        withCredentials: true
      }
    });

    if (res.status === 200 || res.status === 201) {
      return res;
    }
  } catch (err) {
    console.error(err);
  }
};