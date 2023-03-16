import { useEffect, useState } from 'react';
import axios from 'axios';
import S_Container from './components/UI/S_Container';
import { clubType } from './pages/club/club/CreateClub';

function API_TEST() {
  const POST_URL = 'https://dev.somojeon.site/clubs';
  const GET_URL = 'https://dev.somojeon.site';

  const getFetch = async (url: string) => {
    try {
      const res = await axios.get(url);
      return res.data;
    } catch (err) {
      console.error(err);
    }
  };

  const postFetch = async (url: string, newData: any) => {
    try {
      const res = await axios.post(url, newData);
      // status code 나 ok로 분기 더 명확하게 줘야함
      if (res) {
        return res;
      }
    } catch (err) {
      console.error(err);
    }
  };

  const [dataFromServer, setDataFromServer] = useState('');

  useEffect(() => {
    async function getData() {
      const res = await getFetch(GET_URL);
      console.log('get 응답 데이터: ', res);

      setDataFromServer(res);
    }
    getData();
  }, []);

  console.log(dataFromServer);

  interface ImgFileType {
    lastModified: number;
    lastModifiedDate: object;
    name: string;
    size: number;
    type: string;
    webkitRelativePath: string;
  }

  //   let imgFile: ImgFileType;

  //   const onFileChange = ({ target: { files } }: any) => {
  //     const data = files[0];
  //     console.log('이미지 파일 객체:', data);

  //     imgFile = data;
  //   };

  const dataToServer: clubType = {
    clubName: '야구',
    content: '재밌게 야구해요',
    local: '경기도 구리시',
    categoryName: '방탈출',
    tagName: ['태그1', '태그2'],
    isPrivate: true
  };

  const onSubmit = async (e: any) => {
    e.preventDefault();
    console.log('제출 버튼 클릭');
    const res = await postFetch(POST_URL, dataToServer);
    console.log('post 응답 데이터: ', res);
  };

  return (
    <S_Container>
      API_TEST
      <>
        <form onSubmit={onSubmit} style={{ marginBottom: '8px' }}>
          <input
            id='server'
            type='file'
            accept='image/png, image/jpeg, image/jpg'
            // onChange={(e: React.ChangeEvent<HTMLInputElement>) => onFileChange(e)}
          />
          <button>제출</button>
        </form>
        <hr></hr>
        <p>여기 아래에 서버에서 온 문자열이 떠야해요</p>
        <p>{dataFromServer}</p>
      </>
    </S_Container>
  );
}

export default API_TEST;
