import SessionEndPlayerTile from './SessionEndPlayerTile';
import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import ButtonBig from './ButtonBig';
import { useState, useEffect } from 'react';

export default function SessionEnd({ session, setFinalPlayerList, setWinner }) {
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

        const winner = playersWithScores.filter(
            (player) => player.score === playerScores[0]
        );

        const scoresAndWinner = {
            playerList: playersWithScores,
            winnerPlayerId: winner,
        };

        if (scoresAndWinner) {
            setFinalPlayerList(scoresAndWinner);
        } else {
            console.log('no playersWithScore');
        }
    };

    return (
        <Wrapper>
            <img src={session.imageUrl} alt="game cover" />
            <p>PLAY TIME: {session.duration} Minutes</p>
            <form onSubmit={onSubmitHandler}>
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
            </form>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: auto auto 1fr;
    justify-items: center;

    img {
        margin: 20px 0;
        max-width: 50%;
        border-bottom-right-radius: 20px;
        box-shadow: 2px 2px lightslategray;
    }

    p {
        margin: 0;
        padding: 0;
    }

    ul {
        list-style: none;
        margin: 0;
        padding: 20px;
    }
`;
