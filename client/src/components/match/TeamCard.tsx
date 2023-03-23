import { TeamList } from '../../pages/club/match/CreateMatch';
import { S_EditButton, S_NegativeButton } from '../UI/S_Button';
import { S_NameTag } from '../UI/S_Tag';
import { S_Text } from '../UI/S_Text';

interface TeamCardProps {
  teamList: TeamList[];
  team: TeamList;
  idx: number;
  deleteNameTagFromTeam: (idx: number, memberIdx: number) => void;
  openMemberListPopup: (idx: number, e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
  deleteTeam: (idx: number) => void;
}

function TeamCard(props: TeamCardProps) {
  return (
    <div style={{ display: 'flex', justifyContent: 'space-between' }}>
      <S_Text style={{ margin: '0' }}>{props.idx + 1}팀</S_Text>
      <div
        style={{
          flexGrow: 1,
          border: 'none',
          width: '60%',
          marginLeft: '3px',
          paddingLeft: '1px',
          marginBottom: '3px'
        }}
      >
        {props.teamList[props.idx].members.map((member, memberIdx) => (
          <S_NameTag
            key={memberIdx}
            onClick={() => {
              props.deleteNameTagFromTeam(props.idx, memberIdx);
            }}
          >
            {member}&times;
          </S_NameTag>
        ))}
      </div>
      <div style={{ height: '100%' }}>
        <S_EditButton
          onClick={(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
            e.stopPropagation();
            props.openMemberListPopup(props.idx, e);
          }}
        >
          추가
        </S_EditButton>
        <S_NegativeButton
          onClick={() => {
            props.deleteTeam(props.idx);
          }}
        >
          삭제
        </S_NegativeButton>
      </div>
    </div>
  );
}
export default TeamCard;
