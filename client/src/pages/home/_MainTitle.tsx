import styled from 'styled-components'
import { S_Title } from '../../components/UI/S_Text'

const S_TitleBox = styled.div`
  // 타이틀영역 전체 박스
  padding: 20px 0px;
`
const S_TagBox = styled.div`
  // 카테고리 태그 박스
  margin-top: 10px;
`
const S_Category = styled.span`
  // 카테고리 목록
  padding: 6px 11px;
  margin-right: 5px;
  border: 1px solid var(--gray300);
  border-radius: 50px;
  color: var(--gray300);
  font-size: 0.9rem;
  font-weight: 600;
`
function MainTitle() {
  // TODO: 카테고리 목록 받아오고, 클릭하면 해당 카테고리의 소모임만 보여주는 필터
  return (
    <S_TitleBox>
      <S_Title>어떤 소모임이 있을까요?</S_Title>
      <S_TagBox>
        <S_Category>전체</S_Category>
        <S_Category>배드민턴</S_Category>
      </S_TagBox>
    </S_TitleBox>
  )
}

export default MainTitle