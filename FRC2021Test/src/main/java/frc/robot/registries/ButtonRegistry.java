package frc.robot.registries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import frc.robot.configs.ButtonConfig;

public class ButtonRegistry {

    private Map<String, ButtonConfig> buttons = new HashMap<String, ButtonConfig>();

    public void registerButton(String name, ButtonConfig buttonConfig)
    {
        buttons.put(name, buttonConfig);
    }

    public void printRegisteredButtons()
    {
        for(Map.Entry<String, ButtonConfig> button : buttons.entrySet())
        {
            String name = (String)button.getKey();
            ButtonConfig buttonConfig = (ButtonConfig)button.getValue();
            System.out.println("Button: " + name + "\n" +
                               "Joytick: " + buttonConfig.assignedJoystick.toString() + "\n" +
                               "Button Index: " + buttonConfig.buttonIndex);
        }
    }
}
