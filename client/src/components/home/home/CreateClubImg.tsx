import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { S_Label } from '../../UI/S_Text';

const S_ImgBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 90vh;
  background-color: var(--blue300);
  border-radius: 10px;
  padding: 17px 20px 10px 20px;
  position: absolute;
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
    <Link to='/club/create'>
      <S_ImgBox>
        <S_Label color='var(--white)'>직접 소모임 만들기</S_Label>
        <S_Plusbutton>+</S_Plusbutton>
      </S_ImgBox>
    </Link>
  );
}

export default CreateClubImg;
