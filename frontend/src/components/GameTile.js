import styled from 'styled-components/macro';

export default function GameTile({ game, setGameToPlay }) {
    return (
        <Wrapper>
            <label>
                <input
                    type="radio"
                    name="game"
                    onChange={(e) => setGameToPlay(game)}
                />
                <img src={game.thumbnailUrl} alt={game.name} />
            </label>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    img {
        width: 100%;
        border-bottom-right-radius: 20px;
        box-shadow: 2px 2px lightslategray;
        clip: rect(80px 80px);
    }

    [type='radio'] {
        position: absolute;
        opacity: 0;
        width: 0;
        height: 0;
    }

    [type='radio'] + img {
        cursor: pointer;
    }

    [type='radio']:checked + img {
        box-shadow: 2px 2px 1px 1px #e51a23;
    }
`;
