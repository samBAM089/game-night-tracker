import styled from 'styled-components/macro';

export default styled.section`
    background: none;
    border: 1px solid white;
    border-radius: 5px;
    padding: 5px 15px;
    margin: 0;
    display: grid;
    align-items: center;
    justify-items: center;
    grid-template-columns: 1fr 1fr 1fr;

    span {
        font-size: 1em;
        padding-bottom: 2px;
    }
`;
