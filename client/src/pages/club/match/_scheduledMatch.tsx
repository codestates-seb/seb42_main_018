import styled from 'styled-components';
import ScheduleCard from '../../../components/match/ScheduleCard';
import { S_Text } from '../../../components/UI/S_Text';

const S_MatchSchedule = styled.div``;
const S_List = styled.div``;

function ScheduledMatch() {
  return (
    <S_MatchSchedule>
      <S_Text>경기 시작 전까지 참석하기 버튼을 누를 수 있어요.</S_Text>
      <S_List>
        <ScheduleCard />
      </S_List>
    </S_MatchSchedule>
  );
}

export default ScheduledMatch;
