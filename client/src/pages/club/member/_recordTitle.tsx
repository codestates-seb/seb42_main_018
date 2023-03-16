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

const S_ListBox = styled.div`
  display: flex;
  text-align: center;
  `

const S_ListItem = styled.div<{ width?:string, color?:string }>`
  padding: 5px 0px;
  min-width: ${({ width }) => (width || '50px')};
  color: ${({ color }) => (color)};
  border-bottom: 1px solid var(--gray100);
`

function MemberRecordTitle() {
  return (
    <S_ListBox>
      {titleContents.map((e)=>(
        <S_ListItem 
          key={e.name} 
          width={e.width} 
          color={e.color}>
            {e.name}
          </S_ListItem>
      ))}
    </S_ListBox>
  )
}

export default MemberRecordTitle