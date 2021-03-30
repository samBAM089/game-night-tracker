import styled from 'styled-components/macro'
import SessionTile from './SessionTile'


export default function ListBoard() {
    return (
        <Wrapper className="box">
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
            <li>
                <SessionTile/>
            </li>
        </Wrapper>
    )
}

const Wrapper = styled.ul`
  height: 100%;
  background: darkgrey;
  list-style: none;
  display: grid;
  padding: 0;
  margin: 10px 0;
  overflow-y: scroll;
`