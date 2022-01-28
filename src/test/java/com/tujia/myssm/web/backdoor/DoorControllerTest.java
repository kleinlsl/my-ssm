package com.tujia.myssm.web.backdoor;

import java.util.stream.IntStream;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/11/05 12:00
 */
@Slf4j
public class DoorControllerTest {

    @Test
    public void testPromoGen() {
        IntStream.range(0, 128).forEach(idx -> {

            System.out.println("SELECT * FROM Promo_" + idx +
                                       " WHERE flow_status = 10 AND to_time < now() ORDER BY to_time ASC LIMIT 200;");

        });
    }
}
