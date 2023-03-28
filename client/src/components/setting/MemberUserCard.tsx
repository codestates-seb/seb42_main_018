import styled from 'styled-components';
import { S_Button, S_SelectButton } from '../UI/S_Button';
import { S_Description, S_Label, S_SmallDescription, S_Text } from '../UI/S_Text';
import dummy from '../../assets/default_profile.svg';
import { MemberUser } from '../../pages/club/setting/_totalMember';
import { S_Select } from '../UI/S_Select';
import getGlobalState from '../../util/authorization/getGlobalState';
import { useNavigate, useParams } from 'react-router-dom';
import { patchFetch } from '../../util/api';
import { useState } from 'react';
import { ModalBackdrop } from '../UI/S_Modal';
import { S_ButtonBox, S_ConfirmModalContainer } from '../../pages/club/match/CreateMatch';

const S_SettingCardContainer = styled.div`
  display: flex;
`;
const S_ImageWrapper = styled.div`
  margin: 10px;
`;
const S_ContentWrapper = styled.div`
  padding-top: 10px;
  width: 50%;
  height: 60px;
`;
const S_ConfigWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  width: 50%;
  margin: 5px 0;
`;

const S_ButtonWrapper = styled.div`
  display: flex;
`;

interface MemberUserCardProps {
  member: MemberUser;
}

function MemberUserCard(props: MemberUserCardProps) {
  const { userInfo, tokens } = getGlobalState();
  const { id } = useParams();
  const navigate = useNavigate();

  const [isOpenConfirm, setIsOpenConfirm] = useState(false);
  const [isOpenDelegate, setIsOpenDelegate] = useState(false);

  const banishMember = () => {
    patchFetch(
      `${process.env.REACT_APP_URL}/clubs/${id}/memberStatus/${props.member.userId}`,
      {
        clubMemberStatus: 'MEMBER BLACKED'
      },
      tokens
    );
  };

  const delegateLeader = () => {
    patchFetch(
      `${process.env.REACT_APP_URL}/clubs/${id}/delegate?leaderId=${userInfo.userId}&leaderChangeClubRole=MEMBER&memberId=${props.member.userId}&memberChangeClubRole=LEADER`,
      {},
      tokens
    );
  };

  const onChangeSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    patchFetch(
      `${process.env.REACT_APP_URL}/clubs/${id}/clubRole/${props.member.userId}`,
      {
        clubRole: e.target.value === '일반' ? 'MEMBER' : 'MANAGER'
      },
      tokens
    );
  };
  return (
    <>
      <S_SettingCardContainer>
        <S_ImageWrapper>
          <img alt='profile' src={dummy} />
        </S_ImageWrapper>
        <S_ContentWrapper>
          <S_Text color='var(--black)' style={{ fontWeight: 'bold' }}>
            {props.member.nickName}
          </S_Text>
        </S_ContentWrapper>
        <S_ConfigWrapper>
          <S_Select
            style={{ margin: '0', width: '100px', padding: '0 10px' }}
            defaultValue={
              props.member.clubRole === 'LEADER'
                ? '소모임장'
                : props.member.clubRole === 'MANAGER'
                ? '매니저'
                : '일반'
            }
            disabled={props.member.clubRole === 'LEADER'}
            onChange={(e) => {
              onChangeSelect(e);
            }}
          >
            <option>일반</option>
            <option>매니저</option>
            <option disabled>소모임장</option>
          </S_Select>
          {props.member.clubRole !== 'LEADER' ? (
            <S_ButtonWrapper>
              <S_SmallDescription
                onClick={(e) => {
                  e.stopPropagation();
                  setIsOpenDelegate(true);
                }}
                // color='var(--blue300)'
                style={{ textAlign: 'right', marginRight: '10px', textDecoration: 'underline' }}
              >
                소모임장 위임
              </S_SmallDescription>
              <S_SmallDescription
                onClick={(e) => {
                  e.stopPropagation();
                  setIsOpenConfirm(true);
                }}
                color='var(--red100)'
                style={{ textAlign: 'right', marginRight: '10px' }}
              >
                추방
              </S_SmallDescription>
            </S_ButtonWrapper>
          ) : null}
        </S_ConfigWrapper>
      </S_SettingCardContainer>
      <hr style={{ margin: '5px 0', border: '1px solid var(--gray200)' }} />
      {isOpenConfirm && (
        <ModalBackdrop>
          <S_ConfirmModalContainer style={{ height: 'auto' }}>
            <div style={{ width: '90%' }}>
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <S_Label style={{ textAlign: 'left' }}>{props.member.nickName}</S_Label>
                <S_Description style={{ textAlign: 'right' }}>&nbsp;님을</S_Description>
              </div>
              <S_SmallDescription style={{ textAlign: 'left' }}>
                [회원등급: {props.member.clubRole}]
              </S_SmallDescription>
              <S_Description style={{ textAlign: 'right' }}>
                소모임에서 추방하시겠습니까?
              </S_Description>
            </div>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  banishMember();
                  setIsOpenConfirm(false);
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
                  setIsOpenConfirm(false);
                }}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_ConfirmModalContainer>
        </ModalBackdrop>
      )}
      {isOpenDelegate && (
        <ModalBackdrop>
          <S_ConfirmModalContainer style={{ height: 'auto' }}>
            <div style={{ width: '90%' }}>
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <S_Label style={{ textAlign: 'left', display: 'inline' }}>
                  {props.member.nickName}
                </S_Label>
                <S_Description>&nbsp;님을</S_Description>
              </div>
              <S_SmallDescription style={{ textAlign: 'left' }}>
                [회원등급: {props.member.clubRole}]
              </S_SmallDescription>
              <S_Description style={{ textAlign: 'right' }}>
                소모임장으로 위임하시겠습니까?
              </S_Description>
              <S_Description color='var(--red100)'>
                주의: 소모임 메인화면으로 이동합니다.
              </S_Description>
            </div>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  delegateLeader();
                  setIsOpenDelegate(false);
                  navigate(`/club/${id}`);
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
                  setIsOpenDelegate(false);
                }}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_ConfirmModalContainer>
        </ModalBackdrop>
      )}
    </>
  );
}

export default MemberUserCard;
