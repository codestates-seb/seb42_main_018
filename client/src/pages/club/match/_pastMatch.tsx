import styled from 'styled-components';
import ScheduleCard from '../../../components/match/ScheduleCard';
import { S_Text } from '../../../components/UI/S_Text';
import { Schedule } from './ClubSchedule';

const S_MatchSchedule = styled.div``;
const S_List = styled.div``;

interface PastMatchProps {
  schedule: Schedule[];
}

function PastMatch(props: PastMatchProps) {
  return (
    <S_MatchSchedule>
      <S_Text style={{ marginBottom: '20px' }}>지난 경기는 클릭하여 자세히 볼 수 있어요.</S_Text>
      <S_List>
        {props.schedule.map((el) => {
          return (
            <ScheduleCard
              key={el.scheduleId}
              clubId={el.clubId}
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

export default PastMatch;
