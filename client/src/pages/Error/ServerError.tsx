import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_Button } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Label, S_Description } from '../../components/UI/S_Text';

const S_Box = styled.div`
  margin-top: 8vh;
  margin-bottom: 5vh;
  text-align: center;
  img {
    width: 50%;
    margin-top: 3vh;
  }
`;
function ServerError() {
  const navigate = useNavigate();
  const buttonhandle = () => {
    navigate('/home');
  };

  return (
    <S_Container>
      <S_Box>
        <img
          src='https://3dicons.sgp1.cdn.digitaloceanspaces.com/v1/dynamic/color/flash-dynamic-color.png'
          alt='번개 아이콘'
        />
        <S_Label>오류가 발생했어요!</S_Label>
        <S_Description>
          정보를 받아올 수 없어요.
          <br />
          잠시 뒤 다시 시도해주세요.
        </S_Description>
      </S_Box>
      <S_Button onClick={buttonhandle}>홈으로 돌아가기</S_Button>
    </S_Container>
  );
}

export default ServerError;
