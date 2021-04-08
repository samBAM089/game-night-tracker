import { IconContext } from 'react-icons';
import { Gi3DMeeple } from 'react-icons/all';
import styled from 'styled-components/macro';

export default function SessionEndPlayerTile({
    player,
    playersWithScores,
    setPlayersWithScores,
}) {
    const onChangeHandler = (player, event) => {
        console.log(event.target.value);

        const playerWithScore = {
            name: player.name,
            color: player.color,
            score: event.target.value,
        };

        if (
            !playersWithScores.some(
                (player) => player.name === playerWithScore.name
            )
        ) {
            setPlayersWithScores([...playersWithScores, playerWithScore]);
        } else {
            setPlayersWithScores(
                playersWithScores.map((player) =>
                    player.name === playerWithScore.name
                        ? playerWithScore
                        : player
                )
            );
        }
    };

    return (
        <Wrapper>
            <IconContext.Provider
                value={{ color: player.color, size: '1.5em' }}
            >
                <Gi3DMeeple />
            </IconContext.Provider>
            <span>{player.name}</span>
            <input
                type="text"
                placeholder="SCORE"
                onChange={(event) => onChangeHandler(player, event)}
            />
        </Wrapper>
    );
}

const Wrapper = styled.section`
    background: none;
    border-bottom: 1px solid darkgrey;
    padding: 10px 15px;
    margin: 5px;
    display: grid;
    align-items: center;
    justify-items: start;
    grid-template-columns: auto 1fr 1fr;
    grid-gap: 10px;

    span {
        padding: 0 0 0 15px;
    }

    input {
        margin-left: 20px;
        padding: 3px;
        width: 80%;
        font-size: 0.6em;
    }
`;
