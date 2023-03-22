import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { S_Label, S_Description, S_SmallDescription } from '../UI/S_Text';
import TeamMember from './TeamMember';
import { teamMembers } from './TeamMemberData';

const S_TeamBox = styled.div`
  // 팀 소개 전체 박스
  padding: 60px 30px;
  background-color: var(--gray600);
`;
const S_IntroBox = styled.div`
  // 소개글 박스
  margin-bottom: 20px;
`;
const S_LinkButton = styled.button`
  // git 리포, notion 페이지 버튼
  margin-top: 10px;
  margin-right: 10px;
  padding: 7px 12px;
  color: var(--gray300);
  border-radius: 8px;
  border: 1px solid var(--gray300);
  background-color: var(--gray600);
  transition: 0.3s;
  :hover {
    color: var(--gray600);
    background-color: var(--gray300);
  }
`;
const S_MemberBox = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin: 30px 0px;
`;

function Team() {
  return (
    <S_TeamBox>
      <S_IntroBox>
        <S_Label color='var(--white)'>소모전 팀 소개</S_Label>
        <S_Description color='var(--white)'>
          소모전은 2023년 3월 3일 시작했습니다.
          <br />
          프론트엔드 개발자 3명, 백엔드 개발자 3명이 <br />
          함께 프로젝트를 만들었습니다.
        </S_Description>
        <div>
          <Link to='https://github.com/codestates-seb/seb42_main_018'>
            <S_LinkButton>GitHub Repo</S_LinkButton>
          </Link>
          {/* <Link to='https://www.notion.so/codestates/9c70acd6b4c74f37ab14ea78d02f97f7?pvs=4'>
            <S_LinkButton>Team Notion</S_LinkButton>
          </Link> */}
        </div>
      </S_IntroBox>
      <S_MemberBox>
        {teamMembers.map((e) => (
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
  );
}

export default Team;
