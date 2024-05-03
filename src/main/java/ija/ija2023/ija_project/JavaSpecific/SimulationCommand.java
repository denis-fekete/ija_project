package ija.ija2023.ija_project.JavaSpecific;

public class SimulationCommand extends Command
{
    SimulationCommand(CommandType type, int logId)
    {
        this.type = type;
        this.logId = logId;
    }
}
