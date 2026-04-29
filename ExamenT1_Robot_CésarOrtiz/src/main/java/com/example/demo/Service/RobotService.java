package com.example.demo.Service;

import com.example.demo.Model.Robot;
import java.util.List;

public interface RobotService {
    List<Robot> listarTodos();
    Robot guardar(Robot robot);
    Robot obtenerPorId(Long id);
    void eliminar(Long id);
}