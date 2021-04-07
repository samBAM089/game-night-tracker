import { useSession } from '../context/SessionProvider';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';
import GamesBoard from '../components/GamesBoard';
import PlayersBoard from '../components/PlayersBoard';
import RandomPlayerOrder from '../components/RandomPlayerOrder';
import Footer from '../components/Footer';
import { useState, useEffect } from 'react';
import * as gameNightTrackerApi from '../services/gameNightTrackerApi';
import SessionTimer from '../components/SessionTimer';
import SessionEnd from '../components/SessionEnd';

export default function Session() {
    const [session, updateSession] = useSession();
    const [games, setGames] = useState([]);
    const [existingPlayers, setExistingPlayers] = useState([]);
    const [playersAdded, setPlayersAdded] = useState(false);

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
        if (players) {
            const updatedGameSession = {
                ...session,
                playerList: players,
                status: 'randomizePlayers',
            };
            updateSession(updatedGameSession);
        }
    };

    const randomizePlayer = (playerListToRandomize) => {
        if (playerListToRandomize) {
            const updatedPlayerList = {
                ...session,
                playerList: playerListToRandomize,
            };
            updateSession(updatedPlayerList);
        }
    };

    const startTimer = () => {
        if (playersAdded) {
            const sessionStart = {
                ...session,
                status: 'startSession',
            };
            updateSession(sessionStart);
        }
    };

    const setStartDate = (startTime) => {
        if (startTime) {
            const updateSessionTime = {
                ...session,
                startDateTimestamp: startTime,
            };
            updateSession(updateSessionTime);
        }
    };

    const setSessionDuration = (duration) => {
        if (duration) {
            const updateDuration = {
                ...session,
                duration: duration,
                status: 'scoring',
            };
            updateSession(updateDuration);
        }
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
                        setExistingPlayers={setExistingPlayers}
                        selectPlayer={selectPlayer}
                    />
                )}
                {session && session.status === 'randomizePlayers' && (
                    <RandomPlayerOrder
                        session={session}
                        randomizePlayer={randomizePlayer}
                        setPlayersAdded={setPlayersAdded}
                        startTimer={startTimer}
                    />
                )}
                {session && session.status === 'startSession' && (
                    <SessionTimer
                        setStartDate={setStartDate}
                        setSessionDuration={setSessionDuration}
                    />
                )}
                {session && session.status === 'scoring' && (
                    <SessionEnd session={session} />
                )}
            </main>

            <Footer />
        </PageLayout>
    );
}
