import styled from 'styled-components/macro';

export default function Footer() {
    return (
        <Wrapper>
            <h5>powered by</h5>
            <a
                href="https://www.instagram.com/brettspielfieber/?hl=de}"
                target="_blank"
                rel="noreferrer"
            >
                <img src="./images/bf-logo.png" alt=" logo" />
            </a>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-columns: auto auto;
    background: var(--primary);
    margin: 0;
    padding: 0;
    border-top: 2px solid var(--primary);

    img {
        width: 20px;
        margin-bottom: 5px;
        padding-left: 3px;
    }

    h5 {
        padding-bottom: 5px;
        margin: 0;
        text-align: right;
        color: rgba(255, 255, 255, 0.45);
    }
`;
