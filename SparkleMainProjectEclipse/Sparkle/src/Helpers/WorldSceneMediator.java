package Helpers;

import javax.vecmath.Vector3d;

import View.Scene3D;

//not tested - don't know if it works
public class WorldSceneMediator
{
    // don't touch. jakas magia zeby dzialalo
    // jaka magia? normalne mnozenie + jakis offset, bo J3D jest dziwne.
    public static int changeWorldIndexToSceneIndex( int worldX, int worldY, int worldZ )
    {
        int id = worldX * ( EnvSettings.getMAX_Z() * EnvSettings.getMAX_Y() ) + worldY
                * EnvSettings.getMAX_Z() + worldZ + Scene3D.get_contentsOffset();
        return id;
    }

    public static Vector3d changeWorldPlacementToScenePlacement( int x, int y, int z,
            double blockSize )
    {
        Vector3d offset = new Vector3d( -0.25, -0.25, 0 );
        Vector3d blockPlacement = new Vector3d( 0, 0, 0 );
        blockPlacement.x = offset.x + x * blockSize + blockSize / 2.0;
        blockPlacement.y = offset.y + y * blockSize + blockSize / 2.0;
        blockPlacement.z = offset.z + z * blockSize + blockSize / 2.0;
        return blockPlacement;
    }

    public static class WorldIndex
    {
        public int x;
        public int y;
        public int z;

        @Override
        public String toString()
        {
            return "[ " + x + " " + y + " " + z + " ]";
        }

        @Override
        public boolean equals( Object o )
        {
            if( o == this )
            {
                return true;
            }
            if( !( o instanceof WorldIndex ) )
            {
                return false;
            }
            WorldIndex wi = (WorldIndex)o;
            return wi.x == this.x && wi.y == this.y && wi.z == this.z;
        }

	@Override
	public int hashCode()
	{
	    int hash = 5;
	    hash = 79 * hash + this.x;
	    hash = 79 * hash + this.y;
	    hash = 79 * hash + this.z;
	    return hash;
	}
    };
}
