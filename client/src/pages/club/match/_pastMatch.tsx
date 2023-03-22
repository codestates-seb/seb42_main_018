import styled from 'styled-components';
import ScheduleCard from '../../../components/match/ScheduleCard';
import { S_Text } from "../../../components/UI/S_Text";

const S_MatchSchedule = styled.div``;
const S_List = styled.div``;

function PastMatch() {
  return (
    <S_MatchSchedule>
      <S_Text>지난 경기는 클릭하여 자세히 볼 수 있어요.</S_Text>
      <S_List>
        <ScheduleCard />
      </S_List>
    </S_MatchSchedule>
  );
}

export default PastMatch;
