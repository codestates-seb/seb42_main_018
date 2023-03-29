import ClubNo from '../../components/MyPage/ClubNo';
import ClubYes from '../../components/MyPage/ClubYes';
import UserProfile from '../../components/MyPage/UserProfile';
import { S_ButtonBlack } from '../../components/UI/S_Button';
import S_Container from '../../components/UI/S_Container';
import getGlobalState from '../../util/authorization/getGlobalState';
import LoginChecker from '../../components/LoginChecker';
import { useLogoutRequestLogic } from '../../util/authorization/useLogoutRequestLogic';
import { useEffect, useState } from 'react';
import { getFetch } from '../../util/api';
import { myPageUserClubResponses } from '../../types';

function MyPage() {
  const { isLogin, userInfo, tokens } = getGlobalState();
  const { handleLogout } = useLogoutRequestLogic();
  const [userClubs, setUserClubs] = useState<myPageUserClubResponses[]>([]);

  if (isLogin) {
    useEffect(() => {
      getFetch(`${process.env.REACT_APP_URL}/users/${userInfo.userId}`, tokens).then((data) => {
        const userClubs: myPageUserClubResponses[] = data.data.userClubResponses;
        setUserClubs(userClubs);
      });
    }, []);
  }

  return (
    <LoginChecker>
      <S_Container>
        {userInfo && (
          <UserProfile nickName={userInfo.nickName} profileImage={userInfo.profileImage} />
        )}
        {userClubs?.length > 0 ? (
          <ClubYes userClubs={userClubs} setUserClubs={setUserClubs} />
        ) : (
          <ClubNo />
        )}
        <S_ButtonBlack onClick={handleLogout}>로그아웃</S_ButtonBlack>
      </S_Container>
    </LoginChecker>
  );
}

export default MyPage;
