import PageLayout from '../components/PageLayout';
import styled from 'styled-components/macro';
import Footer from '../components/Footer';
import Header from '../components/Header';
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
            <Header />
            <main>
                <form onSubmit={handleSubmit}>
                    <input
                        placeholder="username"
                        type="text"
                        value={username}
                        onChange={({ target }) => setUsername(target.value)}
                    />
                    <input
                        placeholder="password"
                        type="text"
                        value={password}
                        onChange={({ target }) => setPassword(target.value)}
                    />

                    <button type="submit">Login</button>
                </form>
            </main>

            <Footer />
        </PageLayout>
    );

    const Wrapper = styled.section`
        display: grid;
        grid-template-rows: 1fr auto;
    `;
}
