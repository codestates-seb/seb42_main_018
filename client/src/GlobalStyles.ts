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
    --blue300: #377CFB;

    --red100: #F04452;
    --green100: #04D182;
  }

  body {
    margin: 0 auto;
    width: 500px;
    background-color:var(--gray100);
  }

  /* html element 초기화 */
  button {
    border:none; 
    box-shadow:none; 
    cursor:pointer;
  }

  a {
    color: var(--black);
    text-decoration: none;
    outline: none
  }
  a:hover, a:active {
    color: initial;
    text-decoration: none;
  }

  p {
    color: #272E3A;
    font-size: 20px;
    line-height: 160%;
  }
`

export default GlobalStyles;