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
import SessionBoard from '../components/SessionBoard';
import { useHistory } from 'react-router';

export default function Session({ jwtToken }) {
    const [session, updateSession] = useSession();
    const [games, setGames] = useState([]);
    const [existingPlayers, setExistingPlayers] = useState([]);
    const history = useHistory();

    useEffect(() => {
        jwtToken &&
            gameNightTrackerApi
                .getAllGames(jwtToken)
                .then((gameList) => setGames(gameList))
                .catch((error) => console.error(error));
    }, [jwtToken]);

    useEffect(() => {
        jwtToken &&
            gameNightTrackerApi
                .getAllPlayers(jwtToken)
                .then((playersInDb) => setExistingPlayers(playersInDb))
                .catch((error) => console.error(error));
    }, [jwtToken]);

    const selectGame = (gameToPlay) => {
        const gameSession = {
            apiGameId: gameToPlay.apiGameId,
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
        const sessionStart = {
            ...session,
            status: 'startSession',
        };
        updateSession(sessionStart);
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
        const updateDuration = {
            ...session,
            duration: duration,
            status: 'scoring',
        };
        updateSession(updateDuration);
    };

    const setFinalPlayerList = (scoresAndWinner) => {
        const updatedScores = {
            ...session,
            playerList: scoresAndWinner.playerList,
            winnerPlayerId: scoresAndWinner.winnerPlayerId,
        };
        console.log(updatedScores);
        updateSession(updatedScores);

        gameNightTrackerApi
            .saveSession(jwtToken, updatedScores)
            .then(updateSession(null))
            .then(() => history.push('/'))
            .catch((error) => console.error(error));
    };

    return (
        <PageLayout>
            <Header />
            {!session && <GamesBoard games={games} selectGame={selectGame} />}
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
                    startTimer={startTimer}
                />
            )}
            {session && session.status === 'startSession' && (
                <SessionTimer
                    setStartDate={setStartDate}
                    setSessionDuration={setSessionDuration}
                    session={session}
                />
            )}
            {session && session.status === 'scoring' && (
                <SessionEnd
                    session={session}
                    setFinalPlayerList={setFinalPlayerList}
                />
            )}
            {session && session.status === 'endSession' && <SessionBoard />}

            <Footer />
        </PageLayout>
    );
}
