package Model;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

import Helpers.EnvSettings;
import View.Scene3D;

public class World
{
    private static List<Material> _availableMaterials = new ArrayList<Material>();
    private Cell _worldCurrentValues[][][];
    private Cell _worldOldValues[][][]; // needed for double-buffering
    private static World _instance = new World(
        Scene3D.getScene( Controller.MainWindow._sceneCanvas ) );
    private Scene3D _scene;

    private void initMaterials()
    {
        get_availableMaterials().add(
            new Material( "Wood", EnvSettings.WOOD_COLOR, EnvSettings.WOOD_SPECIFIC_HEAT,
                EnvSettings.WOOD_TRANSPARENCY ) );
        get_availableMaterials().add(
            new Material( "Air", EnvSettings.AIR_COLOR, EnvSettings.AIR_SPECIFIC_HEAT,
                EnvSettings.AIR_TRANSPARENCY ) );
    }

    public static Material getMaterial( String materialName )
    {
        for( int i = 0; i < _availableMaterials.size(); ++i )
        {
            if( _availableMaterials.get( i ).get_name().equals( materialName ) )
            {
                return _availableMaterials.get( i );
            }
        }
        return null;
    }

    public void addBuildingPart( Point3d leftBottomBackCorner, Point3d size, String materialName,
            Scene3D scene ) throws ArrayIndexOutOfBoundsException
    {
        for( int i = (int)leftBottomBackCorner.x; i < leftBottomBackCorner.x + size.x; ++i )
        {
            for( int j = (int)leftBottomBackCorner.y; j < leftBottomBackCorner.y + size.y; ++j )
            {
                for( int k = (int)leftBottomBackCorner.z; k < leftBottomBackCorner.z + size.z; ++k )
                {
                    Material mat = getMaterial( materialName );
                    _worldCurrentValues[ i ][ j ][ k ].set_material( mat );
                    int blockIndex = Helpers.WorldSceneMediator.changeWorldIndexToSceneIndex( k, i,
                        j );
                    System.out.println( "index of block to update " + blockIndex );
                    scene.updateBlock( mat, blockIndex );
                }
            }
        }
    }

    public void initWorld( Scene3D scene )
    {
        int airId = 1;
        for( int i = 0; i < EnvSettings.getMAX_LENGTH(); ++i )
        {
            for( int j = 0; j < EnvSettings.getMAX_LENGTH(); ++j )
            {
                for( int k = 0; k < EnvSettings.getMAX_LENGTH(); ++k )
                {
                    // System.out.println( "material  under air name " +
                    // getMaterial( "Air" ) );
                    _worldCurrentValues[ i ][ j ][ k ] = new Cell( getMaterial( "Air" ), 20.0 );
                    _worldOldValues[ i ][ j ][ k ] = new Cell(
                        get_availableMaterials().get( airId ), 20.0 );
                }
            }
        }
        Material defaultMaterial = getMaterial( "Air" );
        assert ( defaultMaterial != null );
        scene.createdWorldRepresentation( defaultMaterial, EnvSettings.getMAX_LENGTH(),
            EnvSettings.getMAX_LENGTH(), EnvSettings.getMAX_LENGTH() );
    }

    // public void cleanWorld()
    // {
    // for( int i = 0; i < EnvSettings.getMAX_LENGTH(); ++i )
    // {
    // for( int j = 0; j < EnvSettings.getMAX_LENGTH(); ++j )
    // {
    // for( int k = 0; k < EnvSettings.getMAX_LENGTH(); ++k )
    // {
    // System.out.println( _worldCurrentValues[ i ][ j ][ k ].get_material() );
    // Material mat = getMaterial( "Air" );
    // _worldCurrentValues[ i ][ j ][ k ].set_material( mat );
    // }
    // }
    // }
    // Scene3D.getScene( MainWindow._sceneCanvas ).cleanScene();
    // }
    public static World getWorld( Scene3D scene )
    {
        return _instance;
    }

    private World( Scene3D scene )
    {
        _scene = scene;
        _worldCurrentValues = new Cell[ EnvSettings.getMAX_LENGTH() ][ EnvSettings.getMAX_LENGTH() ][ EnvSettings
                .getMAX_LENGTH() ];
        set_worldOldValues( new Cell[ EnvSettings.getMAX_LENGTH() ][ EnvSettings.getMAX_LENGTH() ][ EnvSettings
                .getMAX_LENGTH() ] );
        initMaterials();
        initWorld( scene );
    }

    public void set_availableMaterials( List<Material> _availableMaterials )
    {
        this._availableMaterials = _availableMaterials;
    }

    public List<Material> get_availableMaterials()
    {
        return _availableMaterials;
    }

    public void set_worldOldValues( Cell _worldOldValues[][][] )
    {
        this._worldOldValues = _worldOldValues;
    }

    public Cell[][][] get_worldOldValues()
    {
        return _worldOldValues;
    }

    public Cell[][][] get_worldCurrentValues()
    {
        return _worldCurrentValues;
    }

    public void set_worldCurrentValues( Cell[][][] currentValues )
    {
        _worldCurrentValues = currentValues;
    }
}
