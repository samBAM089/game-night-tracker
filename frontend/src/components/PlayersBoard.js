import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import PlayerTile from './PlayerTile';
import ButtonBig from './ButtonBig';
import { useState } from 'react';
import getRandomColorCode from '../services/getRandomColorCode';

export default function PlayersBoard({
    existingPlayers,
    setExistingPlayers,
    selectPlayer,
}) {
    const [players, setPlayers] = useState([]);
    const [playerToAdd, setPlayerToAdd] = useState('');
    const [error, setError] = useState(false);
    const [noPlayersSelected, setNoPlayersSelected] = useState(false);

    const hasPlayerToAdd = playerToAdd.length > 0;

    const handleChange = (event) => {
        setError(false);
        setPlayerToAdd(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (!hasPlayerToAdd) {
            return;
        }
        const playerToAddToExistingPlayers = {
            name: playerToAdd,
            color: getRandomColorCode(),
        };
        if (existingPlayers.length > 0) {
            if (
                !existingPlayers.some(
                    (existingPlayer) => existingPlayer.name === playerToAdd
                )
            ) {
                setExistingPlayers([
                    ...existingPlayers,
                    playerToAddToExistingPlayers,
                ]);
                setPlayerToAdd('');
            } else {
                setError(true);
            }
        }
    };

    const onChange = (selectedPlayer, event) => {
        setNoPlayersSelected(false);
        if (event.target.checked) {
            if (
                !players.includes(
                    (player) => player.name === selectedPlayer.name
                )
            ) {
                setPlayers([...players, selectedPlayer]);
            }
        } else {
            setPlayers(
                players.filter((player) => player.name !== selectedPlayer.name)
            );
        }
    };

    const handleClick = (event) => {
        if (players.length > 0) {
            selectPlayer(players);
        } else {
            setNoPlayersSelected(true);
        }
    };

    return (
        <>
            <Wrapper>
                <Form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        value={playerToAdd}
                        placeholder="NEW PLAYER"
                        onChange={(event) => handleChange(event)}
                    />
                    <ButtonBig type="submit" disabled={!hasPlayerToAdd}>
                        +
                    </ButtonBig>
                </Form>
                {error && <p>Player already exists!</p>}
                {noPlayersSelected && <p>No players selected!</p>}
                <List>
                    {existingPlayers &&
                        existingPlayers.map((existingPlayer) => (
                            <li key={existingPlayer.name}>
                                <PlayerTile
                                    existingPlayer={existingPlayer}
                                    onChange={onChange}
                                />
                            </li>
                        ))}
                </List>
            </Wrapper>
            <ButtonTab>
                <ButtonBig onClick={() => handleClick()}>CONTINUE</ButtonBig>
            </ButtonTab>
        </>
    );
}

const Wrapper = styled.section`
    background: var(--primary);
    display: grid;
    grid-template-rows: auto auto auto;
    overflow: hidden;

    p {
        padding: 0;
        margin: 0;
        text-align: center;
    }
`;

const Form = styled.form`
    max-width: 100%;

    padding: 0 10px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    justify-content: center;
    align-items: center;

    input {
        padding: 10px 5px;
        margin: 15px;
        border-radius: 3px;
        text-align: center;
        border: none;
        outline: none;
    }
`;

const List = styled.ul`
    list-style: none;
    margin: 0;
    padding: 20px 0;
    display: grid;
    justify-content: center;
    grid-gap: 10px;
    overflow-y: scroll;
`;
