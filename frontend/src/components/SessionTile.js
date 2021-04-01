import styled from 'styled-components/macro';

export default function SessionTile({ session }) {
    return (
        <Wrapper>
            <span>
                <img src={session.thumbnail} alt="" />
            </span>
            <div>
                <span>{session.startDate}</span>
                <br />
                <span>
                    <h2>{session.gameName}</h2>
                </span>
                <br />

                <span>Winner: {session.winner}</span>
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
`;