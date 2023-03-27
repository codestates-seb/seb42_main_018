import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import NoItem from '../../components/club/member/NoItem';
import { S_Button } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Label, S_Description, S_Text } from '../../components/UI/S_Text';

const S_Box = styled.div`
  margin-top: 8vh;
  margin-bottom: 5vh;
  text-align: center;
  img {
    width: 60%;
    margin-top: 3vh;
  }
`;
function NotFound() {
  const navigate = useNavigate();
  const buttonhandle = () => {
    navigate('/home');
  };

  return (
    <S_Container>
      <S_Box>
        <img
          src='https://3dicons.sgp1.cdn.digitaloceanspaces.com/v1/dynamic/color/map-pin-dynamic-color.png'
          alt='지도 아이콘'
        />
        <S_Label>길을 잃은 것 같아요!</S_Label>
        <S_Description>
          주소가 잘못 입력되거나 삭제되어
          <br />
          페이지를 찾을 수 없어요
        </S_Description>
      </S_Box>
      <S_Button onClick={buttonhandle}>홈으로 돌아가기</S_Button>
    </S_Container>
  );
}

export default NotFound;
