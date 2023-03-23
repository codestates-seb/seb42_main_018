import styled from 'styled-components';
import { S_ButtonBlack } from '../UI/S_Button';
import { S_Description, S_Label, S_SmallDescription } from '../UI/S_Text';

const S_DeleteBox = styled.div`
  margin: 30px 0px;
`;

function DeleteAccount() {
  return (
    <S_DeleteBox>
      <S_Label color='var(--red100)'>회원탈퇴</S_Label>
      <S_Description color='var(--gray500)'>주의사항 (필독)</S_Description>
      <S_Description>
        회원 탈퇴 시에는 가입했던 소모임 활동 내역 및 정보가 완전히 삭제되어 재가입을 해도 확인이
        불가능합니다. 또한 여러 건의 신고 누적으로 탈퇴할 경우 동일한 정보로 다시 가입할 수
        없습니다.
      </S_Description>
      <S_ButtonBlack>회원탈퇴</S_ButtonBlack>
    </S_DeleteBox>
  );
}

export default DeleteAccount;
