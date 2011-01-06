package Model;

import java.util.List;

import Helpers.EnvSettings;
import Helpers.EnvSettings.CellState;
import Interfaces.IFireConductor;

public class FireConducter implements IFireConductor
{
    public void spreadFire( Cell cell, List<World.CellIndex> cellNeighboours, Cell[][][] _world )
    {
        // jezeli kom�rka jest paliwem i przekroczy flame point / flash point
        // zaczyna
        // produkowac opary. Opary produkowane do biezacej kom�rki oraz s�siad�w
        // b�d�cych tlenem
        if( cell.get_temp() >= cell.get_material().get_flamePoint()
                && cell.get_material().is_fuel() && cell.get_cellState().equals( CellState.NEUTRAL ) )
        {
            cell.set_percentageOfVaporsInCell( 1.0f );
            for( int i = 0; i < cellNeighboours.size(); ++i )
            {
                World.CellIndex id = cellNeighboours.get( i );
                if( _world[ id.x ][ id.y ][ id.z ].get_material().get_name().equals( "Air" ) )
                {
                    _world[ id.x ][ id.y ][ id.z ]
                            .set_percentageOfVaporsInCell( (float)( EnvSettings.MIN_VAPORS_TO_FIRE ) );
                }
            }
        }
        // symualcja wypalania sie kom�rki. kom�rka mo�e si� pali� przez
        // okreslony czas.. je�eli kom�rka ma stan palenia si� zmniejszany jest
        // licznik a� osiagni� 0 - komrka wypali�a si�
        if( cell.get_cellState().equals( CellState.ON_FIRE ) )
        {
            if( cell.get_fireClock() > 0 )
            {
                cell.set_fireClock( cell.get_fireClock() - 1 );
            }
            else
            {
                cell.set_cellState( CellState.FIRED );
            }
        }
        // jezeli kom�rka nie pali sie a st�enie procentowe zawartych w niej
        // opar�w jest takie �e
        // powinna si� pali� oraz ma odpowiedni� temperatur� zapalamy tak�
        // kom�rk� i ustawiamy zegar odliczajacy czas jej spalania
        else if( cell.get_percentageOfVaporsInCell() >= EnvSettings.MIN_VAPORS_TO_FIRE
                && cell.get_temp() > EnvSettings.FIRE_TEMP )
        {
            cell.set_fireClock( cell.get_material().get_howLongItBurns() );
            cell.set_cellState( CellState.ON_FIRE );
        }
    }
}
