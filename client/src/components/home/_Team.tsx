import styled from "styled-components"
import { S_Label, S_Description, S_SmallDescription } from "../../components/UI/S_Text"
import TeamMember from "./_TeamMember"
import { teamMembers } from "./_TeamMemberData"

const S_TeamBox = styled.div`
  // 팀 소개 전체 박스
  padding: 60px 30px;
  background-color: var(--gray600);
`
const S_IntroBox = styled.div`
  // 소개글 박스
  margin-bottom: 20px;
`
const S_MemberBox = styled.div`
  display: flex;
  flex-wrap : wrap;
  justify-content: space-between;
  margin: 30px 0px;
`

function Team() {
  
  return (
    <S_TeamBox>
      <S_IntroBox>
        <S_Label color="var(--white)">소모전 팀 소개</S_Label>
        <S_Description color="var(--white)">
          소모전은 2023년 3월 3일 시작했습니다.<br/>
          프론트엔드 개발자 3명, 백엔드 개발자 3명이 <br/>
          함께 프로젝트를 만들었습니다.
        </S_Description>

      </S_IntroBox>
      <S_MemberBox>
        {teamMembers.map((e)=>(
          <TeamMember 
            key={e.name} 
            profileImg={e.profileImg} 
            position={e.position} 
            name={e.name}
            url={e.url}
          />
        ))}
      </S_MemberBox>
      <S_SmallDescription>© 2023 team SOMOJEON</S_SmallDescription>
    </S_TeamBox>
  )
}

export default Team