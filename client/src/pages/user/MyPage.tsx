import ClubNo from '../../components/MyPage/ClubNo';
import ClubYes from '../../components/MyPage/ClubYes';
import UserProfile from '../../components/MyPage/UserProfile';
import { S_ButtonBlack } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { setIsLogin } from '../../store/store';
import { useNavigate } from 'react-router-dom';
import getGlobalState from '../../util/authorization/getGlobalState';
import EditProfile from './EditProfile';

function MyPage() {
  // TODO : 유저프로필 구성
  // TODO : 가지고 있는 클럽 정보에 따라 다른 화면 보여주기
  // TODO : 로그아웃 구성
  const navigate = useNavigate();
  const { isLogin, userInfo, tokens } = getGlobalState();

  const clickLogout = () => {
    setIsLogin(false);
    navigate('/');
  };

  return (
    <S_Container>
      <UserProfile
        userId={userInfo.userId}
        email={userInfo.email}
        nickName={userInfo.nickName}
        userStatus={userInfo.userStatus}
      />
      {/* 유저정보에 가입한 클럽 데이터가 있으면 -> ClubYes, 아니면 -> ClubNo */}
      {/* {userInfo.userId ? <ClubYes /> : <ClubNo />} */}
      {/* 아래 컴포는 화면에서 보기 위해 잠시 등록 */}
      <ClubNo />
      <ClubYes />
      {/* 버튼 클릭시 로그아웃&메인페이지로 */}
      <S_ButtonBlack onClick={clickLogout}>로그아웃</S_ButtonBlack>
      <EditProfile />
    </S_Container>
  );
}

export default MyPage;
