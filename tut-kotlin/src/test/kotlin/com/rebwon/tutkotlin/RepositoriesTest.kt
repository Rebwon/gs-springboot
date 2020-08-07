package com.rebwon.tutkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTest @Autowired constructor(
        var entityManager: TestEntityManager,
        var userRepository: UserRepository,
        var articleRepository: ArticleRepository
)
{
    @Test
    fun `When findByIdOrNull then return Article`() {
        var rebwon = User("springrebwon", "Maeng", "Solomon")
        entityManager.persist(rebwon)
        var article = Article("Test Title", "Test Headline", "Test Content", rebwon)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        var rebwon = User("springrebwon", "Maeng", "Solomon")
        entityManager.persist(rebwon)
        entityManager.flush()
        val user = userRepository.findByLogin(rebwon.login)
        assertThat(user).isEqualTo(rebwon)
    }
}