package com.iot.homeautomationweb.services;

import com.iot.homeautomationweb.models.Curtains;
import com.iot.homeautomationweb.models.Device;
import com.iot.homeautomationweb.models.Light;
import com.iot.homeautomationweb.repositories.CurtainsRepository;
import com.iot.homeautomationweb.repositories.DeviceRepository;
import com.iot.homeautomationweb.repositories.LightRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
@AllArgsConstructor
public class ApplianceService {
    private final LightRepository lightRepository;
    private final DeviceRepository deviceRepository;
    private final CurtainsRepository curtainsRepository;
    private final UserService userService;
    public Optional<Light> findLightById(Integer id){
        return lightRepository.findById(id);
    }

    public Optional<Light> findLightByLocation(String location){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            return this.userService.getCurrentLoggedInUser().get().getLights().stream()
                    .filter(light -> light.getRoom().equals(location))
                    .findAny();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User was not found.");
    }

    public Optional<Device> findDeviceById(Integer id){
        return deviceRepository.findById(id);
    }

    public Optional<Device> findDeviceByType(String type){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            return this.userService.getCurrentLoggedInUser().get().getDevices().stream()
                    .filter(device -> device.getType().equals(type))
                    .findAny();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User was not found.");
    }

    public List<Light> getAllLights() {
        return lightRepository.findAll();
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public List<Curtains> getAllCurtains() {
        return curtainsRepository.findAll();
    }

    public List<Light> getUserLights() {
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            return this.userService.getCurrentLoggedInUser().get().getLights();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User was not found.");
    }

    public List<Device> getUserDevices() {
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            return this.userService.getCurrentLoggedInUser().get().getDevices();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User was not found.");
    }

    public Curtains getUserCurtains() {
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            return this.userService.getCurrentLoggedInUser().get().getCurtains();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User was not found.");
    }

    @Transactional
    public boolean saveLight(Light light){
        Light newLight = new Light(light.getRoom(), light.getColor(), light.isState());

        this.lightRepository.save(newLight);

        if(!lightRepository.existsById(newLight.getId())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Light with id " + newLight.getId() + " was not added.");
        }

        if(this.userService.getCurrentLoggedInUser().isPresent()){
            this.userService.getCurrentLoggedInUser().get().addLight(newLight);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean saveDevice(Device device){
        Device newDevice = new Device(device.getType(), device.isState());

        this.deviceRepository.save(newDevice);

        if(!deviceRepository.existsById(newDevice.getId())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Device with id " + newDevice.getId() + " was not added.");
        }

        if(this.userService.getCurrentLoggedInUser().isPresent()){
            this.userService.getCurrentLoggedInUser().get().addDevice(newDevice);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean saveCurtains(){
        Curtains newCurtains = new Curtains();
        curtainsRepository.save(newCurtains);

        if(this.userService.getCurrentLoggedInUser().isPresent()){
            this.userService.getCurrentLoggedInUser().get().setCurtains(newCurtains);
            return true;
        }

        return false;
    }

    @Transactional
    public void changeLightState(Integer id, boolean value){
        Light light = lightRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Light was not found."));
        light.setState(value);
    }

    @Transactional
    public void changeDeviceState(Integer id, boolean value){
        Device device = deviceRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Device was not found."));
        device.setState(value);
    }

    @Transactional
    public void changeCurtainsState(boolean value){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            Curtains curtains = this.userService.getCurrentLoggedInUser().get().getCurtains();
            curtains.setState(value);
        }
    }

    @Transactional
    public void changeAutomation (String open, String  close){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            Curtains curtains = this.userService.getCurrentLoggedInUser().get().getCurtains();
            if (open.isBlank()){
                curtains.setCloseTime(close);
                System.out.println(open + " " + close);
                System.out.println(curtains.getOpenTime() + " " + curtains.getCloseTime());
            } else if (close.isBlank()) {
                curtains.setOpenTime(open);
                System.out.println(open + " " + close);
                System.out.println(curtains.getOpenTime() + " " + curtains.getCloseTime());
            } else {
                curtains.setOpenTime(open);
                curtains.setCloseTime(close);
                System.out.println(open + " " + close);
                System.out.println(curtains.getOpenTime() + " " + curtains.getCloseTime());
            }
        }
    }

    @Transactional
    public void updateLight(Integer id,Light light) {
        Light toUpdate = lightRepository.getReferenceById(id);
        if(!light.getRoom().equals(toUpdate.getRoom())){
            toUpdate.setRoom(light.getRoom());
        }
        if(!light.getColor().equals(toUpdate.getColor())){
            toUpdate.setColor(light.getColor());
        }
        if((!light.isState() && toUpdate.isState()) || (light.isState() && !toUpdate.isState())){
            toUpdate.setState(light.isState());
        }
    }

    @Transactional
    public void updateDevice(Integer id,Device device) {
        Device toUpdate = deviceRepository.getReferenceById(id);
        if(!device.getType().equals(toUpdate.getType())){
            toUpdate.setType(device.getType());
        }
        if((!device.isState() && toUpdate.isState()) || (device.isState() && !toUpdate.isState())){
            toUpdate.setState(device.isState());
        }
    }

    public void deleteLightById(Integer id){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            if (lightRepository.findById(id).isPresent()){
                this.userService.getCurrentLoggedInUser().get().deleteLight(lightRepository.findById(id).get());
                lightRepository.deleteById(id);
            }
        }
    }

    public void deleteDeviceById(Integer id){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            if (deviceRepository.findById(id).isPresent()){
                this.userService.getCurrentLoggedInUser().get().deleteDevice(deviceRepository.findById(id).get());
                deviceRepository.deleteById(id);
            }
        }
    }

    public void deleteCurtains(){
        if(this.userService.getCurrentLoggedInUser().isPresent()){
            curtainsRepository.delete(this.userService.getCurrentLoggedInUser().get().getCurtains());
            this.userService.getCurrentLoggedInUser().get().setCurtains(null);
        }
    }

    @Scheduled(fixedDelay = 60000) // Run every 60 seconds
    public void checkCurtainsState() {
        System.out.println("Scheduled");
        automatedCurtainsState();
    }

    public void automatedCurtainsState(){
        System.out.println("Checking Curtains State");
        isUpdated = false;
        getAllCurtains().forEach(curtains -> {
            if(curtains != null){
                LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0); // Ignore the seconds and milliseconds for comparison

                System.out.println(currentTime);

                if(curtains.getOpenTime() != null){
                    LocalTime localOpenTime = curtains.getOpenTime().toLocalTime();
                    if (currentTime.equals(localOpenTime) && !curtains.isState()) {
                        curtains.setState(true);
                        curtainsRepository.save(curtains);
                        System.out.println("Curtains opened at " + currentTime);
                        isUpdated = true;
                    }
                }

                if(curtains.getCloseTime() != null){
                    LocalTime localCloseTime = curtains.getCloseTime().toLocalTime();
                    if (currentTime.equals(localCloseTime) && curtains.isState()) {
                        curtains.setState(false);
                        curtainsRepository.save(curtains);
                        System.out.println("Curtains closed at " + currentTime);
                        isUpdated = true;
                    }
                }
            }
        });
    }

    public static boolean isUpdated = false;
}
