import styled from 'styled-components/macro';

export default function ButtonBig() {
    return <Button> LET'S PLAY </Button>;
}

const Button = styled.button`
    border-radius: 20px;
    box-shadow: black 1px 2px;
    padding: 10px 50px;
    margin: 8px;
    background: #c8a1a2;
    color: white;
    border: none;
`;
