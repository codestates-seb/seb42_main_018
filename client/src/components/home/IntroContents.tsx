import { Navigate, useNavigate } from "react-router-dom"
import styled from "styled-components"
import { S_Button } from "../UI/S_Button"
import { S_Label, S_Text, S_Title } from "../UI/S_Text"

const S_IntroBox = styled.div`
  padding: 40px 20px;
  background-color: var(--white);
`
const S_ContentsBox = styled.div`
  margin-bottom: 20px;
  padding: 30px 25px 25px 25px;
  border-radius: 10px;
  background-color: var(--gray100);
`
const S_ButtonBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 50px;
  text-align: center;
  height: 160px;
`
function IntroContents() {
  const navigate = useNavigate();
  const gotoLogin = () => {
    navigate('/login')
  }
  return (
    // 기획서 정리되면 소개글 페이지 다듬기
    <S_IntroBox>
      <S_ContentsBox>
        <S_Label>✏️ 모임 만들기</S_Label>
        <S_Text>다양한 카테고리의 모임을 만들 수 있어요</S_Text>
      </S_ContentsBox>
      <S_ContentsBox>
        <S_Label>🗓 경기 등록하기</S_Label>
        <S_Text>
          새 경기를 등록하고 참석 인원을 받아봐요<br/>
          이미 진행한 경기 결과도 등록할 수 있답니다
        </S_Text>
      </S_ContentsBox>
      <S_ContentsBox>
        <S_Label>📊 승률 비교하기</S_Label>
        <S_Text>같은 모임에 있는 멤버들과 승률을 비교해보세요</S_Text>
      </S_ContentsBox>
      <S_ButtonBox>
        <S_Title>로그인하고<br/> 소모전을 즐겨보세요</S_Title>
        <S_Button onClick={gotoLogin}>소모전 시작하기 +</S_Button>
      </S_ButtonBox>
    </S_IntroBox>
  )
}

export default IntroContents