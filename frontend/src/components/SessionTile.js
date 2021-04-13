import styled from 'styled-components/macro';
import { GiJeweledChalice } from 'react-icons/all';
import { IconContext } from 'react-icons';

export default function SessionTile({ session }) {
    return (
        <Wrapper>
            <span>
                <img src={session.imageUrl} alt="game box cover" />
            </span>
            <div>
                <span>{session.startDateTimestamp.substring(0, 10)}</span>
                <br />
                <span className="name">{session.gameName}</span>
                <br />
                <br />
                <span>{session.duration} Mins</span>
                <br />
                <span>
                    Winner:{' '}
                    <IconContext.Provider
                        value={{
                            style: {
                                color: '#e2c617',
                                size: '1.5em',
                                verticalAlign: 'middle',
                            },
                        }}
                    >
                        <GiJeweledChalice />
                    </IconContext.Provider>{' '}
                    {session.winnerPlayerId}
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
        border-right: 1px solid rgba(103, 103, 184, 0.53);
        border-bottom: 1px solid rgba(103, 103, 184, 0.53);
    }

    .name {
        font-size: 1.5em;
        padding: 0;
        margin: 0;
    }

    button {
        font-size: 0.8em;
    }
`;
