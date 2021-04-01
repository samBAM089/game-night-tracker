import PageLayout from '../components/PageLayout';
import styled from 'styled-components/macro';
import Footer from '../components/Footer';
import { useState } from 'react';
import { loginUser } from '../services/loginService';
import { Redirect } from 'react-router-dom';

export default function Login({ setJwtToken, jwtToken }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        if (!username && !password) {
            return;
        }
        loginUser(username, password).then(setJwtToken);
        setUsername('');
        setPassword('');
    };

    if (jwtToken) {
        return <Redirect to={'/'} />;
    }

    return (
        <PageLayout>
            <main>
                <Wrapper>
                    <Image src="./images/gnt_logo.png" alt="" />
                    <Form onSubmit={handleSubmit}>
                        <input
                            placeholder="username"
                            type="text"
                            value={username}
                            onChange={({ target }) => setUsername(target.value)}
                        />
                        <input
                            placeholder="password"
                            type="password"
                            value={password}
                            onChange={({ target }) => setPassword(target.value)}
                        />

                        <button type="submit">Login</button>
                    </Form>
                </Wrapper>
            </main>

            <Footer />
        </PageLayout>
    );
}

const Wrapper = styled.section`
    height: 100vh;
    display: grid;
    justify-items: center;
    align-items: center;
`;

const Form = styled.form`
    width: 60%;
    display: grid;
    grid-template-rows: min-content min-content min-content;
    grid-gap: 10px;

    input {
        border-radius: 5px;
        border: none;
        padding: 3px;
        text-align: center;
    }

    button {
        border-radius: 50px;
        padding: 10px 50px;
        margin: 8px;
        background: #c8a1a2;
        color: white;
        border: none;
    }
`;

const Image = styled.img`
    width: 70%;
`;
