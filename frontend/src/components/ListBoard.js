import styled from 'styled-components/macro';
import SessionTile from './SessionTile';

export default function ListBoard() {
    return (
        <Wrapper>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
            <li>
                <SessionTile />
            </li>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    height: 100%;
    background: none;
    overflow-y: scroll;
    list-style: none;
    display: grid;
    padding: 5px;
    margin: 10px 0;
`;
