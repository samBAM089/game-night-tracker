import Session from './pages/Session';
import Login from './pages/Login';
import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Overview from './pages/Overview';
import { SessionProvider } from './context/SessionProvider';
import Header from './components/Header';
import Footer from './components/Footer';
import PageLayout from './components/PageLayout';

export default function App() {
    const [jwtToken, setJwtToken] = useState();

    return (
        <SessionProvider>
            <Router>
                <Switch>
                    <Route exact path="/">
                        <Overview jwtToken={jwtToken} />
                    </Route>
                    <Route exact path="/login">
                        <Login setJwtToken={setJwtToken} jwtToken={jwtToken} />
                    </Route>
                    <Route path="/new">
                        <Session />
                    </Route>
                </Switch>
            </Router>
        </SessionProvider>
    );
}
