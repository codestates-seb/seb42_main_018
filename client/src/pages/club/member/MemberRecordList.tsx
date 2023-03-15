import React from 'react'
import styled from "styled-components";

interface Contents {
  name: string;
  width?: string;
  color?: string;
}

const mainContents: Contents[] = [
  {name: '1'},
  {name: '별명 임시'},
  {name: '58%', color: 'var(--blue300)'},
  {name: '12'},
  {name: '10'},
  {name: '2'},
  {name: '0'},
]

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
    <tr>
      {mainContents.map((e)=>(
        <S_Td key={e.name} width={e.width} color={e.color}>{e.name}</S_Td>
      ))}
    </tr>
  )
}

export default MemberRecordList