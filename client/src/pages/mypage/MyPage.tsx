import ClubNo from '../../components/MyPage/ClubNo';
import ClubYes from '../../components/MyPage/ClubYes';
import UserProfile from '../../components/MyPage/UserProfile';
import { S_ButtonBlack } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import { setIsLogin } from '../../store/store';
import { useNavigate } from 'react-router-dom';
import getGlobalState from '../../util/authorization/getGlobalState';
import LoginChecker from '../../components/LoginChecker';
import { useLogoutRequestLogic } from '../../util/authorization/useLogoutRequestLogic';

function MyPage() {
  // TODO : 로그아웃 구성
  const navigate = useNavigate();
  const { userInfo } = getGlobalState();
  const { handleLogout } = useLogoutRequestLogic();

  console.log(userInfo);
  // const clickLogout = () => {
  //   setIsLogin(false);
  //   navigate('/');
  // };

  // API 8번 유저 정보 조회 -> 항상 신규 정보를 받아오는
  // 상태로 받아온 배열 관리 -> 이 배열을 clubYes에 보내주기 상태 두개 다!

  return (
    <LoginChecker>
      <S_Container>
        <UserProfile nickName={userInfo.nickName} profileImage={userInfo.profileImage} />
        {/* 유저정보에 가입한 클럽 데이터 배열 길이가 0이면 -> ClubNo, 아니라면 -> ClubYes */}
        {/* 퍼미션 체커 제작시 추가 */}
        {userInfo.userClubResponses?.length > 0 ? <ClubYes /> : <ClubNo />}

        {/* 버튼 클릭시 로그아웃&메인페이지로 */}
        <S_ButtonBlack onClick={handleLogout}>로그아웃</S_ButtonBlack>
      </S_Container>
    </LoginChecker>
  );
}

export default MyPage;
