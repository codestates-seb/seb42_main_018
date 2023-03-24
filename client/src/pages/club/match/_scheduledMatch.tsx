import styled from 'styled-components';
import ScheduleCard from '../../../components/match/ScheduleCard';
import { S_Text } from '../../../components/UI/S_Text';
import { Schedule } from './ClubSchedule';

const S_MatchSchedule = styled.div``;
const S_List = styled.div``;

interface ScheduledMatchProps {
  schedule: Schedule[];
}

function ScheduledMatch(props: ScheduledMatchProps) {
  return (
    <S_MatchSchedule>
      <S_Text>경기 시작 전까지 참석하기 버튼을 누를 수 있어요.</S_Text>
      <S_List>
        {props.schedule.map((el) => {
          return (
            <ScheduleCard
              key={el.scheduleId}
              scheduleId={el.scheduleId}
              date={el.date}
              time={el.time}
              placeName={el.placeName}
            />
          );
        })}
      </S_List>
    </S_MatchSchedule>
  );
}

export default ScheduledMatch;
