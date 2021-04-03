import SessionBoard from '../components/SessionBoard';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';
import Footer from '../components/Footer';
import ButtonTabBottom from '../components/ButtonTabBottom';
import { Redirect } from 'react-router-dom';
import { useState, useEffect } from 'react';
import * as gameNightTrackerApi from '../services/gameNightTrackerApi';

export default function Overview({ jwtToken }) {
    const [sessions, setSessions] = useState([]);

    useEffect(() => {
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
                <SessionBoard sessions={sessions} />;
            </main>
            <ButtonTabBottom />
            <Footer />
        </PageLayout>
    );
}
