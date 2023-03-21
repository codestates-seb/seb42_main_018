import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_Button, S_ButtonGray } from '../UI/S_Button';
import { S_Title } from '../UI/S_Text';

const S_Box = styled.div`
  background-color: var(--white);
  margin-bottom: 50px;
  div {
    margin-top: 10px;
  }
  button {
    margin: 8px 0px;
  }
`;

function ClubNo() {
  const navigate = useNavigate();
  return (
    <S_Box>
      <S_Title>아직 가입한 소모임이 없어요!</S_Title>
      <div>
        <S_Button onClick={() => navigate('/home')}>가입할 소모임 찾아보기</S_Button>
        <S_ButtonGray onClick={() => navigate('/club/create')}>내 소모임 만들기 +</S_ButtonGray>
      </div>
    </S_Box>
  );
}

export default ClubNo;
