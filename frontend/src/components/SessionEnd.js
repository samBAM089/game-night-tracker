import SessionEndPlayerTile from './SessionEndPlayerTile';
import styled from 'styled-components/macro';
import ButtonTab from './ButtonTab';
import ButtonBig from './ButtonBig';

export default function SessionEnd({ session }) {
    return (
        <Wrapper>
            <img src={session.imageUrl} alt="game cover" />
            <p>PLAY TIME: {session.duration} Minutes</p>

            <ul>
                {session.playerList.map((player) => (
                    <li key={player.name}>
                        <SessionEndPlayerTile player={player} />
                    </li>
                ))}
            </ul>
            <ButtonTab>
                <ButtonBig>SAVE</ButtonBig>
            </ButtonTab>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-rows: auto auto 1fr;
    justify-items: center;

    img {
        margin: 20px 0;
        max-width: 50%;
        border-bottom-right-radius: 20px;
        box-shadow: 2px 2px lightslategray;
    }

    p {
        margin: 0;
        padding: 0;
    }

    ul {
        list-style: none;
        margin: 0;
        padding: 20px;
    }
`;
