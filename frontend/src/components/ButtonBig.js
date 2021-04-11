import styled from 'styled-components/macro';

export default styled.button`
    border-radius: 20px;
    box-shadow: black 1px 2px;
    padding: 10px 50px;
    margin: 15px;
    background: #c8a1a2;
    color: white;
    border: none;
    outline: none;

    &:disabled {
        background: none;
        border: 1px solid white;
    }

    /*&:enabled {
    background: #c8a1a2;
    color: white;
    box-shadow: black 1px 2px;
    border: none;*/
`;
