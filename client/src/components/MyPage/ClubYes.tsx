import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { UserClubResponsesType } from '../../store/store';
import { ClubData } from '../../types';
import { getFetch } from '../../util/api';
import ClubList from '../home/ClubList';
import { S_Button, S_ButtonGray } from '../UI/S_Button';
import { S_Title } from '../UI/S_Text';
import ClubListSetting from './ClubListSetting';

const S_Box = styled.div`
  margin-bottom: 50px;
  background-color: var(--blue100);
  button {
    margin: 8px 0px;
  }
`;

interface ClubYesProps {
  userClubResponses?: UserClubResponsesType[];
}

function ClubYes(userClubResponses: ClubYesProps) {
  // 받아온 userClubResponses에서 userClubId만 빼내서
  // 해당 url 수만큼 get 요청 보내기
  // 가져온 데이터를 ClubList에 뿌려주기
  // user상태에 따라 가입한 소모임/가입대기 소모임 나누기
  // clubRole이 리더면 리더 별 아이콘과 소모임 설정 보여주기
  // 아니라면 소모임 탈퇴만 보여주기

  const [clubs, setClubs] = useState<ClubData[]>([]); // 뿌려줄 클럽리스트

  return (
    <S_Box>
      <div>
        <S_Title>내 소모임</S_Title>
        <ClubListSetting />
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
