import { useSession } from '../context/SessionProvider';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';
import GamesBoard from '../components/GamesBoard';
import PlayersBoard from '../components/PlayersBoard';
import RandomPlayerOrder from '../components/RandomPlayerOrder';
import Footer from '../components/Footer';
import { useState, useEffect } from 'react';
import * as gameNightTrackerApi from '../services/gameNightTrackerApi';

export default function Session() {
    const [session, updateSession] = useSession();
    const [games, setGames] = useState([]);
    const [existingPlayers, setExistingPlayers] = useState([]);

    useEffect(() => {
        gameNightTrackerApi
            .getAllGames()
            .then((gamelist) => setGames(gamelist))
            .catch((error) => console.error(error));
    }, []);

    useEffect(() => {
        gameNightTrackerApi
            .getAllPlayers()
            .then((playersInDb) => setExistingPlayers(playersInDb))
            .catch((error) => console.error(error));
    }, []);

    const selectGame = (gameToPlay) => {
        const gameSession = {
            gameName: gameToPlay.name,
            imageUrl: gameToPlay.thumbnailUrl,
            startDateTimestamp: '',
            duration: '',
            playerList: [],
            winnerPlayerId: '',
            status: 'addPlayers',
        };
        updateSession(gameSession);
    };

    const selectPlayer = (players) => {
        const updatedGameSession = {
            ...session,
            playerList: players,
            status: 'randomizePlayers',
        };
        updateSession(updatedGameSession);
    };

    return (
        <PageLayout>
            <Header />
            <main>
                {!session && (
                    <GamesBoard games={games} selectGame={selectGame} />
                )}
                {session && session.status === 'addPlayers' && (
                    <PlayersBoard
                        existingPlayers={existingPlayers}
                        selectPlayer={selectPlayer}
                    />
                )}
                {session && session.status === 'randomizePlayers' && (
                    <RandomPlayerOrder session={session} />
                )}
            </main>

            <Footer />
        </PageLayout>
    );
}
