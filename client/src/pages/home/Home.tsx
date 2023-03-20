import S_Container from "../../components/UI/S_Container";
import MainContents from "../../components/home/_MainContents";
import HomeIntro from "../../components/home/_HomeIntro";

function Home() {
    return (
        <S_Container>
            <HomeIntro />
            <MainContents />
        </S_Container>
    )
}

export default Home;