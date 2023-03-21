import { useEffect, useState } from 'react';
import S_Container from './components/UI/S_Container';
import { clubType } from './pages/club/club/CreateClub';
import { getFetch, postFetch } from './util/api';

interface ImgFileType {
  lastModified: number;
  lastModifiedDate: object;
  name: string;
  size: number;
  type: string;
  webkitRelativePath: string;
}

function API_TEST() {
  const POST_URL = `${process.env.REACT_APP_URL}/upload/clubs`;
  const GET_URL = `${process.env.REACT_APP_URL}`;

  const [imgFile, setImgFile] = useState<ImgFileType>();
  const [dataFromServer, setDataFromServer] = useState('');

  useEffect(() => {
    async function getData() {
      const res = await getFetch(GET_URL);
      // console.log('get 응답 데이터: ', res);

      setDataFromServer(res);
    }
    getData();
  }, []);

  // console.log(dataFromServer);

  const onFileChange = ({ target: { files } }: any) => {
    const data = files[0];
    // console.log('이미지 파일 객체:', data);

    setImgFile(data);
  };

  const dataToServer: clubType = {
    clubName: '야구',
    content: '재밌게 야구해요',
    local: '경기도 구리시',
    categoryName: '창의적카테고리',
    tagName: ['태그1', '태그2'],
    isPrivate: true
  };

  const onSubmit = async (e: any) => {
    e.preventDefault();
    console.log(imgFile);
    const res = await postFetch(POST_URL, imgFile);
    console.log('post 요청 응답 데이터: ', res);
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
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => onFileChange(e)}
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
