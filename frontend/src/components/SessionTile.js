import styled from 'styled-components/macro';
import { AiFillStar, Gi3DStairs } from 'react-icons/all';

export default function SessionTile({ session }) {
    return (
        <Wrapper>
            <span>
                <img src={session.imageUrl} alt="game box cover" />
            </span>
            <div>
                <span>{session.startDateTimestamp.substring(0, 11)}</span>
                <br />
                <span>
                    <h2>{session.gameName}</h2>
                </span>
                <br />
                <span>
                    <AiFillStar /> Winner: {session.winnerPlayerId}
                </span>
            </div>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    background: none;
    border: 1px solid white;
    list-style: none;
    border-radius: 5px;
    padding: 20px;
    margin: 20px 10px 0 20px;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-content: space-between;
    grid-gap: 20px;
    font-size: 0.7em;

    div {
        grid-column: span 2;
    }

    img {
        border-bottom-right-radius: 20px;
        max-width: 80px;
        box-shadow: 2px 2px lightslategray;
    }

    h2 {
        padding: 0;
        margin: 0;
    }

    button {
        font-size: 0.8em;
    }
`;
