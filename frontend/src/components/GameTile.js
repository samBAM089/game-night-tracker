import styled from 'styled-components/macro';

export default function GameTile({ game, setGameToPlay }) {
    return (
        <Wrapper>
            <input type="radio" onChange={(e) => setGameToPlay(game)} />
            <img src={game.thumbnailUrl} alt="game box cover" />
            <span>{game.name}</span>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: auto auto;

    img {
        width: 100%;
        border-top-right-radius: 20px;
    }

    button {
        border: 1px solid darkgrey;
        border-top-right-radius: 23px;
        background: none;
        color: darkgrey;
        padding: 3px;
    }
`;
