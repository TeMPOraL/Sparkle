package Model;

import java.util.ArrayList;
import java.util.List;

import Helpers.EnvSettings;
import Model.World.CellIndex;

/**
 * 
 * 
 * class not used
 * 
 */
public class VaporConducter
{
    public void conductVepors( Cell cell, Cell worldCurrentValues[][][],
            List<World.CellIndex> cellNeighboours, CellIndex cellId )
    {
        double notNeededVapors = cell.get_percentageOfVaporsInCell()
                - EnvSettings.MIN_VAPORS_TO_FIRE;
        List<Cell> airNeighs = new ArrayList<Cell>();
        if( notNeededVapors > 0.0 )
        {
            for( int i = 0; i < cellNeighboours.size(); ++i )
            {
                World.CellIndex neighId = cellNeighboours.get( i );
                if( worldCurrentValues[ neighId.x ][ neighId.y ][ neighId.z ].get_material()
                        .get_name().equals( "Air" ) )
                {
                    airNeighs.add( worldCurrentValues[ neighId.x ][ neighId.y ][ neighId.z ] );
                    if( HeataAndVaporsConducter.getWhichNeighbour( cellId, neighId ) == EnvSettings.TOP_NEIGHBOUR )
                    {
                        airNeighs.add( worldCurrentValues[ neighId.x ][ neighId.y ][ neighId.z ] );
                    }
                }
            }
            double vaporsForEachCell = notNeededVapors / airNeighs.size();
            for( int i = 0; i < airNeighs.size(); ++i )
            {
                // if( airNeighs.get( i ).get_percentageOfVaporsInCell() < 1.0 )
                {
                    airNeighs
                            .get( i )
                            .set_percentageOfVaporsInCell(
                                ( (float)( airNeighs.get( i ).get_percentageOfVaporsInCell() + vaporsForEachCell ) ) );
                    cell.set_percentageOfVaporsInCell( (float)( cell.get_percentageOfVaporsInCell() - vaporsForEachCell ) );
                }
            }
        }
    }
}