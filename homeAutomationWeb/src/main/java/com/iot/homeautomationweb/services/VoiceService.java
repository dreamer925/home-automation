package com.iot.homeautomationweb.services;

import ai.picovoice.rhino.RhinoInference;
import com.iot.homeautomationweb.models.Device;
import com.iot.homeautomationweb.models.Light;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VoiceService {
    private final ApplianceService applianceService;
    public String getCommand(RhinoInference inference) {
        String intent = inference.getIntent();
        Map<String, String> slots = inference.getSlots();
        if(Objects.equals(intent, "changeColor")){
            // by default actions apply to all lights unless location is specified
            if (slots.containsKey("location") && applianceService.findLightByLocation(slots.get("location")).isPresent()){
                Light light = applianceService.findLightByLocation(slots.get("location")).get();
                light.setState(true);
                light.setColor(slots.get("color"));
                applianceService.updateLight(light.getId(), light);

                return "light";
            }
            applianceService.getUserLights().forEach(e -> {
                Light light = applianceService.findLightById(e.getId()).get();
                light.setState(true);
                light.setColor(slots.get("color"));
                applianceService.updateLight(light.getId(), light);
            });
            return "light";
        } else if(Objects.equals(intent, "changeLightState")){
            if (slots.containsKey("location")){
                if (Objects.equals(slots.get("state"), "on")){
                    Light light = applianceService.findLightByLocation(slots.get("location")).get();
                    light.setState(true);
                    light.setColor("white");
                    applianceService.updateLight(light.getId(), light);
                    return "light";
                }
                Light light = applianceService.findLightByLocation(slots.get("location")).get();
                light.setState(false);
                light.setColor("none");
                applianceService.updateLight(light.getId(), light);
                return "light";
            }
            if (Objects.equals(slots.get("state"), "on")){
                applianceService.getUserLights().forEach(e -> {
                    Light light = applianceService.findLightById(e.getId()).get();
                    light.setState(true);
                    light.setColor("white");
                    applianceService.updateLight(light.getId(), light);
                });
                return "light";
            }
            applianceService.getUserLights().forEach(e -> {
                Light light = applianceService.findLightById(e.getId()).get();
                light.setState(false);
                light.setColor("none");
                applianceService.updateLight(light.getId(), light);
            });
            return "light";
        } else if(Objects.equals(intent, "changeLightStateOff")){
            if (slots.containsKey("location")){
                Light light = applianceService.findLightByLocation(slots.get("location")).get();
                light.setState(false);
                light.setColor("none");
                applianceService.updateLight(light.getId(), light);
                return "light";
            }
            applianceService.getUserLights().forEach(e -> {
                Light light = applianceService.findLightById(e.getId()).get();
                light.setState(false);
                light.setColor("none");
                applianceService.updateLight(light.getId(), light);
            });
            return "light";
        } else if(Objects.equals(intent, "changeDeviceState")){
            Device device = applianceService.findDeviceByType(slots.get("device")).get();
            applianceService.changeDeviceState(device.getId(), Objects.equals(slots.get("state"), "on"));
            return "device";
        } else if(Objects.equals(intent, "changeСurtainsState")){
            if (slots.get("open").equals("open")){
                applianceService.changeCurtainsState(true);
                return "curtains";
            }
            applianceService.changeCurtainsState(false);
            return "curtains";
        } else if(Objects.equals(intent, "automation")){
            if(slots.containsKey("time2")){
                String openTime = (slots.get("time1") + ":" +
                        (slots.containsKey("time11") ? slots.get("time11") : "00" + " " + slots.get("daytime1")));
                String closeTime = (slots.get("time2") + ":" +
                        (slots.containsKey("time21") ? slots.get("time21") : "00" + " " + slots.get("daytime2")));
                applianceService.changeAutomation(openTime, closeTime);
                return "automation";
            } else if (Objects.equals(slots.get("open"), "open")) {
                String openTime = (slots.get("time1") + ":" +
                        (slots.containsKey("time11") ? slots.get("time11") : "00" + " " + slots.get("daytime1")));
                applianceService.changeAutomation(openTime, "");
                return "automation";
            }
            String closeTime = (slots.get("time1") + ":" +
                    (slots.containsKey("time11") ? slots.get("time11") : "00" + " " + slots.get("daytime1")));
            applianceService.changeAutomation("", closeTime);
            return "automation";
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your intent is not clear.");
        }
    }

    /*public Map<String, String> getCommand(RhinoInference inference) {
        String intent = inference.getIntent();
        Map<String, String> slots = inference.getSlots();
        Map<String, String> response = new HashMap<>();
        if(Objects.equals(intent, "changeColor")){
            // by default actions apply to all lights unless location is specified
            response.put("intent", "color");
            if (slots.containsKey("location")){
                response.put("location", slots.get("location"));
                response.put("color", slots.get("color"));
                return response;
            }
            response.put("color", slots.get("color"));
            response.put("location", "all");
            return response;
        }else if(Objects.equals(intent, "changeLightState")){
            response.put("intent", "light");
            if (slots.containsKey("location")){
                response.put("location", slots.get("location"));
                response.put("state", slots.get("state"));
                return response;
            }
            response.put("location", "all");
            response.put("state", slots.get("state"));
            return response;
        }else if(Objects.equals(intent, "changeLightStateOff")){
            response.put("intent", "light");
            if (slots.containsKey("location")){
                response.put("location", slots.get("location"));
                response.put("state", "off");
                return response;
            }
            response.put("location", "all");
            response.put("state", "off");
            return response;
        }else if(Objects.equals(intent, "changeDeviceState")){
            response.put("intent", "device");
            response.put("device", slots.get("device"));
            response.put("state", slots.get("state"));
            return response;
        }else if(Objects.equals(intent, "changeСurtainsState")){
            response.put("intent", "device");
            response.put("device", "curtains");
            if (slots.get("open").equals("open")){
                response.put("state", "on");
                return response;
            }
            response.put("state", "off");
            return response;
        }else  if(Objects.equals(intent, "automation")){
            response.put("intent", "automation");
            if(slots.containsKey("time2")){
                response.put(slots.get("open1"), slots.get("time1"));
            }
            response.put(slots.get("open2"), slots.get("time2"));
            return response;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your intent is not clear.");
        }
    }*/
}
