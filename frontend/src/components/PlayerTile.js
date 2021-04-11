import styled from 'styled-components/macro';
import { Gi3DMeeple } from 'react-icons/all';
import { IconContext } from 'react-icons';

export default function PlayerTile({ existingPlayer, onChange }) {
    return (
        <Wrapper>
            <IconContext.Provider
                value={{ color: existingPlayer.color, size: '1.5em' }}
            >
                <Gi3DMeeple />
            </IconContext.Provider>
            <span>{existingPlayer.name}</span>
            <input
                type="checkbox"
                onChange={(event) => onChange(existingPlayer, event)}
            />
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
        text-align: left;
    }

    input {
        margin-left: 20px;
        padding: 0;
    }
`;
