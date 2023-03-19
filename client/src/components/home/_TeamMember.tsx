import styled from "styled-components"
import { S_SmallDescription, S_Text } from "../../components/UI/S_Text"
import { Members } from "./_TeamMemberData"

const S_MemberBox = styled.div`
  // 팀 멤버 박스
  display: flex;
  align-items: center;
  width: 210px;
  padding: 20px 0px;
  line-height: 110%;
  border-bottom: 1px solid var(--gray600);
  transition: 0.3s;
  :hover {
    border-bottom: 1px solid var(--gray400) ;
  }
`
const S_ImgBox = styled.div<{img?: string}>`
  // 팀원 프로필 이미지
  width: 50px;
  height: 50px;
  margin-right: 15px;
  border-radius: 50px;
  background-size: cover;
  background-position: center center;
  background-image: url(${(props) => props.img});
`

const S_NameBox = styled.div`
  // 팀원정보와 이름
`

function TeamMember({profileImg, position, name, url} : Members) {
  return (
    <a href={url}>
    <S_MemberBox>
      <S_ImgBox img={profileImg}/>
      <S_NameBox>
        <S_SmallDescription>{position}</S_SmallDescription>
        <S_Text color="var(--white)">{name}</S_Text>
      </S_NameBox>
    </S_MemberBox>
    </a>
  )
}

export default TeamMember