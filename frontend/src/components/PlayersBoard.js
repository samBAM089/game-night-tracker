import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import PlayerTile from './PlayerTile';
import ButtonBig from './ButtonBig';
import { useState } from 'react';

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

    const getRandomColorCode = () => {
        let makeColorCode = '0123456789ABCDEF';
        let code = '#';
        for (let count = 0; count < 6; count++) {
            code = code + makeColorCode[Math.floor(Math.random() * 16)];
        }
        return code;
    };

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
                !existingPlayers.find(
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
    console.log(players);

    const handleClick = (event) => {
        if (players.length > 0) {
            selectPlayer(players);
        } else {
            setNoPlayersSelected(true);
        }
    };

    return (
        <section>
            <Wrapper>
                <Form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        value={playerToAdd}
                        placeholder="NEW PLAYER"
                        onChange={(event) => handleChange(event)}
                    />
                    <button type="submit" disabled={!hasPlayerToAdd}>
                        +
                    </button>
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
        </section>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: auto 1fr;

    p {
        padding: 0;
        margin: 0;
        text-align: center;
    }
`;

const Form = styled.form`
    max-width: 100%;
    margin: 15px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 5px;
    justify-content: center;
    align-items: center;

    input {
        padding: 3px;
        border-radius: 10px;
        text-align: center;
        border: none;
        outline: none;
    }

    button {
        margin: 8px;
        padding: 2px;
        border-radius: 20px;
        background: none;
        color: white;
        border: 1px solid darkgrey;

        &:enabled {
            background: #c8a1a2;
            color: white;
            box-shadow: black 1px 2px;
            border: none;
        }
    }
`;

const List = styled.ul`
    list-style: none;
    margin: 0;
    padding: 20px 0;
    display: grid;
    justify-content: center;
    grid-gap: 10px;
`;
