import styled from 'styled-components/macro';

export default styled.div`
    height: 100vh;
    display: grid;
    grid-template-rows: auto 1fr auto auto;

    main {
        background: var(--primary);
        padding: 0 16px;
        overflow-y: hidden;
    }
`;
