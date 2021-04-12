import SessionEndPlayerTile from './SessionEndPlayerTile';
import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import ButtonBig from './ButtonBig';
import { useState, useEffect } from 'react';

export default function SessionEnd({ session, setFinalPlayerList }) {
    const [playersWithScores, setPlayersWithScores] = useState([]);

    useEffect(() => {
        setPlayersWithScores(session.playerList);
    }, []);

    const onSubmitHandler = (event) => {
        event.preventDefault();

        const playerScores = playersWithScores.map((player) => player.score);

        playerScores.sort(function (a, b) {
            return b - a;
        });

        const winnerWithScore = playersWithScores.filter(
            (player) => player.score === playerScores[0]
        );
        const winner = winnerWithScore[0].name;
        const scoresAndWinner = {
            playerList: playersWithScores,
            winnerPlayerId: winner,
        };

        if (scoresAndWinner) {
            setFinalPlayerList(scoresAndWinner);
        }
    };

    return (
        <>
            <Wrapper>
                <img src={session.imageUrl} alt="game cover" />
                <p>PLAY TIME: {session.duration} Minutes</p>
                <Form onSubmit={onSubmitHandler}>
                    <ul>
                        {session.playerList.map((player) => (
                            <li key={player.name}>
                                <SessionEndPlayerTile
                                    player={player}
                                    playersWithScores={playersWithScores}
                                    setPlayersWithScores={setPlayersWithScores}
                                />
                            </li>
                        ))}
                    </ul>
                    <ButtonTab>
                        <ButtonBig type="submit">SAVE</ButtonBig>
                    </ButtonTab>
                </Form>
            </Wrapper>
        </>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: auto auto 1fr;
    justify-items: center;

    height: 100%;
    background: var(--primary);
    padding: 0 16px;
    overflow-y: hidden;

    img {
        margin: 20px 0;
        max-width: 50%;
        border-bottom-right-radius: 20px;
        border-right: 2px solid rgba(103, 103, 184, 0.53);
        border-bottom: 2px solid rgba(103, 103, 184, 0.53);
    }

    p {
        margin: 5px;
        padding-bottom: 5px;
    }

    form {
        overflow-y: scroll;
    }

    ul {
        list-style: none;
        margin: 0;
        padding: 5px 20px;
    }
`;

const Form = styled.form`
    display: grid;
    grid-template-rows: 1fr auto;
`;
