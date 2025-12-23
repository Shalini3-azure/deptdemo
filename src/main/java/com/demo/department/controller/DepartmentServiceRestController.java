package com.demo.department.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/*
 * @author shalshe  created on 12/1/2025
 */
@RestController
@RequestMapping("/departments")
public class DepartmentServiceRestController {

    // ---------------------------
    // 1. Auth Check for All Endpoints
    // ---------------------------
    private boolean isUnauthorized(String header) {
        return header == null || !header.equals("Bearer valid-token");
    }

    @GetMapping("")
    public ResponseEntity<?> getDepartments(
            @RequestHeader(value="Authorization", required=false) String authHeader) {

        if (isUnauthorized(authHeader)) {
            System.out.println("Unauthorized access to Department service");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        return ResponseEntity.ok(Arrays.asList("HR", "Finance", "IT"));
    }

    // ---------------------------
    // 2. Simulated Error
    // ---------------------------
    @GetMapping("/error")
    public ResponseEntity<?> error() {
        throw new RuntimeException("Simulated Department error");
    }

    // ---------------------------
    // 3. Slow Endpoint
    // ---------------------------
    @GetMapping("/slow")
    public ResponseEntity<?> slow() throws Exception {
        Thread.sleep(30000);
        return ResponseEntity.ok("Delayed response from Department");
    }

    // ---------------------------
    // 4. CPU Spike
    // ---------------------------
    @GetMapping("/cpu")
    public String cpuSpike() {
        for (long i=0; i<800_000_000L; i++) Math.sqrt(i);
        return "CPU spike completed";
    }

    // ---------------------------
    // 5. Malicious Payload Detection
    // ---------------------------
    @PostMapping("")
    public ResponseEntity<?> createDepartment(@RequestBody Map<String,String> payload) {
        String name = payload.get("name");
        if (name != null && (name.contains("--") || name.contains("'") || name.contains(" OR "))) {
            System.out.println("Suspicious input detected: " + name);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Suspicious input blocked");
        }
        return ResponseEntity.ok("Created department: " + name);
    }
    @GetMapping("/whoami")
public ResponseEntity<String> whoami() {
    return ResponseEntity.ok("department-service");
}
@GetMapping("/internal")
public ResponseEntity<String> internal() {
    return ResponseEntity.ok("Internal department data");
}

}
