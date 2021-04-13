import styled from 'styled-components/macro';
import { useState } from 'react';
import ButtonBig from './ButtonBig';
import ButtonTab from './ButtonTab';
import { GiMeeple } from 'react-icons/all';

export default function SessionTimer({
    setStartDate,
    setSessionDuration,
    session,
}) {
    const [timer, setTimer] = useState({ s: 0, m: 0, h: 0 });
    const [startTimeForSession, setStartTimeForSession] = useState('');
    const [stopTimeForSession, setStopTimeForSession] = useState('');
    const [intervalId, setIntervalId] = useState();
    const [gameHasStarted, setGameHasStarted] = useState(false);
    const [duration, setDuration] = useState();

    const onClickToStart = () => {
        const startDate = new Date();
        let startDateTimeStamp = startDate.getTime();
        setStartTimeForSession(startDateTimeStamp);
        setStartDate(startDate);
        run();
        const intervalId = setInterval(run, 1000);
        setIntervalId(intervalId);
        setGameHasStarted(true);
    };

    const onClickToStop = () => {
        const stopDate = new Date();
        let stopDateTimeStamp = stopDate.getTime();
        setStopTimeForSession(stopDateTimeStamp);

        clearInterval(intervalId);
        setGameHasStarted(false);
        const gameDuration = (
            (new Date().getTime() - startTimeForSession) /
            1000 /
            60
        )
            .toString()
            .split('.')[0];

        setDuration(gameDuration);
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
        setSessionDuration(duration);
    };

    return (
        <>
            <Wrapper>
                <img src={session.imageUrl} alt="game cover" />
                <section className="timer">
                    <span className="time">
                        {timer.h >= 10 ? timer.h : '0' + timer.h}
                    </span>
                    <span className="colon">:</span>
                    <span className="time">
                        {timer.m >= 10 ? timer.m : '0' + timer.m}
                    </span>
                    <span className="colon">:</span>
                    <span className="time">
                        {timer.s >= 10 ? timer.s : '0' + timer.s}
                    </span>
                </section>
                {!gameHasStarted && (
                    <MeepleSection>
                        <MeepleX />
                        <MeepleX />
                        <MeepleX />
                        <MeepleX />
                    </MeepleSection>
                )}
                {gameHasStarted && (
                    <MeepleSection>
                        <Meeple />
                        <Meeple className="two" />
                        <Meeple className="three" />
                        <Meeple className="four" />
                    </MeepleSection>
                )}

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
    display: grid;
    grid-template-rows: auto auto auto auto;
    justify-items: center;
    height: 100%;
    background: var(--primary);
    padding: 0 16px;
    overflow-y: hidden;

    img {
        margin: 20px 0;
        max-width: 50%;
        border-bottom-right-radius: 20px;
        border-right: 2px solid rgba(103, 103, 184, 0.53);
        border-bottom: 2px solid rgba(103, 103, 184, 0.53);
    }

    .timer {
        margin-top: 20px;
        text-align: center;
    }

    .time {
        border: 1px solid white;
        border-radius: 5px;
        padding: 20px;
        margin: 0 5px;
        font-size: 2em;
    }

    .colon {
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

    div {
        align-self: end;
        margin-top: 15px;
        text-align: center;
    }
`;

const MeepleSection = styled.section`
    padding: 0 20px;
    margin: 15px 0;
    display: grid;
    grid-template-columns: auto auto auto auto;
    justify-items: center;
    align-self: center;
`;

const MeepleX = styled(GiMeeple)`
    color: white;
    transform: scale(3);
    margin: 25px 20px;
`;

const Meeple = styled(GiMeeple)`
    margin: 25px 20px;
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
