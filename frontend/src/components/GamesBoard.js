import GameTile from './GameTile';
import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';

export default function GamesBoard({ games, selectGame }) {
    return (
        <section>
            <ButtonTab />
            <Wrapper>
                {games.map((game) => (
                    <li key={game.id}>
                        <GameTile game={game} selectGame={selectGame} />
                    </li>
                ))}
            </Wrapper>
        </section>
    );
}

const Wrapper = styled.ul`
    list-style: none;
    margin: 0;
    padding: 0;
    display: grid;
    justify-content: center;
    grid-template-columns: repeat(auto-fit, minmax(80px, 1fr));
    justify-items: stretch;
    grid-gap: 10px;
`;
