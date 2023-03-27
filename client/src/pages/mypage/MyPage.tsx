import ClubNo from '../../components/MyPage/ClubNo';
import ClubYes from '../../components/MyPage/ClubYes';
import UserProfile from '../../components/MyPage/UserProfile';
import { S_ButtonBlack } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { setIsLogin, UserClubResponsesType } from '../../store/store';
import { useNavigate } from 'react-router-dom';
import getGlobalState from '../../util/authorization/getGlobalState';

function MyPage() {
  // TODO : 로그아웃 구성
  const navigate = useNavigate();
  const { userInfo, tokens } = getGlobalState();

  const clickLogout = () => {
    setIsLogin(false);
    navigate('/');
  };

  return (
    <S_Container>
      <UserProfile userId={userInfo.userId} profileImage={userInfo.profileImage} />
      {/* 유저정보에 가입한 클럽 데이터 배열 길이가 0이면 -> ClubNo, 아니라면 -> ClubYes */}
      {userInfo.userClubResponses?.length > 0 ? (
        <ClubYes userClubResponses={userInfo.userClubResponses} />
      ) : (
        <ClubNo />
      )}

      {}
      {/* 버튼 클릭시 로그아웃&메인페이지로 */}
      <S_ButtonBlack onClick={clickLogout}>로그아웃</S_ButtonBlack>
    </S_Container>
  );
}

export default MyPage;
