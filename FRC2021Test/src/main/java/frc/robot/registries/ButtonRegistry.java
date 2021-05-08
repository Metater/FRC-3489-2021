package frc.robot.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import frc.robot.configs.ButtonConfig;
import frc.robot.types.ButtonActionType;
import frc.robot.types.ButtonLocation;

public class ButtonRegistry {

    private Map<ButtonActionType, ButtonConfig> buttons = new HashMap<ButtonActionType, ButtonConfig>();

    public void registerButton(ButtonActionType buttonType, ButtonConfig buttonConfig)
    {
        buttons.put(buttonType, buttonConfig);
    }

    public void printRegisteredButtons()
    {
        for(Map.Entry<ButtonActionType, ButtonConfig> button : buttons.entrySet())
        {
            ButtonActionType buttonType = (ButtonActionType)button.getKey();
            ButtonConfig buttonConfig = (ButtonConfig)button.getValue();
            System.out.println("Button: " + buttonType.toString());
            System.out.println("Locations:");
            int location = 1;
            for(ButtonLocation buttonLocation : buttonConfig.buttonLocations)
            {
                System.out.println("\tLocation " + location);
                System.out.println("\tJoystick: " + buttonLocation.assignedJoystick.toString());
                System.out.println("\tButton Index: " + buttonLocation.buttonIndex);
                location++;
            }
        }
    }

    public List<Map.Entry<ButtonActionType, ButtonConfig>> getTeleopPeriodicButtons()
    {

        for(Map.Entry<ButtonActionType, ButtonConfig> button : buttons.entrySet())
        {
    }
}
