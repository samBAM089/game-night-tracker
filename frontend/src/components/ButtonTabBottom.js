import styled from 'styled-components/macro'
import ButtonBig from './ButtonBig'

export default function ButtonTabBottom() {
    return (
        <Wrapper>
            <ButtonBig/>
        </Wrapper>
    )
}

const Wrapper = styled.section`
  background: darkslateblue;
  text-align: center;
`