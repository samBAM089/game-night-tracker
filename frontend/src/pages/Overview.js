import SessionBoard from '../components/SessionBoard';
import ButtonTab from '../components/ButtonTab';
import { Redirect, Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import * as gameNightTrackerApi from '../services/gameNightTrackerApi';
import ButtonBig from '../components/ButtonBig';
import Header from '../components/Header';
import PageLayout from '../components/PageLayout';
import Footer from '../components/Footer';

export default function Overview({ jwtToken }) {
    const [sessions, setSessions] = useState([]);

    useEffect(() => {
        jwtToken &&
            gameNightTrackerApi
                .getAllSessions(jwtToken)
                .then((sessionList) => setSessions(sessionList))
                .catch((error) => console.error(error));
    }, [jwtToken]);

    if (!jwtToken) {
        return <Redirect to="/login" />;
    }

    return (
        <PageLayout>
            <Header />
            <main>
                <SessionBoard sessions={sessions} />
            </main>
            <ButtonTab>
                <Link to={'/new'}>
                    <ButtonBig>LET'S PLAY</ButtonBig>
                </Link>
            </ButtonTab>
            <Footer />
        </PageLayout>
    );
}
