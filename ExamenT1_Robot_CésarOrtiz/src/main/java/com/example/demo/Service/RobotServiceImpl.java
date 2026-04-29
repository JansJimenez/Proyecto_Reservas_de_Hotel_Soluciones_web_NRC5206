package com.example.demo.Service;

import com.example.demo.Model.Robot;
import com.example.demo.Repository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RobotServiceImpl implements RobotService {

    @Autowired
    private RobotRepository robotRepository;

    @Override
    public List<Robot> listarTodos() { return robotRepository.findAll(); }

    @Override
    public Robot guardar(Robot robot) { return robotRepository.save(robot); }

    @Override
    public Robot obtenerPorId(Long id) { return robotRepository.findById(id).orElse(null); }

    @Override
    public void eliminar(Long id) { robotRepository.deleteById(id); }
}