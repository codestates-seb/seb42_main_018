import S_Container from '../../components/UI/S_Container';
import MainContents from '../../components/home/MainContents';
import HomeIntro from '../../components/home/HomeIntro';
import CreateClubImg from '../../components/home/CreateClubImg';

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
