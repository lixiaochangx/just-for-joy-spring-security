package com.xc.justforjoy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author lxcecho
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping("listTasks")
    public String listTasks() {
        return "任务列表";
    }

    @PostMapping("newTasks")
    @PreAuthorize("hasRole('ADMIN')")
    public String newTasks() {
        return "创建了一个新的任务";
    }

    @PutMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId") Integer id) {
        return "更新了一下id为:" + id + "的任务";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTasks(@PathVariable("taskId") Integer id) {
        return "删除了id为:" + id + "的任务";
    }
}
