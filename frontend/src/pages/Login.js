import PageLayout from '../components/PageLayout';
import styled from 'styled-components/macro';
import Footer from '../components/Footer';
import { useState } from 'react';
import { loginUser } from '../services/loginService';
import { Redirect } from 'react-router-dom';
import ButtonBig from '../components/ButtonBig';
import { GiPerspectiveDiceSixFacesFour } from 'react-icons/all';
import { IconContext } from 'react-icons';

export default function Login({ setJwtToken, jwtToken }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        if (!username || !password) {
            return;
        }
        loginUser(username, password).then(setJwtToken).catch(setError(true));
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
                    <Image src="./images/gnt_logo.png" alt="appLogo" />
                    {!error && (
                        <p>
                            <IconContext.Provider
                                value={{
                                    style: {
                                        verticalAlign: 'middle',
                                    },
                                }}
                            >
                                <GiPerspectiveDiceSixFacesFour />
                            </IconContext.Provider>{' '}
                            IT'S GAME TIME!
                        </p>
                    )}
                    {error && <p>Please try again!</p>}

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

                        <ButtonBig type="submit">Login</ButtonBig>
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
    grid-template-rows: 1fr auto 1fr;
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
        outline: none;
    }
`;

const Image = styled.img`
    width: 70%;
`;
