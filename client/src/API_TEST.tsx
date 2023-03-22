import axios, { AxiosResponse } from 'axios';
import FormData from 'form-data';
import { useState } from 'react';
import S_Container from './components/UI/S_Container';

interface ImgFileType {
  lastModified: number;
  lastModifiedDate?: object;
  name: string;
  size: number;
  type: string;
  webkitRelativePath: string;
}

function API_TEST() {
  const POST_URL = `${process.env.REACT_APP_URL}/upload/users`;

  const [imgFile, setImgFile] = useState<ImgFileType>();

  const onFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const data = e.target.files?.[0];
    // console.log('이미지 파일 객체:', data);
    if (data) {
      setImgFile(data);
    }
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (imgFile) {
      // TODO : any 처리해야함 (@types/form-data 패키지 설치했는데도 미해결)
      const formData: any = new FormData();
      formData.append('image', imgFile);

      console.log(formData);
      console.log(Array.from(formData.entries()));

      const config = {
        headers: {
          'content-type': `multipart/form-data; boundary=${formData._boundary}`
        }
      };

      try {
        const res: AxiosResponse<string> = await axios.post(POST_URL, formData, config);
        console.log('post 요청 응답 데이터: ', res);
      } catch (err) {
        console.error(err);
      }
    }
  };

  return (
    <S_Container>
      API_TEST
      <br />
      <br />
      <form onSubmit={onSubmit} style={{ marginBottom: '8px' }}>
        <input
          id='server'
          type='file'
          accept='image/png, image/jpeg, image/jpg'
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => onFileChange(e)}
        />
        <button type='submit'>제출</button>
      </form>
    </S_Container>
  );
}

export default API_TEST;
