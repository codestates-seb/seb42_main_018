import S_Container from '../../components/UI/S_Container';
import Tabmenu from '../../components/TabMenu';

function EditPassword() {
  const tabs = [
    { id: 1, title: '프로필', path: `/mypage/edit` },
    { id: 2, title: '계정 설정', path: `/mypage/edit/password` },
    { id: 3, title: '회원 탈퇴', path: `/mypage/edit/account` }
  ];

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
    </S_Container>
  );
}

export default EditPassword;
