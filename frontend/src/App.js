import styled from 'styled-components/macro';
import ListBoard from './components/ListBoard';
import ButtonTabBottom from './components/ButtonTabBottom';
import Header from './components/Header';
import Footer from './components/Footer';

export default function App() {
    return (
        <PageLayout>
            <Header />
            <main>
                <ListBoard />
            </main>
            <ButtonTabBottom />
            <Footer />
        </PageLayout>
    );
}

const PageLayout = styled.div`
    height: 100vh;
    display: grid;
    grid-template-rows: auto 1fr auto auto;

    Header {
        overflow-x: hidden;
    }

    main {
        background: darkslateblue;
        padding: 0 16px;
        overflow-y: hidden;
    }

    Footer {
        background: darkslateblue;
        text-align: center;
        padding: 0;
        margin: 0;
    }
`;
