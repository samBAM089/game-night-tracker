import styled from 'styled-components/macro';

export default function GameTile({ game, selectGame }) {
    const onClickhandler = (event) => {
        selectGame(game);
    };

    return (
        <Wrapper>
            <button onClick={onClickhandler}>
                <img src={game.thumbnailUrl} alt="game box cover" />
                <span>SELECT</span>
            </button>
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
