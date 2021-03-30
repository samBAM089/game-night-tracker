import styled from 'styled-components/macro';

export default function Footer() {
    return (
        <Wrapper>
            <h5>powered by</h5>
            <img src="./images/bf-logo.png" alt="logo" />
        </Wrapper>
    );
}

const Wrapper = styled.section`
    display: grid;
    grid-template-columns: auto auto;
    background: darkslateblue;
    margin: 0;
    padding: 0;

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
