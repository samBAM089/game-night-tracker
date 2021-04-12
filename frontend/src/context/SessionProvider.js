import { createContext, useContext, useState } from 'react';

export const SessionContext = createContext();

export const SessionProvider = ({ children }) => {
    const [session, setSession] = useState();

    const updateSession = (updatedSession) => {
        setSession(updatedSession);
    };

    return (
        <SessionContext.Provider value={[session, updateSession]}>
            {children}
        </SessionContext.Provider>
    );
};

export const useSession = () => useContext(SessionContext);
