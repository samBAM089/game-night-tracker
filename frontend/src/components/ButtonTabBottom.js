import styled from 'styled-components/macro';
import ButtonBig from './ButtonBig';

export default function ButtonTabBottom() {
    return (
        <Wrapper>
            <ButtonBig>LET'S PLAY</ButtonBig>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    background: var(--primary);
    border-top: 1px solid rgba(103, 103, 184, 0.53);
    text-align: center;
    padding: 10px;
`;
