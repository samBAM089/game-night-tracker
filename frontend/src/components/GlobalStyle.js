import { createGlobalStyle } from 'styled-components';

export default createGlobalStyle`
  :root {
    --primary: darkslateblue;
  }

  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    color: aliceblue;
    font-family: sans-serif;
  }
`;
