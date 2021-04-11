import styled from 'styled-components/macro';
import { Gi3DMeeple } from 'react-icons/all';
import { IconContext } from 'react-icons';

export default function SessionPlayerTile({ player }) {
    return (
        <Wrapper>
            <IconContext.Provider
                value={{ color: player.color, size: '1.5em' }}
            >
                <Gi3DMeeple />
            </IconContext.Provider>
            <span>{player.name}</span>
        </Wrapper>
    );
}

const Wrapper = styled.section`
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
        padding-bottom: 5px;

        /* overflow: hidden;
     text-overflow: ellipsis;
     white-space: nowrap;*/
    }
`;
