import styled from 'styled-components'
import { S_EditButton } from '../../components/UI/S_Button'
import { S_TagSmall } from '../../components/UI/S_Tag'
import { S_Description, S_Label, S_SmallDescription, } from '../../components/UI/S_Text'
import { ClubProps, ClubTag } from './Home'

const S_ClubBox = styled.div`
  // 전체 컨테이너
  display: flex;
  padding: 20px 0px;
  background-color: var(--white);
  border-top: 1px solid var(--gray100);
  .title {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
`
const S_ImgBox = styled.div<{img?: string}>`
  // 클럽 대표 이미지 썸네일크기로 자르기
  margin: 0px 20px;
  width: 80px;
  height: 80px;
  border-radius: 10px;
  background-size: cover;
  background-position: center center;
  background-image: url(${(props) => props.img});
`
function ClubList({clubName, content, local, categoryName, memberCount, tagResponseDtos }: ClubProps) {
  {console.log(tagResponseDtos)}
  return (
    <S_ClubBox>
      <S_ImgBox img='https://photoresources.wtatennis.com/photo-resources/2019/08/15/dbb59626-9254-4426-915e-57397b6d6635/tennis-origins-e1444901660593.jpg?width=1200&height=630' />
      <div>
        <div className='title'>
          <S_Label>{clubName}</S_Label>
          <S_EditButton>소모임 설정</S_EditButton>
        </div>
        <S_SmallDescription>{categoryName} ・ {local} ・ 인원 {memberCount}명</S_SmallDescription>
        <S_Description color='var(--gray600)'>{content}</S_Description>
        <div>
          {tagResponseDtos.map((e) => (
            <S_TagSmall key={e.tagId}>{e.tagName}</S_TagSmall>
          ))}
        </div>
      </div>
    </S_ClubBox>
  
  )
}

export default ClubList