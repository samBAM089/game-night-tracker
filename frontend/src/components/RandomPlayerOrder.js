import styled from 'styled-components';
import ButtonBig from './ButtonBig';
import ButtonTab from './ButtonTab';
import { useState } from 'react';
import SessionPlayerTile from './SessionPlayerTile';

export default function RandomPlayerOrder({
    session,
    randomizePlayer,
    startTimer,
}) {
    const [firstPlayer, setFirstPlayer] = useState(false);

    const randomizePlayerOrder = () => {
        const playerListToRandomize = [...session.playerList];
        const randomizedPlayers = [];
        while (playerListToRandomize.length > 0) {
            const randomIndex = Math.floor(
                Math.random() * playerListToRandomize.length
            );
            randomizedPlayers.push(playerListToRandomize[randomIndex]);
            playerListToRandomize.splice(randomIndex, 1);
        }
        randomizePlayer(randomizedPlayers);
    };

    const randomizeInterval = () => {
        const intervalId = setInterval(randomizePlayerOrder, 100);
        setTimeout(() => clearInterval(intervalId), 3000);
        setFirstPlayer(true);
    };

    const onClickHandler = () => {
        startTimer();
    };

    return (
        <>
            <Wrapper>
                <img src={session.imageUrl} alt="game cover" />
                <ul>
                    {session.playerList.map((player) => (
                        <li key={player.name}>
                            <SessionPlayerTile
                                player={player}
                                firstPlayer={firstPlayer}
                            />
                        </li>
                    ))}
                </ul>
                <ButtonBig className="randomizer" onClick={randomizeInterval}>
                    RANDOMIZE
                </ButtonBig>
            </Wrapper>
            <ButtonTab>
                <ButtonBig onClick={onClickHandler}>CONTINUE</ButtonBig>
            </ButtonTab>
        </>
    );
}

const Wrapper = styled.section`
    background: var(--primary);
    display: grid;
    grid-template-rows: auto 1fr auto;
    justify-items: center;
    overflow-y: hidden;

    img {
        margin: 20px 0;
        max-width: 50%;
        border-bottom-right-radius: 20px;
        border-right: 2px solid rgba(103, 103, 184, 0.53);
        border-bottom: 2px solid rgba(103, 103, 184, 0.53);
    }

    span {
        padding-top: 10px;
    }

    ul {
        list-style: none;
        padding: 0 20px;
        margin-top: 0;
        overflow-y: scroll;
    }

    li {
        margin-top: 10px;
    }

    .randomizer {
        padding: 10px 45px;
    }
`;
