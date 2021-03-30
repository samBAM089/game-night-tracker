import styled from 'styled-components/macro';

export default function Header() {
    return (
        <Wrapper>
            <div>
                <img src="./images/gnt_logo_quer2.png" alt="" />
            </div>
        </Wrapper>
    );
}

const Wrapper = styled.header`
    background: darkslateblue;
    padding: 10px 16px;
    text-align: center;
    border-bottom: 1px solid rgba(103, 103, 184, 0.53);

    img {
        text-align: center;
        margin: 5px 0 0 0;
        width: 90vw;
    }
`;
