import S_Container from '../../components/UI/S_Container';
import MainContents from '../../components/home/home/MainContents';
import HomeIntro from '../../components/home/home/HomeIntro';
import CreateClubImg from '../../components/home/home/CreateClubImg';
import getGlobalState from '../../util/authorization/getGlobalState';

function Home() {
  return (
    <S_Container>
      <HomeIntro />
      <MainContents />
      <CreateClubImg />
    </S_Container>
  );
}

export default Home;
