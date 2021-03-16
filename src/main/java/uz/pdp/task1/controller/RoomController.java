package uz.pdp.task1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Hotel;
import uz.pdp.task1.entity.Room;
import uz.pdp.task1.payload.RoomDto;
import uz.pdp.task1.repository.HotelRepository;
import uz.pdp.task1.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    //create
    @PostMapping
    public String add(@RequestBody RoomDto roomDto) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Room room = new Room();
            room.setNumber(roomDto.getNumber());
            room.setFloor(roomDto.getFloor());
            room.setSize(roomDto.getSize());
            room.setHotel(optionalHotel.get());
            roomRepository.save(room);
            return "room saved";
        } else return "hotel not found";
    }

    //read
    @GetMapping
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Room getById(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        return optionalRoom.orElseGet(Room::new);
    }


//    @GetMapping(value = "hotelID/{id}")
//    public List<Room> getByHotelId(@PathVariable Integer id) {
//        return roomRepository.findAllByHotelId(id);
//    }
    //read by hotel id
 @GetMapping(value = "/forHotel/{id}")
    public Page<Room> getByHotelId(@PathVariable Integer id, @RequestParam int page) {
     Pageable pageable= PageRequest.of(page,10);
     Page<Room> all = roomRepository.findAllByHotelId(id,pageable);
     return all;

 }

    //update

    @PutMapping(value = "/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Optional<Room> optionalRoom = roomRepository.findById(id);
            if (optionalRoom.isPresent()) {
                Room editingRoom = optionalRoom.get();
                editingRoom.setNumber(roomDto.getNumber());
                editingRoom.setFloor(roomDto.getFloor());
                editingRoom.setSize(roomDto.getSize());
                editingRoom.setHotel(optionalHotel.get());
                roomRepository.save(editingRoom);
                return "room edited";
            }
            return "Room id not found";
        } else return "hotel id not found";
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Integer id) {

        try {
            roomRepository.deleteById(id);
            return "room deleted";
        } catch (Exception e) {
            return "error in deleting";
        }
    }


}
