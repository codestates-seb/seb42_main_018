import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Tabmenu from '../../components/TabMenu';
import { S_Button, S_ButtonBlack } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { ModalBackdrop } from '../../components/UI/S_Modal';
import { S_Description, S_Label, S_Title } from '../../components/UI/S_Text';
import { deleteFetch } from '../../util/api';
import getGlobalState from '../../util/authorization/getGlobalState';
import { S_ButtonBox, S_ConfirmModalContainer } from '../club/match/CreateMatch';
import { useLogoutRequestLogic } from '../../util/authorization/useLogoutRequestLogic';

const S_DeleteBox = styled.div`
  margin: 50px 0px;
  .box {
    margin-bottom: 20px;
  }
`;

function DeleteAccount() {
  const { userInfo, tokens } = getGlobalState();
  const navigate = useNavigate();
  const [isOpenDelete, setIsOpenDelete] = useState(false);
  const { handleLogout } = useLogoutRequestLogic();

  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];

  const checkDeleteAvailable = () => {
    const userRolesInAllClubs = userInfo.userClubResponses.map((el) => el.clubRole);
    if (userRolesInAllClubs.includes('LEADER')) return false;
    else return true;
  };

  const deleteUser = async () => {
    if (checkDeleteAvailable()) {
      if (tokens) {
        const res = await deleteFetch(
          `${process.env.REACT_APP_URL}/users/${userInfo.userId}`,
          tokens
        );
        if (res) {
          alert('탈퇴했습니다.');
          handleLogout(); // 추후 모달 처리
        }
      }
    } else {
      alert('탈퇴 조건이 충족되지 않았습니다. 주의사항을 다시 한 번 읽어주세요.');
      return;
    }
  };

  return (
    <>
      <S_Container>
        <Tabmenu tabs={tabs} />
        <S_DeleteBox>
          <S_Title color='var(--red100)'>회원탈퇴</S_Title>
          <div className='box'></div>
          <S_Label>주의사항 (필독)</S_Label>
          <S_Description>
            1. 회원 탈퇴 시에는 가입했던 소모임 활동 내역 및 정보가 완전히 삭제되어 재가입을 해도
            확인이 불가능합니다. 또한 여러 건의 신고 누적으로 탈퇴할 경우 동일한 정보로 다시 가입할
            수 없습니다.
          </S_Description>
          <S_Description>
            2. 본인이 소모임장인 모임이 있다면 소모임 설정 메뉴에서 다른 회원에게 위임하거나 소모임
            해체 후 탈퇴할 수 있습니다.
          </S_Description>
          <div className='box'></div>
          <S_ButtonBlack onClick={() => setIsOpenDelete(true)}>회원탈퇴</S_ButtonBlack>
        </S_DeleteBox>
      </S_Container>
      {isOpenDelete && (
        <ModalBackdrop>
          <S_ConfirmModalContainer>
            <S_Label>정말로 탈퇴 하시겠습니까?</S_Label>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  deleteUser();
                  setIsOpenDelete(false);
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
                  setIsOpenDelete(false);
                }}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_ConfirmModalContainer>
        </ModalBackdrop>
      )}
    </>
  );
}

export default DeleteAccount;
