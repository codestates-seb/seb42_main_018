import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyles = createGlobalStyle` 
  ${reset} 

  /* COLOR PALETTE */
  :root {
    --white: #FFF;
    --black: #000;

    --gray100: #F1F3F6;
    --gray200: #CACFD9;
    --gray300: #A1A9B4;
    --gray400: #788290;
    --gray500: #3D4755;
    --gray600: #272E3A;

    --blue100: #E3F0FF;
    --blue200: #90C2FF;
    --blue300: #377CFB;

    --red100: #F04452;
    --green100: #04D182;

    --kakao-main-theme: #FEE500;
    --kakao-hover-theme: #FADA0A;

    --naver-main-theme: #19CE60;
    --naver-hover-theme: #1BC15C;
  }

  body {
    /* 중앙정렬 및 배경 전체 색 */
    margin: 0 auto;
    max-width: 500px;
    box-shadow: 0px 0px 30px -10px var(--gray200);
    background-color: var(--gray100);
    /* 서체 시스템 통일 */
    color: var(--gray600);
    line-height: 1.5em;
  }

  button {
    border:none; 
    box-shadow:none;
    font-weight: 500;
    cursor:pointer;
  }

  a {
    color: var(--gray600);
    text-decoration: none;
    outline: none
  }
  a:hover, a:active {
    color: initial;
    text-decoration: none;
  }

  input[type="number"] {
    width: 40px;
  }
  input[type="date"],[type="time"] {
  }
`;

export default GlobalStyles;
