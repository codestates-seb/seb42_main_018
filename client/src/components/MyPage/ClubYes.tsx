import styled from 'styled-components';
import { myPageUserClubResponses } from '../../types';
import getGlobalState from '../../util/authorization/getGlobalState';

import { S_Description, S_Title } from '../UI/S_Text';
import { S_ButtonGray } from '../UI/S_Button';
import ClubListSetting from './ClubListSetting';
import { useNavigate } from 'react-router-dom';

const S_Box = styled.div`
  button {
    margin: 8px 0px;
  }
  .myclub {
    margin-bottom: 50px;
  }
  .notMyClub {
    margin-bottom: 50px;
  }
`;

interface ClubYesProps {
  userClubs: myPageUserClubResponses[];
  setUserClubs: React.Dispatch<React.SetStateAction<myPageUserClubResponses[]>>;
}

function ClubYes({ userClubs, setUserClubs }: ClubYesProps) {
  const navigate = useNavigate();
  const { userInfo } = getGlobalState();
  const { userClubResponses } = userInfo || {};

  return (
    <S_Box>
      <div className='myclub'>
        <S_Title>내 소모임</S_Title>
        {userClubs.map(
          (el) =>
            el.clubMemberStatus === 'MEMBER ACTIVE' &&
            el.clubRole !== null && (
              <ClubListSetting key={el.clubId} clubId={el.clubId} clubRole={el.clubRole} />
            )
        )}
        <S_ButtonGray onClick={() => navigate('/club/create')}>내 소모임 만들기 +</S_ButtonGray>
      </div>
      <div className='notMyClub'>
        <S_Title>가입 대기 소모임</S_Title>
        <S_Description>리더의 가입 승인을 기다리는 목록입니다</S_Description>
        {userClubs.map(
          (el) =>
            el.clubMemberStatus === null &&
            el.clubRole === null && (
              <ClubListSetting key={el.clubId} clubId={el.clubId} clubRole={el.clubRole} />
            )
        )}
      </div>
    </S_Box>
  );
}

export default ClubYes;
