import styled from 'styled-components';
import { UserClubResponsesType } from '../../store/store';
import { getFetch } from '../../util/api';

import { S_Title } from '../UI/S_Text';
import ClubListSetting from './ClubListSetting';

const S_Box = styled.div`
  margin-bottom: 50px;
  button {
    margin: 8px 0px;
  }
`;

interface ClubResponses {
  userClubId: number;
  isPlayer: boolean;
  clubRole: string;
  playCount: number;
  winCount: number;
  winRate: number;
}

function ClubYes() {
  // 1. 받아온 userClubResponses에서
  // 2. 각 객체별 clubId를 가져와
  // 3. 해당 주소로 get요청 보내고
  // 4. 그렇게 받아온 클럽데이터를
  // 5. 클럽리스트로 보여주기
  // 5-1. clubRole이 멤버면 '탈퇴하기'만
  // 5-2. clubRole이 LEADER면 '클럽설정' 버튼

  return (
    <S_Box>
      <div className='myclub'>
        <S_Title>내 소모임</S_Title>
        <ClubListSetting key={1} userClubId={1} clubRole='LEADER' />
        {/* {data.map((el) => {
          <ClubListSetting key={el.userClubId} userClubId={el.userClubId} clubRole={el.clubRole} />;
        })} */}
        {/* 받아온 클럽 정보 뿌려주기 */}
      </div>
      <S_Title>가입 대기 소모임</S_Title>
    </S_Box>
  );
}

export default ClubYes;
