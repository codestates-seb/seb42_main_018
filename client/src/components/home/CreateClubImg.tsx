import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { S_Label, S_SmallDescription, S_Text, S_Title } from '../UI/S_Text';

const S_ImgBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 20px 0px;
  background-color: var(--blue300);
  border-radius: 10px;
  padding: 17px 20px 10px 20px;
`;
const S_Plusbutton = styled.div`
  font-size: 4rem;
  color: var(--white);
  :hover {
    color: var(--blue200);
  }
`;

function CreateClubImg() {
  return (
    <S_ImgBox>
      <S_Label color='var(--white)'>
        마음에 드는 소모임이 없나요?
        <br />
        직접 소모임을 만들어 보세요!
      </S_Label>
      <Link to='/club/create'>
        <S_Plusbutton>+</S_Plusbutton>
      </Link>
    </S_ImgBox>
  );
}

export default CreateClubImg;
