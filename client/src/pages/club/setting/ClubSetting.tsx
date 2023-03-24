import { useNavigate, useParams } from 'react-router-dom';
import S_Container from '../../../components/UI/S_Container';
import { S_Description, S_Label, S_Text, S_Title } from '../../../components/UI/S_Text';

function ClubSetting() {
  const navigate = useNavigate();
  const { id } = useParams();
  return (
    <S_Container>
      <S_Title style={{ marginBottom: '20px' }}>소모임 설정</S_Title>
      <S_Label fontSize='1.1rem' onClick={() => navigate(`/club/${id}/edit`)}>
        소모임 정보 수정
      </S_Label>
      <S_Label fontSize='1.1rem' onClick={() => navigate(`/club/${id}/setting/member`)}>
        회원 관리
      </S_Label>
      <hr
        style={{
          display: 'block',
          width: '100%',
          height: '1px',
          border: '0',
          borderTop: '1px solid var(--gray200)'
        }}
      />
      <S_Label fontSize='1.1rem'>소모임장 위임</S_Label>
      <S_Label fontSize='1.1rem' color='var(--red100)'>
        소모임 해체
      </S_Label>
      <S_Description>
        소모임 인원이 있을 경우 소모임을 해체할 수 없습니다.
        <br />
        해체를 원하실 경우 모든 소모임 인원을 탈퇴시켜주세요.
      </S_Description>
    </S_Container>
  );
}

export default ClubSetting;
