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
  readOnly?: boolean;
}

function RecordCard(props: RecordCardProps) {
  return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
      <span>{props.idx + 1}경기</span>

      <select
        {...props.register(`${props.record.id}.firstTeamNumber`, {
          // value: props.record.firstTeamNumber
        })}
        defaultValue={String(props.record.firstTeamNumber)}
      >
        {props.teamList &&
          props.teamList.map((team, idx) => {
            return <option key={idx + 1}>{idx + 1}</option>;
          })}
      </select>
      <span>팀</span>
      <S_Input
        {...props.register(`${props.record.id}.firstTeamScore`, {
          value: props.record.firstTeamScore
        })}
        type='number'
        // value={props.record.firstTeamScore}
        style={{ margin: '0', height: '30px', textAlign: 'center' }}
      ></S_Input>
      <span>:</span>
      <S_Input
        {...props.register(`${props.record.id}.secondTeamScore`, {
          value: props.record.secondTeamScore
        })}
        type='number'
        // value={props.record.secondTeamScore}
        style={{ margin: '0', height: '30px', textAlign: 'center' }}
      ></S_Input>
      <select
        {...props.register(`${props.record.id}.secondTeamNumber`, {})}
        defaultValue={String(props.record.secondTeamNumber)}
      >
        {props.teamList &&
          props.teamList.map((team, idx) => {
            return (
              <option key={idx + 2} value={idx + 1}>
                {idx + 1}
              </option>
            );
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
