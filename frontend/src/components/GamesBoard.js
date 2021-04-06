import GameTile from './GameTile';
import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import ButtonBig from './ButtonBig';
import { useState } from 'react';

export default function GamesBoard({ games, selectGame }) {
    const [gameToPlay, setGameToPlay] = useState();
    const [noGameSelected, setNoGameSelected] = useState(false);

    const onClickHandler = () => {
        if (gameToPlay) {
            selectGame(gameToPlay);
        } else {
            setNoGameSelected(true);
        }
    };

    return (
        <Wrapper>
            {noGameSelected && <span>NO GAME SELECTED!</span>}
            <List>
                {games.map((game) => (
                    <li key={game.id}>
                        <GameTile
                            game={game}
                            setGameToPlay={setGameToPlay}
                            setNoGameSelected={setNoGameSelected}
                        />
                    </li>
                ))}
            </List>

            <ButtonTab>
                <ButtonBig onClick={onClickHandler}>CONTINUE</ButtonBig>
            </ButtonTab>
        </Wrapper>
    );
}

const List = styled.ul`
    width: 100%;
    list-style: none;
    margin: 0;
    padding: 20px 5px;
    display: grid;
    justify-content: center;
    grid-template-columns: repeat(auto-fit, minmax(80px, 1fr));
    justify-items: stretch;
    grid-gap: 10px;
    overflow: scroll;
`;

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: 1fr auto;

    span {
        text-align: center;
        margin-top: 10px;
    }
`;
