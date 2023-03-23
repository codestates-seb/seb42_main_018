import { FieldValues, UseFormRegister } from 'react-hook-form';
import { Record, TeamList } from '../../pages/club/match/CreateMatch';
import { S_NegativeButton } from '../UI/S_Button';
import { S_Input } from '../UI/S_Input';

interface RecordCardProps {
  idx: number;
  record: Record;
  teamList: TeamList[];
  deleteRecord: (idx: number, record: Record) => void;
  register: UseFormRegister<FieldValues>;
}

function RecordCard(props: RecordCardProps) {
  return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
      <span>{props.idx + 1}경기</span>

      <select {...props.register(`${props.record.id}.firstTeam`)}>
        {props.teamList &&
          props.teamList.map((team, idx) => {
            return <option key={idx + 1}>{idx + 1}</option>;
          })}
      </select>
      <span>팀</span>
      <S_Input
        {...props.register(`${props.record.id}.firstTeamScore`)}
        type='number'
        style={{ margin: '0', height: '30px' }}
      ></S_Input>
      <span>:</span>
      <S_Input
        {...props.register(`${props.record.id}.secondTeamScore`)}
        type='number'
        style={{ margin: '0', height: '30px' }}
      ></S_Input>
      <select {...props.register(`${props.record.id}.secondTeam`)}>
        {props.teamList &&
          props.teamList.map((team, idx) => {
            return <option key={idx + 2}>{idx + 1}</option>;
          })}
      </select>
      <span>팀</span>
      <S_NegativeButton
        onClick={() => {
          props.deleteRecord(props.idx, props.record);
        }}
      >
        삭제
      </S_NegativeButton>
    </div>
  );
}

export default RecordCard;
