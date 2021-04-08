import styled from 'styled-components/macro';
import { useState } from 'react';
import ButtonBig from './ButtonBig';
import ButtonTab from './ButtonTab';
import { GiMeeple } from 'react-icons/all';

export default function SessionTimer({ setStartDate, setSessionDuration }) {
    const [timer, setTimer] = useState({ s: 0, m: 0, h: 0 });
    const [startTimeForSession, setStartTimeForSession] = useState('');
    const [stopTimeForSession, setStopTimeForSession] = useState('');
    const [intervalId, setIntervalId] = useState();
    const [gameHasStarted, setGameHasStarted] = useState(false);
    const [duration, setDuration] = useState();

    const onClickToStart = () => {
        let startDate = new Date();
        let startDateTimeStamp = startDate.getTime();
        setStartTimeForSession(startDateTimeStamp);
        setStartDate(startDate);
        console.log(startDate);
        console.log(startDateTimeStamp);
        run();
        const intervalId = setInterval(run, 1000);
        setIntervalId(intervalId);
        setGameHasStarted(true);
    };

    const onClickToStop = () => {
        let stopDateTimeStamp = new Date().getTime();
        setStopTimeForSession(stopDateTimeStamp);
        console.log(stopTimeForSession);
        clearInterval(intervalId);
        setGameHasStarted(false);
    };

    let updatedS = timer.s,
        updatedM = timer.m,
        updatedH = timer.h;

    const run = () => {
        if (updatedS === 60) {
            updatedM++;
            updatedS = 0;
        }
        if (updatedM === 60) {
            updatedH++;
            updatedM = 0;
        }
        updatedS++;
        return setTimer({ s: updatedS, m: updatedM, h: updatedH });
    };

    const goToScoring = () => {
        const gameDuration = (
            (stopTimeForSession - startTimeForSession) /
            1000 /
            60
        )
            .toString()
            .split('.')[0];

        console.log(gameDuration);
        setDuration(gameDuration);
        setSessionDuration(duration);
    };

    return (
        <>
            <Wrapper>
                <p>QUALITY TIME</p>
                {!gameHasStarted && (
                    <section>
                        <MeepleX />
                        <MeepleX />
                        <MeepleX />
                        <MeepleX />
                    </section>
                )}
                {gameHasStarted && (
                    <section>
                        <Meeple />
                        <Meeple className="two" />
                        <Meeple className="three" />
                        <Meeple className="four" />
                    </section>
                )}
                <section className="timer">
                    <span>{timer.h >= 10 ? timer.h : '0' + timer.h}</span>
                    &nbsp;:&nbsp;
                    <span>{timer.m >= 10 ? timer.m : '0' + timer.m}</span>
                    &nbsp;:&nbsp;
                    <span>{timer.s >= 10 ? timer.s : '0' + timer.s}</span>
                </section>

                <div>
                    <ButtonBig
                        onClick={onClickToStart}
                        disabled={gameHasStarted}
                    >
                        START
                    </ButtonBig>
                    <ButtonBig
                        onClick={onClickToStop}
                        disabled={!gameHasStarted}
                    >
                        STOP
                    </ButtonBig>
                </div>
            </Wrapper>
            <ButtonTab>
                <ButtonBig
                    onClick={goToScoring}
                    disabled={!stopTimeForSession > 0}
                >
                    SCORING
                </ButtonBig>
            </ButtonTab>
        </>
    );
}

const Wrapper = styled.div`
    box-sizing: border-box;
    display: grid;
    grid-template-rows: auto 1fr auto;
    grid-gap: 30px;
    justify-content: stretch;
    text-align: center;

    p {
        margin: 20px;
        text-align: center;
        font-size: 1em;
    }

    .timer {
        text-align: center;

        margin: 50px 30px;
    }

    span {
        border: 1px solid white;
        border-radius: 5px;
        padding: 20px;
        margin: 0 5px;
        font-size: 2em;
    }

    .two {
        color: gold;
        animation-delay: 1s;
    }

    .three {
        color: lightskyblue;
        animation-delay: 2s;
    }

    .four {
        color: lightgreen;
        animation-delay: 3s;
    }
`;

const MeepleX = styled(GiMeeple)`
    margin: 0 20px 5px 20px;
    color: white;
    transform: scale(3);
`;

const Meeple = styled(GiMeeple)`
    margin: 0 20px 5px 20px;
    color: firebrick;
    transform: scale(3);
    -webkit-animation: Meeple 4s infinite linear;
    animation: Meeple 4s infinite linear;

    @-webkit-keyframes Meeple {
        0% {
            -webkit-transform: rotate(0);
            transform: rotate(0);
        }
        100% {
            -webkit-transform: rotate(359);
            transform: rotate(359);
        }
    }

    @keyframes Meeple {
        0% {
            -webkit-transform: rotate(0);
            transform: rotate(0);
        }
        100% {
            -webkit-transform: rotate(359);
            transform: rotate(359);
        }
    }
`;
