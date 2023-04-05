import { useState } from 'react';
import {
  Record,
  S_ButtonBox,
  S_ConfirmModalContainer,
  TeamList
} from '../../pages/club/match/CreateMatch';
import { S_Button, S_EditButton, S_NegativeButton } from '../UI/S_Button';
import { ModalBackdrop } from '../UI/S_Modal';
import { S_NameTag } from '../UI/S_Tag';
import { S_Description, S_Label, S_Text } from '../UI/S_Text';

interface TeamCardProps {
  teamList: TeamList[];
  team: TeamList;
  idx: number;
  deleteNameTagFromTeam: (idx: number, memberIdx: number) => void;
  openMemberListPopup: (idx: number, e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
  deleteTeam: (idx: number) => void;
  records: Record[];
  setRecords: React.Dispatch<React.SetStateAction<Record[]>>;
}

function TeamCard(props: TeamCardProps) {
  const [isOpenDeleteTeam, setIsOpenDeleteTeam] = useState(false);
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
            if (props?.records.length !== 0) {
              setIsOpenDeleteTeam(true);
            } else {
              props.deleteTeam(props.idx);
            }
          }}
        >
          삭제
        </S_NegativeButton>
      </div>
      {isOpenDeleteTeam && (
        <ModalBackdrop>
          <S_ConfirmModalContainer>
            <S_Description color='var(--black)'>현재 팀 구성의 전적이 존재합니다.</S_Description>
            <S_Description color='var(--black)'>
              기존 팀이 삭제될 경우 전적이 초기화됩니다.
            </S_Description>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  props.deleteTeam(props.idx);
                  props.setRecords([]);
                  setIsOpenDeleteTeam(false);
                }}
              >
                확인
              </S_Button>
              <S_Button
                addStyle={{
                  width: '48%',
                  backgroundColor: 'var(--gray100)',
                  color: 'var(--gray400)',
                  hoverBgColor: 'var(--gray200)'
                }}
                onClick={() => {
                  setIsOpenDeleteTeam(false);
                }}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_ConfirmModalContainer>
        </ModalBackdrop>
      )}
    </div>
  );
}
export default TeamCard;
