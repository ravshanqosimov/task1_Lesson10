package uz.pdp.task1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Hotel;
import uz.pdp.task1.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController{

    @Autowired
    HotelRepository hotelRepository;

    //C
    @PostMapping
    public String add(@RequestBody Hotel hotel    ) {
         if (!hotelRepository.existsByName(hotel.getName())){
        hotelRepository.save(hotel);
        return "hotel saved";}
         else return "this hotel was saved before";

    }

    //R
    @GetMapping
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Hotel getById(@PathVariable Integer id) {
        Optional<Hotel> optionalSubject = hotelRepository.findById(id);
        return optionalSubject.orElseGet(Hotel::new);
    }

    //U
    @PutMapping(value = "/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel hotel  ) {

        Optional<Hotel> optionalSubject = hotelRepository.findById(id);
        if (optionalSubject.isPresent()) {
            Hotel editingcontinent = optionalSubject.get();
            editingcontinent.setName(hotel.getName());
            hotelRepository.save(editingcontinent);
            return "hotel edited";
        }
        return "hotel id not found";
    }

    //D
    @DeleteMapping(value = "/{id}")
    public String deleteById(@PathVariable Integer id) {
        hotelRepository.deleteById(id);
        return "hotel deleted";
    }
}