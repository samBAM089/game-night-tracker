import styled from 'styled-components';
import ButtonBig from './ButtonBig';
import ButtonTab from './ButtonTab';
import { useState } from 'react';
import SessionPlayerTile from './SessionPlayerTile';
import { IconContext } from 'react-icons';
import { GiBeamsAura, GiPerspectiveDiceSixFacesFour } from 'react-icons/all';

export default function RandomPlayerOrder({
    session,
    player,
    randomizePlayer,
}) {
    const randomizePlayerOrder = () => {
        let playerListToRandomize = [...session.playerList];
        let randomizedPlayers = [];
        while (playerListToRandomize.length > 0) {
            let randomIndex = Math.floor(
                Math.random() * playerListToRandomize.length
            );
            randomizedPlayers.push(playerListToRandomize[randomIndex]);
            playerListToRandomize.splice(randomIndex, 1);
        }
        randomizePlayer(randomizedPlayers);
    };

    const randomizeInterval = () => {
        const intervalId = setInterval(randomizePlayerOrder, 300);
        setTimeout(() => clearInterval(intervalId), 3000);
    };

    return (
        <Wrapper>
            <img src={session.imageUrl} alt="game cover" />
            <Inner>
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
                <button onClick={randomizeInterval}>RANDOMIZE</button>
            </Inner>
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
    grid-template-rows: auto auto 1fr 1fr;
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

const Inner = styled.div`
    display: grid;
    grid-template-columns: 1fr auto;
    grid-gap: 10px;
    padding-bottom: 10px;

    button {
        font-size: 0.6em;
        padding: 0 10px;
        margin: 0;
        border-radius: 20px;
        border: none;
        box-shadow: black 1px 2px;
    }
`;
