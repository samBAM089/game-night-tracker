import { Gi3DMeeple } from 'react-icons/all';
import { IconContext } from 'react-icons';
import TileLayout from './TileLayout';

export default function PlayerTile({ existingPlayer, onChange }) {
    return (
        <TileLayout>
            <IconContext.Provider
                value={{ color: existingPlayer.color, size: '1.5em' }}
            >
                <Gi3DMeeple />
            </IconContext.Provider>
            <span>{existingPlayer.name}</span>
            <input
                type="checkbox"
                onChange={(event) => onChange(existingPlayer, event)}
            />
        </TileLayout>
    );
}
