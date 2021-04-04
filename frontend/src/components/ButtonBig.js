import styled from 'styled-components/macro';

export default styled.button`
    border-radius: 20px;
    box-shadow: black 1px 2px;
    padding: 10px 50px;
    margin: 8px;
    background: #c8a1a2;
    color: white;
    border: none;

    &:active {
        background: none;
        box-shadow: darkgrey 2px 2px;
    }
`;
