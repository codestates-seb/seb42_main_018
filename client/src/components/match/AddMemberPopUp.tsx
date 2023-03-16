import styled from 'styled-components';
import { S_Tag } from '../../components/UI/S_Tag';
import { TeamList } from '../../pages/club/match/CreateMatch';

interface AddMemberPopUpProps {
  top: number;
  idx: number;
  candidateList: string[];
  teamList: TeamList[];
  setTeamList: React.Dispatch<React.SetStateAction<TeamList[]>>;
  setCandidateList: React.Dispatch<React.SetStateAction<string[]>>;
  setIsOpenAddMember: React.Dispatch<React.SetStateAction<boolean>>;
}

const S_PopupContainer = styled.div<{ top?: number }>`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  position: absolute;
  width: 60%;
  height: auto;
  border-radius: 5px;
  z-index: 9;
  background-color: var(--white);
  top: ${(props) => props.top || 0}px;
  left: 35%;
  padding: 10px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
`;

function AddMemberPopUp(props: AddMemberPopUpProps) {
  const TEAM_ELEMENT_HEIGHT = 34;

  // const addTeamMember = (
  //   member: string,
  //   idx: number,
  //   teamList: TeamList[],
  //   setTeamList: React.Dispatch<React.SetStateAction<TeamList[]>>
  // ) => {
  //   const copied = [...teamList];
  //   if (!copied[idx].members.includes(member)) {
  //     copied[idx].members.push(member);
  //   }
  //   setTeamList([...copied]);
  // };

  return (
    <S_PopupContainer top={TEAM_ELEMENT_HEIGHT + props.top}>
      {props.candidateList &&
        props.candidateList.map((member, idx) => {
          return (
            <S_Tag
              key={idx}
              onClick={() => {
                
                // addTeamMember(member, idx, props.teamList, props.setTeamList);

                //클릭한 멤버를 각 팀 명단리스트로 추가하는 기능
                const copiedTeamList = [...props.teamList];
                if (!copiedTeamList[props.idx].members.includes(member)) {
                  copiedTeamList[props.idx].members.push(member);
                }
                props.setTeamList([...copiedTeamList]);

                //클릭한 멤버를 후보 멤버리스트에서 빼주는 기능
                const copiedCandidateList = [...props.candidateList]; 
                copiedCandidateList.splice(idx, 1);
                props.setCandidateList(copiedCandidateList);

                
              }}
            >
              {member}+
            </S_Tag>
          );
        })}
    </S_PopupContainer>
  );
}

export default AddMemberPopUp;
