import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import PlayerTile from './PlayerTile';

export default function PlayersBoard({ existingPlayers, selectPlayer }) {
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
                                <PlayerTile existingPlayer={existingPlayer} />
                            </li>
                        ))}
                </List>
            </Wrapper>
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
    padding: 0;
    display: grid;
    justify-content: center;
    grid-gap: 10px;
`;
