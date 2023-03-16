import React from 'react'
import styled from "styled-components";

interface Title {
  name: string;
  width?: string;
  color?: string;
}

const titleContents: Title[] = [
  {name: '순위'},
  {name: '', width: '190px'},
  {name: '승률', width: '80px', color: 'var(--blue300)'},
  {name: '경기 수', width: '80px'},
  {name: '승'},
  {name: '무'},
  {name: '패'},
]

const S_Th = styled.th<{ width?:string, color?:string }>`
  padding: 8px 0px;
  width: ${({ width }) => (width || '50px')};
  color: ${({ color }) => (color)};
`

function MemberRecordTitle() {
  return (
    <thead>
      <tr>
        {titleContents.map((e)=>(
          <S_Th key={e.name} width={e.width} color={e.color}>{e.name}</S_Th>
        ))}
      </tr>
    </thead>
  )
}

export default MemberRecordTitle