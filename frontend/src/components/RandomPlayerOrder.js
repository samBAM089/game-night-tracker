import styled from 'styled-components';
import ButtonBig from './ButtonBig';
import ButtonTab from './ButtonTab';
import { useState } from 'react';
import SessionPlayerTile from './SessionPlayerTile';
import { IconContext } from 'react-icons';
import { GiBeamsAura, GiPerspectiveDiceSixFacesFour } from 'react-icons/all';

export default function RandomPlayerOrder({ session, player }) {
    return (
        <Wrapper>
            <img src={session.imageUrl} alt="game cover" />
            <span>
                <IconContext.Provider
                    value={{
                        color: '#e2c617',
                        size: '1.2em',
                    }}
                >
                    <GiBeamsAura />
                </IconContext.Provider>{' '}
                FIRST PLAYER
            </span>
            <ul>
                {session.playerList.map((player) => (
                    <li key={player.name}>
                        <SessionPlayerTile player={player} />
                    </li>
                ))}
            </ul>
            <ButtonTab>
                <ButtonBig>CONTINUE</ButtonBig>
            </ButtonTab>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: 1fr auto 1fr 1fr;
    justify-items: center;

    img {
        margin: 20px 0;
        max-width: 50%;
        border-bottom-right-radius: 20px;
        box-shadow: 2px 2px lightslategray;
    }

    ul {
        list-style: none;
        padding: 0 20px;
        margin-top: 0;
    }

    li {
        margin-top: 10px;
    }
`;
