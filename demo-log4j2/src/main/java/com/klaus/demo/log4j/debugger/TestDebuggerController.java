package com.klaus.demo.log4j.debugger;

import com.fd.web.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Klaus
 */
@Slf4j
@RestController
@RequestMapping("/api/debugger")
public class TestDebuggerController {

    @GetMapping("/{id}")
    public Result<String> testDebugger(@PathVariable("id") String id) {
        log.debug("TestDebuggerController#testDebugger id:{}", id);
        return Result.ok(id);
    }
}
