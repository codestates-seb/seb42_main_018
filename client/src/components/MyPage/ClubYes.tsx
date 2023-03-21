import styled from 'styled-components';
import ClubList from '../home/ClubList';
import { S_Button, S_ButtonGray } from '../UI/S_Button';
import { S_Title } from '../UI/S_Text';

const S_Box = styled.div`
  margin-bottom: 50px;
  background-color: var(--blue100);
  div {
    margin-top: 10px;
  }
  button {
    margin: 8px 0px;
  }
`;

function ClubYes() {
  return (
    <S_Box>
      <div>
        <S_Title>내 소모임</S_Title>
        {/* 받아온 클럽 정보 뿌려주기 */}
        {/* {{받아온 데이터 이름}.map((e) => (
          <ClubList
            key={e.clubId}
            clubId={e.clubId}
            clubName={e.clubName}
            profileImage={e.profileImage}
            content={e.content}
            local={e.local}
            categoryName={e.categoryName}
            memberCount={e.memberCount}
            tagResponseDtos={e.tagResponseDtos}
          />
        ))} */}
      </div>
      <div>
        <S_Title>가입 대기 소모임</S_Title>
        {/* 받아온 클럽 정보 뿌려주기 */}
        {/* {{받아온 데이터 이름}.map((e) => (
          <ClubList
            key={e.clubId}
            clubId={e.clubId}
            clubName={e.clubName}
            profileImage={e.profileImage}
            content={e.content}
            local={e.local}
            categoryName={e.categoryName}
            memberCount={e.memberCount}
            tagResponseDtos={e.tagResponseDtos}
          />
        ))} */}
      </div>
    </S_Box>
  );
}

export default ClubYes;
