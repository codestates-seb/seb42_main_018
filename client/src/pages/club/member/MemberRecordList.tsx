import styled from "styled-components";

const S_ListBox = styled.div`
  display: flex;
  border-bottom: 1px solid var(--gray200);
`

const S_Td = styled.td<{ width?:string, color?:string }>`
  padding: 20px 0px;
  text-align: center;
  
  border-left: 1px solid var(--gray600);
  border-top: 1px solid var(--gray100);
  width: ${({ width }) => (width || '50px')};
  color: ${({ color }) => (color)};
`

function MemberRecordList() {
  return (
    <S_ListBox>

    </S_ListBox>
  )
}

export default MemberRecordList