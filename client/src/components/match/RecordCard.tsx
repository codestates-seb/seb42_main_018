import { Record, TeamList } from '../../pages/club/match/CreateMatch';
import { S_NegativeButton } from '../UI/S_Button';
import { S_Input } from '../UI/S_Input';

interface RecordCardProps {
  round: number;
  record: Record;
  teamList: TeamList[];
  onClickDelete: () => void;
  onChangeField: (param: { key: keyof Record; value: string }) => void;
}

function RecordCard({ record, teamList, onClickDelete, onChangeField, round }: RecordCardProps) {
  return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
      <span>{round}경기</span>

      <select
        onChange={(e) => onChangeField({ key: 'firstTeam', value: e.target.value })}
        value={record.firstTeam}
      >
        {teamList.map((team) => {
          return <option key={team.id}>{team.id}</option>;
        })}
      </select>
      <span>팀</span>
      <S_Input
        type='number'
        style={{ margin: '0', height: '30px' }}
        onChange={(e) => onChangeField({ key: 'firstTeamScore', value: e.target.value })}
        value={record.firstTeamScore}
      ></S_Input>
      <span>:</span>
      <S_Input
        onChange={(e) => onChangeField({ key: 'secondTeamScore', value: e.target.value })}
        type='number'
        style={{ margin: '0', height: '30px' }}
        value={record.secondTeamScore}
      ></S_Input>
      <select onChange={(e) => onChangeField({ key: 'secondTeam', value: e.target.value })}>
        {teamList.map((team) => {
          return <option key={team.id}>{team.id}</option>;
        })}
      </select>
      <span>팀</span>
      <S_NegativeButton onClick={onClickDelete}>삭제</S_NegativeButton>
    </div>
  );
}

export default RecordCard;
