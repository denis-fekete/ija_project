/**
 * Class containing simple simulation command used for logging of Robots and Obstacles
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

public class SimulationCommand extends Command
{
    SimulationCommand(CommandType type, int logId)
    {
        this.type = type;
        this.logId = logId;
    }
}
