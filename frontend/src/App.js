import styled from 'styled-components/macro'
import ListBoard from './components/ListBoard'
import ButtonTabBottom from "./components/ButtonTabBottom";


export default function App() {
    return (
        <PageLayout>
            <header>
                <h1>GAME NIGHT TRACKER</h1>
            </header>
            <main>
                <ListBoard/>
            </main>
            <ButtonTabBottom/>
            <footer> powered by</footer>
        </PageLayout>
    )
}

const PageLayout = styled.div`
  height: 100vh;
  display: grid;
  grid-template-rows: auto 1fr auto;

  header {
    background: darkslateblue;
    padding: 0 16px;
    text-align: center;
  }

  main {
    background: darkslateblue;
    padding: 0 16px;
    overflow-y: hidden;
  }

  footer {
    background: darkslateblue;
    text-align: center;
    padding: 5px;
  }
`