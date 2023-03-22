import S_Container from '../../components/UI/S_Container';
import MainContents from '../../components/home/MainContents';
import HomeIntro from '../../components/home/HomeIntro';
import CreateClubImg from '../../components/home/CreateClubImg';
import getGlobalState from '../../util/authorization/getGlobalState';

function Home() {
  const { isLogin, userInfo, tokens } = getGlobalState();
  console.log(isLogin);
  console.log(userInfo);
  console.log(tokens);
  return (
    <S_Container>
      <HomeIntro />
      <MainContents />
      <CreateClubImg />
    </S_Container>
  );
}

export default Home;
