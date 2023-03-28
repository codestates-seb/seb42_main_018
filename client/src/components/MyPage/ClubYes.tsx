import styled from 'styled-components';
import { myPageUserClubResponses } from '../../types';
import getGlobalState from '../../util/authorization/getGlobalState';

import { S_Title } from '../UI/S_Text';
import ClubListSetting from './ClubListSetting';

const S_Box = styled.div`
  margin-bottom: 50px;
  button {
    margin: 8px 0px;
  }
`;

interface ClubYesProps {
  userClubs: myPageUserClubResponses[];
  setUserClubs: React.Dispatch<React.SetStateAction<myPageUserClubResponses[]>>;
}

function ClubYes({ userClubs, setUserClubs }: ClubYesProps) {
  const { userInfo } = getGlobalState();
  const { userClubResponses } = userInfo || {};
  console.log(userClubResponses);

  return (
    <S_Box>
      <div className='myclub'>
        <S_Title>내 소모임</S_Title>
        {userClubs.map(
          (el) =>
            el.clubRole !== null && (
              <ClubListSetting key={el.clubId} clubId={el.clubId} clubRole={el.clubRole} />
            )
        )}
      </div>
      <div className='notMyClub'>
        <S_Title>가입 대기 소모임</S_Title>
        {userClubs.map(
          (el) =>
            el.clubRole === null && (
              <ClubListSetting key={el.clubId} clubId={el.clubId} clubRole={el.clubRole} />
            )
        )}
      </div>
    </S_Box>
  );
}

export default ClubYes;
