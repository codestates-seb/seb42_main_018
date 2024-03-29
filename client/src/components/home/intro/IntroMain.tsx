import styled from 'styled-components';
import backImg from '../../../assets/intro_background.png';
import { S_Text, S_Title } from '../../UI/S_Text';

const S_IntroBox = styled.div`
  margin-top: 50px;
  padding-top: 60px;
  height: 490px;
  text-align: center;
  background-color: var(--blue300);
  background-size: 100%;
  line-height: 160%;
  img {
    width: 100%;
    animation: fadeinImg 1.2s;
    background-color: var(--blue300);
  }
  p {
    animation: fadein 1.5s;
  }
  h1 {
    animation: fadein 1.5s;
  }
  @keyframes fadeinImg {
    from {
      opacity: 0;
      transform: translate3d(0, 5%, 0);
    }
    to {
      opacity: 1;
      transform: translateZ(-5);
    }
  }
  @keyframes fadein {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

function IntroMain() {
  return (
    <S_IntroBox>
      <S_Title color='var(--white)'>소모임 경기 전적 보기, 소모전</S_Title>
      <S_Text color='var(--white)'>
        모임을 만들고, 경기 결과를 기록하고, <br />
        나와 모임원들의 승률을 확인해보세요!
      </S_Text>
      <img src={backImg} alt='배경이미지' />
    </S_IntroBox>
  );
}

export default IntroMain;
