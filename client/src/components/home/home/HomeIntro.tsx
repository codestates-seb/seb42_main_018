import styled from 'styled-components';
import { S_Label, S_Description, S_SmallDescription } from '../../UI/S_Text';

const S_IntroBox = styled.div`
  // 정렬 설정
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  // 크기 설정
  margin: -20px -20px 0px -20px;
  padding: 20px;
  height: 160px;
  font-weight: 400;
  // 배경이미지 설정
  background-color: var(--blue300);
  background-size: 45%;
  background-repeat: no-repeat;
  background-position: right 20px;
  background-image: url('https://3dicons.sgp1.cdn.digitaloceanspaces.com/v1/dynamic/premium/trophy-dynamic-premium.png');
`;

const S_TextBox = styled.div`
  // 제목과 설명이 있는 박스
`;

function HomeIntro() {
  return (
    <S_IntroBox>
      <S_TextBox>
        <S_Label color='var(--white)'>소모임 경기 전적 보기, 소모전</S_Label>
        <S_Description color='var(--white)'>
          모임을 만들고, 경기 결과를 기록하고,
          <br />
          나와 모임원들의 승률을 확인해보세요!
        </S_Description>
      </S_TextBox>
      {/* '소모전 알아보기' 등은 일반적으로 서비스 소개 페이지로 이동됨. 현재 화면구성에는 없으므로 어떻게 처리할지 고민*/}
      <S_SmallDescription color='var(--blue200)'>소모전 알아보기</S_SmallDescription>
    </S_IntroBox>
  );
}

export default HomeIntro;
