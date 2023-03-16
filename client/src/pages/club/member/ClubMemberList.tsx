import React from 'react'
import styled from 'styled-components'
import { S_Label, S_Description } from '../../../components/UI/S_Text'

const S_Container = styled.div`
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

function ClubMemberList() {
  return (
    <S_Container>
      <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcU50X1UOeDaphmUyD6T8ROKs-HjeirpOoapiWbC9cLAqewFy1gthrgUTB9E7nKjRwOVk&usqp=CAU" alt="프로필이미지" />
      <S_Contents>
        <S_Label>벌꿀오소리</S_Label>
        <S_Description>승률 56.5%</S_Description>
      </S_Contents>
    </S_Container>
  )
}

export default ClubMemberList;