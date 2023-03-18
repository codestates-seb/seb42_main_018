import S_Container from "../../components/UI/S_Container";
import MainContents from "./_MainContents";
import IntroContents from "./_IntroContents";

function Home() {
    return (
        <S_Container>
            <IntroContents />
            <MainContents />
        </S_Container>
    )
}

export default Home;