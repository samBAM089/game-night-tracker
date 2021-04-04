import { useSession } from '../context/SessionProvider';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';
import GamesBoard from '../components/GamesBoard';
import ButtonTab from '../components/ButtonTab';
import Footer from '../components/Footer';
import ButtonBig from '../components/ButtonBig';
import { useState, useEffect } from 'react';
import * as gameNightTrackerApi from '../services/gameNightTrackerApi';

export default function Session() {
    const [session, updateSession] = useSession();
    const [games, setGames] = useState([]);

    useEffect(() => {
        gameNightTrackerApi
            .getAllGames()
            .then((gamelist) => setGames(gamelist))
            .catch((error) => console.error(error));
    });

    const selectGame = (game) => {
        const gameSession = {
            gameName: game.name,
            imageUrl: game.thumbnailUrl,
            startDateTimestamp: '',
            duration: '',
            playerList: [],
            winnerPlayerId: '',
            status: 'addPlayers',
        };

        updateSession(gameSession);
    };

    return (
        <PageLayout>
            <Header />
            <main>
                <GamesBoard games={games} selectGame={selectGame} />
            </main>
            <ButtonTab>
                <ButtonBig>CONTINUE</ButtonBig>
            </ButtonTab>
            <Footer />
        </PageLayout>
    );
}
