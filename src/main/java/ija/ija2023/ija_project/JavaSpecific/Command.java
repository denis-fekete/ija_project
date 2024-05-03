/**

 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

public abstract class Command
{
    CommandType type;

    int logId;

    public CommandType getType()
    {
        return this.type;
    }
}

