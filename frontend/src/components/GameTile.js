import styled from 'styled-components/macro';

export default function GameTile({ game, setGameToPlay, setNoGameSelected }) {
    const handleChange = (game) => {
        setNoGameSelected(false);
        setGameToPlay(game);
    };

    return (
        <Wrapper>
            <label>
                <input
                    type="radio"
                    name="game"
                    onChange={(e) => handleChange(game)}
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
        border-right: 1px solid rgba(103, 103, 184, 0.53);
        border-bottom: 1px solid rgba(103, 103, 184, 0.53);
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
