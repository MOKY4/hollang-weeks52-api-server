package swyg.hollang.controller

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import swyg.hollang.entity.Hobby

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
class HobbyControllerTest(
    @Autowired val em: EntityManager,
    @Autowired val mockMvc: MockMvc
) {

    @BeforeEach
    fun beforeEach() {

        for (i in 1..40) {
            val hobby = Hobby(
                "홀랑 $i",
                "홀랑 $i 요약",
                "홀랑 $i 상세정보",
                "default",
                "https://example.com/hollang$i.png",
                "https://weeks52.me"
            )
            hobby.recommendCount = 40L - i
            em.persist(hobby)
        }
    }

    @Test
    fun getHobbiesRank(){
        // given
        val page = 0
        val size = 20
        val sort = "recommendCount"

        /**
         * when
         */

        // 쿼리 파라미터를 넣었을 때
        val hasQueryParameter = mockMvc.perform(get("/hobbies")
                .queryParam("page", page.toString())
                .queryParam("size", size.toString())
                .queryParam("sort", sort))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))

        // 쿼리 파라미터를 넣지 않았을 때
        val noQueryParameter = mockMvc.perform(get("/hobbies"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))

        /**
         * then
         */

        // 쿼리 파라미터를 넣었을 때
        hasQueryParameter
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.hobbies.length()", 20).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.hobbies[0].id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.hobbies[0].shortName").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.hobbies[0].recommendCount").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.hobbies[0].imageUrl").exists())


        // 쿼리 파라미터를 안넣었을 때
        noQueryParameter
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.hobbies.length()", 20).exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.hobbies[0].id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.hobbies[0].shortName").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.hobbies[0].recommendCount").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.data.hobbies[0].imageUrl").exists())
    }
}
