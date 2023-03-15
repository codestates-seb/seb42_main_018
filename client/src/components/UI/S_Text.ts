// 모든 텍스트 포맷이 모여 있는 컴포넌트입니다.
import styled from "styled-components";

// 글자 스타일에는 색상값만 내려줄 수 있습니다. 
// ex) <S_Title color="var(--blue300)">제목입력</S_Title>

export const S_Title = styled.h1<{color?: string}>`
  // 가장 큰 제목입니다. 
  // 페이지 가장 상단의 페이지 설명(신규 소모임 만들기 등)이나
  // 소모임 소개에 소모임 이름 등에 사용했습니다.
  font-size: 1.8rem;
  line-height: 2.3rem;
  font-weight: 800;
  margin-bottom: 5px;
  color: ${(props) => props.color || 'var(--gray600)'};
`

export const S_Label = styled.div<{color?: string}>`
  // 두번째로 큰 글씨입니다. 입력창 위에 라벨이나
  // 메인-홈의 소모임 목록의 제목, 경기 일정의 경기 장소 등에 사용했습니다. 
  font-size: 1.3rem;
  line-height: 1.8rem;
  font-weight: 800;
  margin-bottom: 5px;
  color: ${(props) => props.color || 'var(--gray600)'};
`

export const S_Text = styled.p<{color?: string}>`
  // 글로벌 스타일에 적용된 기본 글씨를 따라가는 p태그입니다.
  margin-bottom: 5px;
`

export const S_Description = styled.div<{color?: string}>`
  // 라벨과 입력창 사이 보조적으로 들어가는 설명글입니다.
  // 또한 소모임 소개 개별 페이지에 제목 밑 카테고리/지역/인원을 설명합니다.
  // 기본 색상은 회색입니다.
  font-size: 0.9rem;
  line-height: 1.3rem;
  margin-bottom: 5px;
  color: ${(props) => props.color || 'var(--gray300)'};
`

export const S_SmallDescription = styled.div<{color?: string}>`
  // 가장 작은 설명글입니다.
  // 메인-홈의 소모임 목록의 제목 밑 카테고리/지역/인원을 설명합니다.
  // 기본 색상은 회색입니다.
  font-size: 0.75rem;
  line-height: 1.1rem;
  font-weight: 600;
  margin-bottom: 5px;
  color: ${(props) => props.color || 'var(--gray300)'};
`