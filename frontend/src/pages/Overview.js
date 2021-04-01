import SessionBoard from '../components/SessionBoard';
import PageLayout from '../components/PageLayout';
import Header from '../components/Header';
import Footer from '../components/Footer';
import ButtonTabBottom from '../components/ButtonTabBottom';
import { Redirect } from 'react-router-dom';

export default function Overview({ jwtToken }) {
    const sessions = [
        {
            id: '1',
            thumbnail:
                'https://cf.geekdo-images.com/yfoefJ7a0cEhKxMUV0sDqQ__thumb/img/LYoDI0narqs87hfBnimak-qN2EQ=/fit-in/200x150/filters:strip_icc()/pic5955679.jpg',
            gameName: 'CloudAge',
            startDate: '01.01.2020',
            winner: 'samBAM',
        },
        {
            id: '2',
            thumbnail:
                'https://cf.geekdo-images.com/t8bTlZmxz6PiskMSjcBHcw__thumb/img/TByBGUDO_jNVxDaOviRWOFtu9fk=/fit-in/200x150/filters:strip_icc()/pic4815033.jpg',
            gameName: 'Calico',
            startDate: '03.01.2020',
            winner: 'Sonja',
        },
        {
            id: '3',
            thumbnail:
                'https://cf.geekdo-images.com/NSF3OBldDuQIfLIav_eL9Q__thumb/img/vdTdCpJPHw6UM3_MTiwr8YkEliU=/fit-in/200x150/filters:strip_icc()/pic3736981.jpg',
            gameName: 'Pulsar 2849',
            startDate: '09.01.2020',
            winner: 'Tim',
        },
        {
            id: '4',
            thumbnail:
                'https://cf.geekdo-images.com/9nGoBZ0MRbi6rdH47sj2Qg__thumb/img/ezXcyEsHhS9iRxmuGe8SmiLLXlM=/fit-in/200x150/filters:strip_icc()/pic5786795.jpg',
            gameName: 'Monopoly',
            startDate: '15.01.2020',
            winner: 'Albert Einstein',
        },
        {
            id: '5',
            thumbnail:
                'https://cf.geekdo-images.com/yfoefJ7a0cEhKxMUV0sDqQ__thumb/img/LYoDI0narqs87hfBnimak-qN2EQ=/fit-in/200x150/filters:strip_icc()/pic5955679.jpg',
            gameName: 'CloudAge',
            startDate: '31.01.2020',
            winner: 'Anna',
        },
    ];

    if (!jwtToken) {
        return <Redirect to="/login" />;
    }

    return (
        <PageLayout>
            <Header />
            <main>
                <SessionBoard sessions={sessions} />;
            </main>
            <ButtonTabBottom />
            <Footer />
        </PageLayout>
    );
}
