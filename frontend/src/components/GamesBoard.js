import GameTile from './GameTile';
import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import ButtonBig from './ButtonBig';
import { useState } from 'react';

export default function GamesBoard({ games, selectGame }) {
    const [gameToPlay, setGameToPlay] = useState();

    const onClickHandler = () => {
        if (gameToPlay) {
            selectGame(gameToPlay);
        }
    };

    return (
        <section>
            <Wrapper>
                {games.map((game) => (
                    <li key={game.id}>
                        <GameTile game={game} setGameToPlay={setGameToPlay} />
                    </li>
                ))}
            </Wrapper>
            <ButtonTab>
                <ButtonBig onClick={onClickHandler}>CONTINUE</ButtonBig>
            </ButtonTab>
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
