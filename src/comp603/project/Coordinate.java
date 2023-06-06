/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603.project;

/**
 *
 * @author tjack
 */
public class Coordinate
{
    private final int x;
    private final int y;
    
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (!(o instanceof Coordinate))
        {
            return false;
        }
        
        Coordinate other = (Coordinate) o;
        return ((this.x == other.x) & (this.y == other.y));
    }
    
    @Override
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 180 * hashCode + this.x + this.y;
        return hashCode;
    }
}

