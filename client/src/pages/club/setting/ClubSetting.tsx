import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { S_Button } from '../../../components/UI/S_Button';
import S_Container from '../../../components/UI/S_Container';
import { ModalBackdrop } from '../../../components/UI/S_Modal';
import { S_Description, S_Label, S_Title } from '../../../components/UI/S_Text';
import { deleteFetch, getFetch } from '../../../util/api';
import getGlobalState from '../../../util/authorization/getGlobalState';
import { S_ButtonBox, S_ConfirmModalContainer } from '../match/CreateMatch';
import { MemberUser } from './_totalMember';

function ClubSetting() {
  const navigate = useNavigate();
  const { tokens, userInfo } = getGlobalState();
  const { id } = useParams();

  const myClub = userInfo.userClubResponses?.find((club) => club.clubId === Number(id));
  const isLeader = myClub?.clubRole === 'LEADER';

  const [totalMembers, setTotalMembers] = useState<MemberUser[]>([]);
  const [isOpenDisband, setIsOpenDisband] = useState(false);

  const checkPossible = () => {
    console.log(totalMembers);
    if (totalMembers?.length !== 1) {
      alert('다른 멤버가 존재합니다. 소모임 해체를 할 수 없습니다.');
      return false;
    }
    return true;
  };

  const getMembers = async () => {
    await getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/members`, tokens).then((data) => {
      setTotalMembers(data.data);
    });
  };

  const disbandClub = () => {
    if (checkPossible()) {
      deleteFetch(`${process.env.REACT_APP_URL}/clubs/${id}`, tokens);
      navigate('/home');
    }
    return;
  };

  useEffect(() => {
    if (!isLeader) {
      alert('권한이 없습니다.');
      navigate(`/club/${id}`);
    }
  }, []);

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
      {/* <S_Label fontSize='1.1rem'>소모임장 위임</S_Label> */}
      <S_Label
        fontSize='1.1rem'
        color='var(--red100)'
        onClick={() => {
          getMembers();
          setIsOpenDisband(true);
        }}
      >
        소모임 해체
      </S_Label>
      <S_Description>
        소모임 인원이 있을 경우 소모임을 해체할 수 없습니다.
        <br />
        해체를 원하실 경우 모든 소모임 인원을 탈퇴시켜주세요.
      </S_Description>
      {isOpenDisband && (
        <ModalBackdrop>
          <S_ConfirmModalContainer style={{ height: 'auto' }}>
            <div style={{ width: '90%' }}>
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <S_Label style={{ textAlign: 'left' }}>정말로</S_Label>
                {/* <S_Description style={{ textAlign: 'right' }}>&nbsp;님을</S_Description> */}
              </div>
              {/* <S_SmallDescription style={{ textAlign: 'left' }}>
              </S_SmallDescription> */}
              <S_Description style={{ textAlign: 'right' }}>
                소모임을 해체 하시겠습니까?
              </S_Description>
            </div>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  disbandClub();
                  setIsOpenDisband(false);
                }}
              >
                확인
              </S_Button>
              <S_Button
                addStyle={{
                  width: '48%',
                  backgroundColor: 'var(--gray100)',
                  color: 'var(--gray400)',
                  hoverBgColor: 'var(--gray200)'
                }}
                onClick={() => {
                  setIsOpenDisband(false);
                }}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_ConfirmModalContainer>
        </ModalBackdrop>
      )}
    </S_Container>
  );
}

export default ClubSetting;
