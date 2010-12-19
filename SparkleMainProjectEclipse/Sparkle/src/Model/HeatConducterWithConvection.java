package Model;

import Helpers.EnvSettings;

public class HeatConducterWithConvection extends HeatConducter
{
    static final double SIDE_ENERGY_FACTOR = 0.15;
    static final double TOP_ENERGY_FACTOR = 0.15;
    static final double DOWN_ENERGY_FACTOR = 0.15;
    @Override
    public double calculateEnergyFlow( Cell cell, Cell neigh, int whichNeighbour )
    {
        double energy = super.calculateEnergyFlow( cell, neigh, whichNeighbour );
        // wymiana ciepla w powietrzu
        if( cell.get_material().get_name().equals( "Air" )
                && neigh.get_material().get_name().equals( "Air" ) )
        {
            energy *= EnvSettings.CONSTANT_ENERGY_FACTOR;

			if( whichNeighbour == EnvSettings.TOP_NEIGHBOUR )
            {
                energy *= TOP_ENERGY_FACTOR;

            if( ( whichNeighbour == EnvSettings.TOP_NEIGHBOUR && energy < 0 )
                    || ( whichNeighbour == EnvSettings.BOTTOM_NEIGBOUR && energy > 0 ) )
            {
                energy *= TOP_ENERGY_FACTOR;

            }
            else if( ( whichNeighbour == EnvSettings.BOTTOM_NEIGBOUR && energy < 0 )
                    || ( whichNeighbour == EnvSettings.TOP_NEIGHBOUR && energy > 0 ) )
            {
                energy *= DOWN_ENERGY_FACTOR;
            }
            else
            {
                energy *= SIDE_ENERGY_FACTOR;
            }
        }
        return energy;
    }
}
