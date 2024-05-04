/**
 * Abstract class containing simple command used for logging of Robots and Obstacles
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

public abstract class Command
{
    /**
     * Type of the command
     */
    CommandType type;

    /**
     * Log of the simulation when this command was created
     */
    int logId;

    /**
     * @return Returns type of the command
     */
    public CommandType getType()
    {
        return this.type;
    }
}

