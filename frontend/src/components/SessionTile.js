import styled from 'styled-components/macro'

export default function SessionTile() {
    return (
        <Wrapper>
            <li>IMAGE</li>
            <li>GAME NAME</li>
            <li>DATE</li>
        </Wrapper>
    )
}

const Wrapper = styled.ul`
  background: dimgrey;
  list-style: none;
  border-radius: 5px;
  padding: 20px;
  margin: 20px 20px 0px 20px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-gap: 20px;
`