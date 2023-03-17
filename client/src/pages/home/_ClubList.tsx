import styled from 'styled-components'
import { S_EditButton } from '../../components/UI/S_Button'
import { S_TagSmall } from '../../components/UI/S_Tag'
import { S_Description, S_Label, S_SmallDescription, } from '../../components/UI/S_Text'
import { ClubProps, ClubTag } from '././_MainContents'


const S_ClubBox = styled.div`
  // 전체 컨테이너
  display: flex;
  padding: 20px 0px;
  background-color: var(--white);
  border-top: 1px solid var(--gray100);
`

const S_TitleBox = styled.div`
  // 제목 - 소모임 설정 버튼 정렬
  display: flex;
  flex-grow: 1;
  align-items: center;
  justify-content: space-between;
`
const S_ImgBox = styled.div<{img?: string}>`
  // 클럽 대표 이미지 썸네일크기로 자르기
  margin-right: 15px;
  min-width: 80px;
  height: 80px;
  border-radius: 10px;
  background-size: cover;
  background-position: center center;
  background-image: url(${(props) => props.img});
`
const S_ContentsBox = styled.div`
  // 제목 / 카테고리, 지역, 인원 / 설명 / 태그 박스
  width: 100%;
`
const S_Hidden = styled.div`
  // 설명글 길어지면 잘라주기
  display: -webkit-box;
  word-wrap: break-word;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
  margin-bottom: 5px;
`

function ClubList({clubName, clubImg, content, local, categoryName, memberCount, tagResponseDtos }: ClubProps) {
  {console.log(tagResponseDtos)}
  return (
    <S_ClubBox>
      <S_ImgBox img={clubImg} />
      <S_ContentsBox>
        <S_TitleBox>
          <S_Label>{clubName}</S_Label>
          <S_EditButton>소모임 설정</S_EditButton>
        </S_TitleBox>
        <S_SmallDescription>{categoryName} ・ {local} ・ 인원 {memberCount}명</S_SmallDescription>
        <S_Hidden><S_Description color='var(--gray600)'>{content}</S_Description></S_Hidden>
        <div>
          {tagResponseDtos.map((e) => (
            <S_TagSmall key={e.tagId}>{e.tagName}</S_TagSmall>
          ))}
        </div>
      </S_ContentsBox>
    </S_ClubBox>
  
  )
}

export default ClubList