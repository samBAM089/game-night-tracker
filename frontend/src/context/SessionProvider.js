import { createContext, useContext, useState, useEffect } from 'react';

export const SessionContext = createContext();

export const SessionProvider = ({ children }) => {
    const [session, setSession] = useState();

    const updateSession = (updatedSession) => {
        setSession(updatedSession);
        localStorage.setItem(
            'gameNightTracker',
            JSON.stringify(updatedSession)
        );
    };

    useEffect(() => {
        const localStorageSession = localStorage.getItem('gameNightTracker');
        if (localStorageSession) {
            setSession(JSON.parse(localStorageSession));
        }
    }, []);

    return (
        <SessionContext.Provider value={[session, updateSession]}>
            {children}
        </SessionContext.Provider>
    );
};

export const useSession = () => useContext(SessionContext);
