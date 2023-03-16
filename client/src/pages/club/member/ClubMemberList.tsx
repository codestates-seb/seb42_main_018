import styled from 'styled-components'
import { S_Label, S_Description } from '../../../components/UI/S_Text'
import { ClubMemberProps } from './ClubMember'

const S_Box = styled.div`
  // 전체 컨테이너 스타일
  display: flex;
  align-items: center;
  padding: 20px 10px;
  border-top: 1px solid var(--gray100);
  img {
    // 프로필 이미지 스타일
    height: 65px;
    border-radius: 10px;
  }
`
const S_Contents = styled.div`
  // 별명과 승률 스타일
  padding-left: 20px;
  padding-top: 5px;
`

function ClubMemberList({memberId, profileImage, name, winRate}: ClubMemberProps) {
  
  return (
    <S_Box key={memberId}>
      <img src={profileImage} alt="프로필이미지" />
      <S_Contents>
        <S_Label>{name}</S_Label>
        <S_Description>승률 {winRate}%</S_Description>
      </S_Contents>
    </S_Box>
  )
}

export default ClubMemberList;