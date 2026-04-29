package com.example.demo.Controller;

import com.example.demo.Model.Robot;
import com.example.demo.Service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/robots")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @GetMapping
    public List<Robot> listar() {
        return robotService.listarTodos();
    }

    @GetMapping("/{id}")
    public Robot obtener(@PathVariable Long id) {
        return robotService.obtenerPorId(id);
    }

    @PostMapping
    public Robot crear(@RequestBody Robot robot) {
        return robotService.guardar(robot);
    }

    @PutMapping("/{id}")
    public Robot actualizar(@RequestBody Robot robot, @PathVariable Long id) {
        Robot actual = robotService.obtenerPorId(id);
        if (actual != null) {
            actual.setNombre(robot.getNombre());
            actual.setTipo(robot.getTipo());
            actual.setPeso(robot.getPeso());
            actual.setAltura(robot.getAltura());
            actual.setAutonomia(robot.getAutonomia());
            return robotService.guardar(actual);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        robotService.eliminar(id);
    }
}