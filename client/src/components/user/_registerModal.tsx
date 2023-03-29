import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { handleKakaoLogin } from '../../util/authorization/handleSnsLogin';
import { ModalBackdrop, ModalContainer } from '../UI/S_Modal';
import { S_Label } from '../UI/S_Text';
import { S_EditButton } from '../UI/S_Button';
import logo from '../../assets/logo.svg';
import kakaoLogo from '../../assets/login_kakao.svg';
import naverLogo from '../../assets/login_naver.svg';

const S_RegisterModalContainer = styled(ModalContainer)`
  height: 220px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  & .logo {
    height: 4vh;
  }
`;

const S_SnsButtonBox = styled.div`
  width: 45%;
  margin-bottom: 12px;
  display: flex;
  justify-content: center;

  & > .sns-btn {
    width: 60px;
    height: 60px;
    border-radius: 50%;
  }
  & .kakao {
    background-image: url(${kakaoLogo});
  }
  & .naver {
    background-image: url(${naverLogo});
  }
`;

interface RegisterModalProps {
  showModal: boolean;
  handleModal: () => void;
}

function RegisterModal({ showModal, handleModal }: RegisterModalProps) {
  const navigate = useNavigate();

  return (
    <>
      {showModal && (
        <ModalBackdrop onClick={handleModal}>
          <S_RegisterModalContainer onClick={(e) => e.stopPropagation()}>
            <img src={logo} alt='소모전 로고' className='logo' />
            <S_Label>SNS로 간편하게 가입해보세요!</S_Label>
            <S_SnsButtonBox>
              <button className='sns-btn kakao' onClick={handleKakaoLogin}></button>
            </S_SnsButtonBox>
            <S_EditButton onClick={() => navigate('/register')}>이메일로 가입하기</S_EditButton>
          </S_RegisterModalContainer>
        </ModalBackdrop>
      )}
    </>
  );
}

export default RegisterModal;
