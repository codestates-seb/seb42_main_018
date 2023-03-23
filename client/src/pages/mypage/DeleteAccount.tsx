import styled from 'styled-components';
import Tabmenu from '../../components/TabMenu';
import { S_ButtonBlack } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Description, S_Label, S_SmallDescription, S_Title } from '../../components/UI/S_Text';

const S_DeleteBox = styled.div`
  margin: 50px 0px;
  .box {
    margin-bottom: 20px;
  }
`;

function DeleteAccount() {
  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];
  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <S_DeleteBox>
        <S_Title color='var(--red100)'>회원탈퇴</S_Title>
        <div className='box'></div>
        <S_Label>주의사항 (필독)</S_Label>
        <S_Description>
          회원 탈퇴 시에는 가입했던 소모임 활동 내역 및 정보가 완전히 삭제되어 재가입을 해도 확인이
          불가능합니다. 또한 여러 건의 신고 누적으로 탈퇴할 경우 동일한 정보로 다시 가입할 수
          없습니다.
        </S_Description>
        <div className='box'></div>
        <S_ButtonBlack>회원탈퇴</S_ButtonBlack>
      </S_DeleteBox>
    </S_Container>
  );
}

export default DeleteAccount;
