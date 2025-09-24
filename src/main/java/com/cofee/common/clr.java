package com.cofee.common;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@Service
@SpringBootTest
@RequiredArgsConstructor
public class clr {
    private final EntityManager em;

    @Test
    @Transactional
    public void resetProductsWithTruncate() {
        em.flush();   // JPA가 쌓아둔 변경 즉시 반영
        em.clear();   // 1) 영속성 컨텍스트 비우기

        // 2) FK가 있으면 잠시 끔 (필요할 때만)
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        // 3) 테이블 비우고 auto_increment 리셋 (TRUNCATE는 암시적 COMMIT)
        em.createNativeQuery("TRUNCATE TABLE products").executeUpdate();

        // 4) FK 복구
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

        // 5) 안전하게 JPA 캐시 한 번 더 비움
        em.clear();
    }
}
