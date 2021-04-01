import StartSession from './pages/StartSession';
import Login from './pages/Login';
import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Overview from './pages/Overview';

export default function App() {
    const [jwtToken, setJwtToken] = useState();

    return (
        <Router>
            <Switch>
                <Route exact path="/">
                    <Overview jwtToken={jwtToken} />
                </Route>
                <Route exact path="/login">
                    <Login setJwtToken={setJwtToken} jwtToken={jwtToken} />
                </Route>
                <Route path="/newsession">
                    <StartSession />
                </Route>
            </Switch>
        </Router>
    );
}
