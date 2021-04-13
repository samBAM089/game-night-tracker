import { Gi3DMeeple } from 'react-icons/all';
import { IconContext } from 'react-icons';
import TileLayout from './TileLayout';

export default function SessionPlayerTile({ player }) {
    return (
        <TileLayout>
            <IconContext.Provider
                value={{ color: player.color, size: '1.5em' }}
            >
                <Gi3DMeeple />
            </IconContext.Provider>
            <span>{player.name}</span>
        </TileLayout>
    );
}
