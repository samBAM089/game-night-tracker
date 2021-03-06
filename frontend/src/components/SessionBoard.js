import styled from 'styled-components/macro';
import SessionTile from './SessionTile';

export default function SessionBoard({ sessions }) {
    return (
        <Wrapper>
            {sessions.map((session) => (
                <li key={session.id}>
                    <SessionTile session={session} />
                </li>
            ))}
        </Wrapper>
    );
}

const Wrapper = styled.ul`
    display: grid;
    justify-content: space-around;
    background: none;
    overflow-y: scroll;
    list-style: none;
    padding: 5px;
    margin: 10px 0;
`;
