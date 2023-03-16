// 모든 버튼 컴포넌트

import styled from 'styled-components';

const StyledButton = styled.button<ButtonStyleType>`
  width: ${(props) => props.width || '100%'};
  background-color: ${(props) => props.backgroundColor || 'var(--blue300)'};
  color: ${(props) => props.color || 'var(--white)'};

  height: 50px;
  padding: 10px 12px;
  border-radius: 5px;
  font-size: 1rem;

  :hover {
    background-color: var(--blue200);
  }
`;

interface ButtonStyleType {
  width?: string;
  color?: string;
  backgroundColor?: string;
}

interface S_ButtonProps {
  children: string;
  addStyle?: ButtonStyleType;
  onClick?: () => void;
}

export function S_Button({ children, addStyle, onClick }: S_ButtonProps) {
  if (addStyle) {
    const { width, backgroundColor, color } = addStyle;
    return (
      <StyledButton width={width} backgroundColor={backgroundColor} color={color} onClick={onClick}>
        {children}
      </StyledButton>
    );
  }

  return <StyledButton>{children}</StyledButton>;
}

export const S_ButtonGray = styled(StyledButton)`
  // 가장 기본적인 회색 버튼입니다. 화면 가로너비 전체를 차지합니다.
  color: var(--gray600);
  background-color: var(--gray100);
  :hover {
    background-color: var(--gray200);
  }
`;

export const S_ButtonBlack = styled(StyledButton)`
  // 마이페이지의 로그아웃, 회원탈퇴에 사용한 버튼입니다.
  width: 140px;
  background-color: var(--gray500);
  :hover {
    background-color: var(--gray400);
  }
`;

export const S_SelectButton = styled.button`
  // 참석, 불참을 표시하기 전 기본 버튼입니다.
  width: 46px;
  height: 30px;
  color: var(--gray600);
  background-color: var(--white);
  border: 1px solid var(--gray200);
  border-radius: 5px;
`;

export const S_EditButton = styled.button`
  // 수정/편집/설정 등을 나타내는, 글자로만 이루어진 버튼입니다.
  padding: 7px;
  border-radius: 5px;
  text-decoration: underline;
  color: var(--gray300);
  background-color: var(--white);
  :hover {
    background-color: var(--gray100);
  }
`;

export const S_NegativeButton = styled(S_EditButton)`
  // 삭제/탈퇴/가입거부 등 부정을 나타내는, 글자로만 이루어진 버튼입니다.
  color: var(--red100);
`;
