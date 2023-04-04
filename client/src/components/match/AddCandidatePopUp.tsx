import styled from 'styled-components';
import { S_NameTag } from '../UI/S_Tag';
import { Candidate, TeamList } from '../../pages/club/match/CreateMatch';
import { MemberUser } from '../../pages/club/setting/_totalMember';

interface AddCandidatePopUpProps {
  top: number;
  left: number;
  idx: number;
  totalMembers?: MemberUser[];
  candidates?: Candidate[];
  candidateList: string[];
  teamList: TeamList[];
  setTeamList: React.Dispatch<React.SetStateAction<TeamList[]>>;
  setCandidateList: React.Dispatch<React.SetStateAction<string[]>>;
  setIsOpenAddMember: React.Dispatch<React.SetStateAction<boolean>>;
}

const S_PopupContainer = styled.div<{ top?: number; left?: number }>`
  position: absolute;
  width: 300px;
  height: auto;
  border-radius: 5px;
  z-index: 9;
  background-color: var(--white);
  top: ${(props) => (props.top ? props.top + 20 : 0)}px;
  left: ${(props) => (props.left ? props.left - 300 : 0)}px;
  padding: 10px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
`;

function AddCandidatePopUp(props: AddCandidatePopUpProps) {
  return (
    <S_PopupContainer top={props.top} left={props.left}>
      {props.totalMembers &&
        props.totalMembers.map((member, idx) => {
          return (
            <S_NameTag
              key={idx}
              onClick={() => {
                //클릭한 멤버를 각 팀 명단리스트로 추가하는 기능
                const copiedTeamList = [...props.teamList];

                if (!copiedTeamList[props.idx].members.includes(member.nickName)) {
                  copiedTeamList[props.idx].members.push(member.nickName);
                  copiedTeamList[props.idx].membersIds.push(member.userId);
                }
                props.setTeamList([...copiedTeamList]);

                //클릭한 멤버를 후보 멤버리스트에서 빼주는 기능
                // const copiedCandidateList = [...props.candidateList];
                // copiedCandidateList.splice(idx, 1);
                // props.setCandidateList(copiedCandidateList);
              }}
            >
              {member.nickName}+
            </S_NameTag>
          );
        })}
    </S_PopupContainer>
  );
}

export default AddCandidatePopUp;
