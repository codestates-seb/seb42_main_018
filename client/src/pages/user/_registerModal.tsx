import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { handleKakaoLogin } from '../../util/snsLoginLogic';
import alertPreparingService from '../../util/alertPreparingService';
import { ModalBackdrop, ModalContainer } from '../../components/UI/S_Modal';
import { S_Label } from '../../components/UI/S_Text';
import { S_EditButton } from '../../components/UI/S_Button';
import logo from '../../assets/logo.svg';

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
  justify-content: space-between;

  & > .sns-btn {
    width: 60px;
    height: 60px;
    background-color: coral;
    border-radius: 50%;
  }
  & .kakao {
    background-color: var(--kakao-main-theme);
  }
  & .naver {
    background-color: var(--naver-main-theme);
  }
`;

interface RegisterModalProps {
  showModal: boolean;
  handleModal: () => void;
}

function RegisterModal({ showModal, handleModal }: RegisterModalProps) {
  const navigate = useNavigate();
  const goToRegister = () => {
    navigate('/register');
  };
  return (
    <>
      {showModal && (
        <ModalBackdrop onClick={handleModal}>
          <S_RegisterModalContainer onClick={(e) => e.stopPropagation()}>
            <img src={logo} alt='소모전 로고' className='logo' />
            <S_Label>SNS로 간편하게 가입해보세요!</S_Label>
            <S_SnsButtonBox>
              <button className='sns-btn kakao' onClick={handleKakaoLogin}></button>
              <button className='sns-btn naver' onClick={alertPreparingService}></button>
            </S_SnsButtonBox>
            <S_EditButton onClick={goToRegister}>이메일로 가입하기</S_EditButton>
          </S_RegisterModalContainer>
        </ModalBackdrop>
      )}
    </>
  );
}

export default RegisterModal;
