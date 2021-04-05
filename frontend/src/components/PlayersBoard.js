import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import PlayerTile from './PlayerTile';
import ButtonBig from './ButtonBig';
import { useState } from 'react';

export default function PlayersBoard({ existingPlayers, selectPlayer }) {
    const [players, setPlayers] = useState([]);

    const onChange = (selectedPlayer, event) => {
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

    return (
        <section>
            <Wrapper>
                <ButtonTab>
                    <input type="text" placeholder="NEW PLAYER" />
                    <button>+</button>
                </ButtonTab>
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
                <ButtonBig onClick={() => selectPlayer(players)}>
                    CONTINUE
                </ButtonBig>
            </ButtonTab>
        </section>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: auto 1fr;
`;

const List = styled.ul`
    list-style: none;
    margin: 0;
    padding: 20px 0;
    display: grid;
    justify-content: center;
    grid-gap: 10px;
`;
