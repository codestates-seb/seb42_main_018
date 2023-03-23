import DeleteAccount from '../../components/MyPage/DeleteAccount';
import { S_Button } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { S_Input } from '../../components/UI/S_Input';
import { S_Description, S_Label, S_Text } from '../../components/UI/S_Text';

function EditProfile() {
  return (
    <S_Container>
      <div>
        <S_Label>프로필사진</S_Label>
        <S_Label>닉네임</S_Label>
        <S_Description>닉네임은 언제든지 바꿀 수 있어요.</S_Description>
        <S_Input></S_Input>
        <S_Label>이메일</S_Label>
        <S_Text>이메일 주소 고정</S_Text>
        <S_Label>비밀번호</S_Label>
        <S_Input></S_Input>
        <S_Label>비밀번호 확인</S_Label>
        <S_Input></S_Input>
        <S_Button>회원정보 수정</S_Button>
        <DeleteAccount />
      </div>
    </S_Container>
  );
}

export default EditProfile;
